package com.sustech.gamercenter.service;


import com.sustech.gamercenter.dao.MessageRepository;
import com.sustech.gamercenter.dao.PaymentRepository;
import com.sustech.gamercenter.dao.UserRepository;
import com.sustech.gamercenter.dao.projection.GameView;
import com.sustech.gamercenter.model.Payment;
import com.sustech.gamercenter.model.User;
import com.sustech.gamercenter.service.token.SimpleTokenService;
import com.sustech.gamercenter.util.exception.*;
import com.sustech.gamercenter.util.model.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Autowired
    PaymentRepository paymentRepository;

    @Autowired
    MailService mailService;


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

    //    public void registerUserConfirm(String email,String confirmationCode){
    //        // TODO add email confirmation, confirmationCode stores in redis
    //    }

    public Map<String, String> loginAuthentication(String email, String password, String role) throws UserNotFoundException, IncorrectPasswordException, UserHasNoRoleException {
        User user = queryUserByEmail(email);
        if (role.length() != 1 || !user.getRole().contains(role.toLowerCase())) {
            throw new UserHasNoRoleException("User " + user.getName() + " has no such role");
        } else if (encoder.matches(password, user.getPassword())) {
            Map<String, String> map = new HashMap<>();
            map.put("user_id", user.getId().toString());
            map.put("token", tokenService.createToken(user));
            return map;
        } else {
            throw new IncorrectPasswordException("Password incorrect");
        }
    }

    public void logout(String token) throws UserHasNoTokenException, InvalidTokenException {
        tokenService.deleteToken(token);
    }


    //
    //
    //
    //
    //


    private final static String STORAGE_PREFIX = System.getProperty("user.dir");

    public byte[] getAvatar(String id) throws IOException {
        String path = STORAGE_PREFIX + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator
                + "static" + File.separator + "user" + File.separator + "avatar" + File.separator + id + ".jpg";
        File file = new File(path);
        if (!file.exists())
            file = new File(path.replace(id, "default"));
        FileInputStream inputStream = new FileInputStream(file);
        byte[] bytes = new byte[inputStream.available()];
        inputStream.read(bytes, 0, inputStream.available());
        return bytes;
    }

    public void uploadAvatar(String token, MultipartFile avatar) throws InvalidTokenException, UserNotFoundException, IOException {
//        User user = queryUserById(15734L);
        User user = queryUserById((tokenService.getIdByToken(token)));

        String path = File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator
                + "static" + File.separator + "user" + File.separator + "avatar";
        String realPath = STORAGE_PREFIX + path;
        File dir = new File(realPath);

        String filename = user.getId().toString() + ".jpg";
        File fileServer = new File(dir, filename);

//        System.out.println(fileServer.exists());
//        System.out.println(fileServer.getAbsoluteFile().delete());

        byte[] data = avatar.getBytes();
        FileOutputStream fileOutputStream = new FileOutputStream(fileServer);
        fileOutputStream.write(data);
        fileOutputStream.flush();
        fileOutputStream.close();
        System.gc();
    }


    //
    //
    //
    //
    // login/token required


    public void changeEmail(String token, String new_email) throws InvalidTokenException, UserNotFoundException, EmailNotSendException {
        queryUserById((tokenService.getIdByToken(token)));
        mailService.sendConfirmationCodeMail(new_email, 15);
    }

    public void changeEmailConfirm(String token, String new_email, String confirmation_code) throws InvalidTokenException, UserNotFoundException, InvalidConfirmationCodeException {
        User user = queryUserById((tokenService.getIdByToken(token)));
        if (tokenService.compareConfirmationCode(new_email, confirmation_code)) {
            user.setEmail(new_email);
            userRepository.flush();
        } else {
            throw new InvalidConfirmationCodeException("Confirmation code doesn't match");
        }
    }

    public void changePassword(String token, String old, String password) throws IncorrectPasswordException, InvalidTokenException, UserNotFoundException {
        User user = queryUserById((tokenService.getIdByToken(token)));
        if (encoder.matches(old, user.getPassword())) {
            user.setPassword(encoder.encode(password));
            userRepository.flush();
        } else {
            throw new IncorrectPasswordException("Old password incorrect");
        }
    }

    public void changeBio(String token, String bio) throws InvalidTokenException, UserNotFoundException {
        User user = queryUserById((tokenService.getIdByToken(token)));
        user.setBio(bio);
        userRepository.flush();
    }

    public Double changeBalance(String token, Double amount) throws InvalidTokenException, UserNotFoundException {
        User user = queryUserById((tokenService.getIdByToken(token)));
        user.setBalance(user.getBalance() + amount);
        userRepository.flush();
        return user.getBalance();
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

    public void purchaseGame(Long userId, Long devId, Long gameId, Double price) throws UserNotFoundException, InsufficientBalanceException {
        // actual transferring
        transfer(price, userId, devId);

        User player = queryUserById(userId);
        User dev = queryUserById(devId);
        Payment payment = new Payment(userId, -price, player.getBalance());
        Payment receive = new Payment(devId, price, dev.getBalance());

        // add audit record
        paymentRepository.save(payment);
        paymentRepository.save(receive);

        paymentRepository.receiveGame(userId, gameId, payment.getId());
    }

    public List<String> userHasGameTags(String token) throws InvalidTokenException {
        return userRepository.userHasGameTags(tokenService.getIdByToken(token));
    }

    public List<GameView> userHasGamesWithTag(String token, String tag) throws InvalidTokenException {
        Long id = tokenService.getIdByToken(token);
        if (StringUtils.isEmpty(tag)) {
            return userRepository.userHasGames(id);
        } else {
            return userRepository.userHasGamesWithTag(id, tag);
        }
    }


    //
    //
    //
    //
    // message


    public void sendChatTo(String token, Long to, String message) throws InvalidTokenException {
        Long from = tokenService.getIdByToken(token);
        userRepository.sendMessage(from, to, "chat", message);
    }

    public void readMessage(Long id) {
        userRepository.readMessage(id);
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

}