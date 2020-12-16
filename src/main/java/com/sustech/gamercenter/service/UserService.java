package com.sustech.gamercenter.service;


import com.sustech.gamercenter.dao.*;
import com.sustech.gamercenter.dao.projection.GameView;
import com.sustech.gamercenter.model.Payment;
import com.sustech.gamercenter.model.User;
import com.sustech.gamercenter.model.UserCollection;
import com.sustech.gamercenter.model.UserInfo;
import com.sustech.gamercenter.service.token.SimpleTokenService;
import com.sustech.gamercenter.util.exception.*;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

@Service
public class UserService {

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    SimpleTokenService tokenService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserCollectionRepository userCollectionRepository;

    @Autowired
    GameRepository gameRepository;

    @Autowired
    GameCommentRepository gameCommentRepository;

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

    public void registerUser(String name, String email, String password, String role) throws UserRegisterException, UnauthorizedAttemptException, EmailNotSendException {
        if (role.contains("a") || role.contains("t")) {
            throw new UnauthorizedAttemptException("Cannot register admin/tester account");
        } else if (!role.equals("p") && !role.equals("d") && !role.equals("pd") && !role.equals("dp")) {
            throw new UserRegisterException("Invalid role selection");
        }

        mailService.sendConfirmationCodeMail(email, 15);

        try {
            password = encoder.encode(password);
            User user = new User(name, email, password, role.toLowerCase());
            user.setLocked(true);
            userRepository.save(user);
        } catch (Exception e) {
            throw new UserRegisterException("Username/email already exists", e);
        }
    }

    public void registerUserConfirm(String email, String confirmationCode) throws UserNotFoundException, InvalidConfirmationCodeException {
        User user = queryUserByEmail(email);
        if (tokenService.compareConfirmationCode(email, confirmationCode)) {
            user.setLocked(false);
            userRepository.flush();
        } else {
            throw new InvalidConfirmationCodeException("Confirmation code doesn't match");
        }
    }

    public void resendRegisterConfirm(String email) throws UserNotFoundException, EmailNotSendException {
        queryUserByEmail(email);
        mailService.sendConfirmationCodeMail(email, 15);
    }

