package com.sustech.gamercenter.service;


//import com.sustech.gamercenter.dao.PlayerRepository;

import com.sustech.gamercenter.dao.UserRepository;
import com.sustech.gamercenter.model.User;
import com.sustech.gamercenter.service.token.SimpleTokenService;
import com.sustech.gamercenter.util.exception.*;
import com.sustech.gamercenter.util.model.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@Service
public class UserService {

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    UserRepository userRepository;

    @Autowired
    SimpleTokenService tokenService;


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
        }

        try {
            password = encoder.encode(password);
            User user = new User(name, email, password, role.toLowerCase());
            userRepository.save(user);
        } catch (Exception e) {
            throw new UserRegisterException(e.getClass().getSimpleName(), e);
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

    public void uploadAvatar(String token, MultipartFile avatar) throws InvalidTokenException, UserNotFoundException, IOException {
        User user = queryUserById((tokenService.getIdByToken(token)));

        String path = File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator
                + "static" + File.separator + "user" + File.separator + "avatar";
        String realPath = STORAGE_PREFIX + path;
        File dir = new File(realPath);
        String filename = user.getId().toString() + ".jpg";
        File fileServer = new File(dir, filename);
        avatar.transferTo(fileServer);
    }

    public void topup(String token, Double amount) throws InvalidTokenException, UserNotFoundException {
        User user = queryUserById((tokenService.getIdByToken(token)));
        user.setBalance(user.getBalance() + amount);
        userRepository.flush();
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

    public UserInfo getUserInfo(String token) throws InvalidTokenException {
        Long id = tokenService.getIdByToken(token);
        return new UserInfo.builder()
                .user(userRepository.getOne(id))
                .friends(userRepository.userHasFriends(id))
                .build();
    }
}