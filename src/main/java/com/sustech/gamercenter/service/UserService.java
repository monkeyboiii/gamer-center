package com.sustech.gamercenter.service;


import com.sustech.gamercenter.dao.MessageRepository;
import com.sustech.gamercenter.dao.UserRepository;
import com.sustech.gamercenter.dao.projection.GameView;
import com.sustech.gamercenter.model.User;
import com.sustech.gamercenter.service.token.SimpleTokenService;
import com.sustech.gamercenter.util.exception.*;
import com.sustech.gamercenter.util.model.UserInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

@Service
public class UserService {

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    SimpleTokenService tokenService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    MessageRepository messageRepository;


    /**
     * normal try catch with throwable stacktrace option available
     */
    public User queryUserById(Long id) throws UserNotFoundException {
        try {
            return userRepository.getOne(id);
        } catch (Exception e) {
            throw new UserNotFoundException("User with id " + id.toString() + " doesn't exist");
        }
    }

    /**
     * {@link java.util.Optional<User>} style return or throw exception
     */
    public User queryUserByName(String name) throws UserNotFoundException {
        return userRepository
                .findByNameIgnoreCase(name)
                .orElseThrow(() ->
                        new UserNotFoundException("User with name " + name + " doesn't exist"));
    }

    public User queryUserByEmail(String email) throws UserNotFoundException {
        return userRepository
                .findByEmail(email)
                .orElseThrow(() ->
                        new UserNotFoundException("User with email " + email + " doesn't exist"));
    }

    public void registerUser(String name, String email, String password, String role) throws UserRegisterException, UnauthorizedAttemptException {
        if (role.contains("a") || role.contains("t")) {
            throw new UnauthorizedAttemptException("Cannot register admin/tester account");
        } else if (!role.equals("p") && !role.equals("d") && !role.equals("pd") && !role.equals("dp")) {
            throw new UserRegisterException("Invalid role selection");
        }

        try {
            password = encoder.encode(password);
            User user = new User(name, email, password, role.toLowerCase());
            userRepository.save(user);
        } catch (Exception e) {
            throw new UserRegisterException("Username/email already exists", e);
        }
    }


    // TODO add email confirmation, confirmationCode stores in redis
//    public void registerUserConfirm(String email,String confirmationCode){}


    private final static String STORAGE_PREFIX = System.getProperty("user.dir");

    public byte[] getAvatar(String id) throws IOException {
        String path = STORAGE_PREFIX + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator
                + "static" + File.separator + "user" + File.separator + "avatar" + File.separator + id + ".jpg";
        File file = new File(path);
        FileInputStream inputStream = new FileInputStream(file);
        byte[] bytes = new byte[inputStream.available()];
        inputStream.read(bytes, 0, inputStream.available());
        return bytes;
    }

    public void uploadAvatar(String token, MultipartFile avatar) throws InvalidTokenException, UserNotFoundException, IOException {
        User user = queryUserById((tokenService.getIdByToken(token)));

        String path = File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator
                + "static" + File.separator + "user" + File.separator + "avatar";
        String realPath = STORAGE_PREFIX + path;
        File dir = new File(realPath);
        String filename = user.getId().toString() + ".jpg";
        File fileServer = new File(dir, filename);

        try {
            avatar.transferTo(fileServer);
        } catch (Exception e) {
            logger.error(e.getClass().getName());
        }
    }

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);


    //
    //
    //
    //
    // login/token required


    public void changePassword(String token, String old, String password) throws IncorrectPasswordException, InvalidTokenException, UserNotFoundException {
        User user = queryUserById((tokenService.getIdByToken(token)));
        if (encoder.matches(old, user.getPassword())) {
            user.setPassword(encoder.encode(password));
            userRepository.flush();
        } else {
            throw new IncorrectPasswordException("Old password incorrect");
        }
    }

    public void changeEmail(String token, String email) throws InvalidTokenException, UserNotFoundException {
        User user = queryUserById((tokenService.getIdByToken(token)));
        user.setEmail(email);
        userRepository.flush();
    }

    public void changeEmailConfirm(String token, String email, String confirmation_code) throws InvalidTokenException, UserNotFoundException {
        User user = queryUserById((tokenService.getIdByToken(token)));
        // TODO add email confirmation
    }

    public void changeBio(String token, String bio) throws InvalidTokenException, UserNotFoundException {
        User user = queryUserById((tokenService.getIdByToken(token)));
        user.setBio(bio);
        userRepository.flush();
    }

    public void topUp(String token, Double amount) throws InvalidTokenException, UserNotFoundException {
        User user = queryUserById((tokenService.getIdByToken(token)));
        user.setBalance(user.getBalance() + amount);
        userRepository.flush();
    }

    public void auditPurchase(Long id, Double amount) throws UserNotFoundException, InsufficientBalanceException {
        User user = queryUserById(id);
        if (user.getBalance() + amount < 0) {
            throw new InsufficientBalanceException("User  " + user.getName() + " has no sufficient balance");
        } else {
            userRepository.auditPurchase(id, amount);
        }
    }

    public void transfer(Double amount, Long from, Long to) throws UserNotFoundException, InsufficientBalanceException {
        User payer = queryUserById(from);
        User payee = queryUserById(to);
        Double balance = payer.getBalance();
        if (balance < amount) {
            throw new InsufficientBalanceException("User  " + payer.getName() + " has no sufficient balance");
        }

        payer.setBalance(balance - amount);
        payee.setBalance(payee.getBalance() + amount);

        userRepository.flush();
    }


    //
    //
    //
    //
    //


    /**
     * retrieve the most specific user info in a wrapper class
     */
    public UserInfo getUserInfo(String token) throws InvalidTokenException {
        Long id = tokenService.getIdByToken(token);
        return new UserInfo.builder()
                .user(userRepository.getOne(id))
                .friends(userRepository.userHasFriends(id))
                .games(userRepository.userHasGames(id))
//                .messages(messageRepository.findAllByUserIdAndUnread(id, true))
//                .messages(userRepository.userHasAllMessages(id))
                .messages(userRepository.userHasUnreadMessages(id, true))
                .build();
    }

    public void sendFriendRequest(String token, String value, String method) throws InvalidTokenException, UserNotFoundException {
        Long id = tokenService.getIdByToken(token);
        Long to_user_id;

        if (method.equals("id")) {
            to_user_id = new Long(value);
        } else if (method.equals("name")) {
            to_user_id = queryUserByName(value).getId();
        } else { // email
            to_user_id = queryUserByEmail(value).getId();
        }

        userRepository.friendRequest(id, to_user_id);
        userRepository.sendMessage(id, to_user_id, "friend request", "Please add me as a friend");
    }

    public void confirmFriendRequest(String token, Long from) throws InvalidTokenException {
        Long confirm_id = tokenService.getIdByToken(token);
        userRepository.confirmFriendRequest(from, confirm_id);
        userRepository.sendMessage(confirm_id, from, "reply", "I've accepted your friend request");
    }

    public void readMessage(Long id) {
        userRepository.readMessage(id);
    }


    //
    //
    //
    //
    //


    public List<GameView> userHasGamesInTag(String token, String tag) throws InvalidTokenException {
        return userRepository.userHasGamesWithTag(tokenService.getIdByToken(token), tag);
    }

}