    public Map<String, String> loginAuthentication(String email, String password, String role) throws UserNotFoundException, IncorrectPasswordException, UserHasNoRoleException, UserAccountLockedException {
        User user = queryUserByEmail(email);
        if (role.length() != 1 || !user.getRole().contains(role.toLowerCase())) {
            throw new UserHasNoRoleException("User " + user.getName() + " has no such role");
        } else if (user.getLocked()) {
            throw new UserAccountLockedException("User " + user.getName() + "'s account is locked");
        } else if (encoder.matches(password, user.getPassword())) {
            Map<String, String> map = new HashMap<>();
            map.put("user_id", user.getId().toString());
            map.put("user_name", user.getName());
            map.put("token", tokenService.createToken(user, role.toLowerCase()));
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
    // avatar, collection, file related


    private final static String STORAGE_PREFIX = System.getProperty("user.dir");

    private final static String RES_USER = STORAGE_PREFIX + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator + "static" + File.separator + "user";

    private final static String COLLECTION = RES_USER + File.separator + "collection";

    private final static String AVATAR = RES_USER + File.separator + "avatar";

    public byte[] getAvatar(String id) throws IOException {
        String path = AVATAR + File.separator + id + ".jpg";
        File file = new File(path);
        if (!file.exists())
            file = new File(path.replace(id, "default"));
        FileInputStream inputStream = new FileInputStream(file);
        return IOUtils.toByteArray(inputStream);
    }

    public void uploadAvatar(String token, MultipartFile avatar) throws InvalidTokenException, UserNotFoundException, IOException {
        User user = queryUserById((tokenService.getIdByToken(token)));
        File dir = new File(AVATAR);

        String filename = user.getId().toString() + ".jpg";
        File fileServer = new File(dir, filename);

        byte[] data = avatar.getBytes();
        FileOutputStream fileOutputStream = new FileOutputStream(fileServer);
        fileOutputStream.write(data);
        fileOutputStream.flush();
        fileOutputStream.close();
        System.gc();
    }

    public void uploadCollection(String token, String name, String type, MultipartFile file) throws InvalidTokenException, UploadFileException {
        Long id = tokenService.getIdByToken(token);

        name = StringUtils.isEmpty(name) ? (new Random().ints(97, 122 + 1)
                .limit(10)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString()) : name;

        File dir = new File(COLLECTION + File.separator + id.toString());
        if (!dir.exists()) dir.mkdir();

        try {
            String path = File.separator + id.toString()
                    + File.separator + name + "." + file.getOriginalFilename().split("\\.")[1];
            File save = new File(COLLECTION + path);
            save.createNewFile();
            file.transferTo(save);

            UserCollection u = new UserCollection(id, name, type, path);
            userCollectionRepository.save(u);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new UploadFileException("Failed to upload collection");
        }
    }

    public List<UserCollection> getCollection(String token, String type) throws InvalidTokenException {
        Long userId = tokenService.getIdByToken(token);
        if (StringUtils.isEmpty(type)) {
            return userCollectionRepository.findAllByUserId(userId);
        } else {
            return userCollectionRepository.findAllByUserIdAndType(userId, type);
        }
    }

    public byte[] downloadCollection(String path) throws IOException {
        FileInputStream inputStream = new FileInputStream(COLLECTION + File.separator + path);
        return IOUtils.toByteArray(inputStream);
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

    public void changeEmailConfirm(String token, String new_email, String confirmationCode) throws InvalidTokenException, UserNotFoundException, InvalidConfirmationCodeException {
        User user = queryUserById((tokenService.getIdByToken(token)));
        if (tokenService.compareConfirmationCode(new_email, confirmationCode)) {
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

    private Long purchase(Long userId, Long devId, Long gameId, Double price) throws InsufficientBalanceException, UserNotFoundException {
        // actual transferring
        transfer(price, userId, devId);

        User player = queryUserById(userId);
        User dev = queryUserById(devId);
        Payment payment = new Payment(userId, -price, player.getBalance());
        Payment receive = new Payment(devId, price, dev.getBalance());

        // add audit record
        paymentRepository.save(payment);
        paymentRepository.save(receive);

        return payment.getId();
    }

    public void purchaseGame(Long userId, Long devId, Long gameId, Double price) throws UserNotFoundException, InsufficientBalanceException {
        Long paymentId = purchase(userId, devId, gameId, price);
        paymentRepository.receiveGame(userId, gameId, paymentId);
    }

    public void purchaseGameDLC(Long userId, Long devId, Long gameId, Long DLCId, Double price) throws InsufficientBalanceException, UserNotFoundException {
        Long paymentId = purchase(userId, devId, gameId, price);
        paymentRepository.receiveGameDLC(userId, gameId, DLCId, paymentId);
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


    public void sendMessageTo(String token, String to_name, String type, String message) throws InvalidTokenException, UserNotFoundException {
        Long from = tokenService.getIdByToken(token);
        Long to = queryUserByName(to_name).getId();

        if (type.equals("invitation")) {
            String gameName = gameRepository.getOne(Long.valueOf(message)).getName();
            message = "Let's play " + gameName + " together!";
        }

        userRepository.sendMessage(from, to, type, message);
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


    //
    //
    //
    //
    // OAuth


    public String createOAuthToken(String token, Long game_id) throws InvalidTokenException {
        Long user_id = tokenService.getIdByToken(token);
        String uuid = UUID.randomUUID().toString();
        userRepository.createOAuthToken(user_id, game_id, uuid);
        return uuid;
    }

    public String checkUserHasGame(String token, Long game_id) throws InvalidTokenException {
        Long user_id = tokenService.getIdByToken(token);
        String status = userRepository.userHasGameStatus(user_id, game_id);
        if (StringUtils.isEmpty(status)) {
            return "purchase";
        } else if (status.equals("purchased")) {
            return "download";
        } else if (status.equals("downloaded")) {
            return "owned";
        } else {
            return "forbidden";
        }
    }


    //
    //
    //
    //
    // comment


    public void reportComment(String token, Long comment_id, String reason) throws InvalidTokenException {
        Long id = tokenService.getIdByToken(token);
        gameCommentRepository.reportComment(comment_id, id, reason);
    }

}