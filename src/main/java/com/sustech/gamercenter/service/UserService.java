package com.sustech.gamercenter.service;


import com.sustech.gamercenter.model.User;
import com.sustech.gamercenter.dao.UserRepository;
import com.sustech.gamercenter.service.token.SimpleTokenServiceImpl;
import com.sustech.gamercenter.util.exception.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class UserService {

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    SimpleTokenServiceImpl tokenService;


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


    /**
     * TODO, authority check
     */
    public User registerUser(String name, String email, String password, String role) throws UserRegisterException, UnauthorizedAttemptException {
        try {
            password = encoder.encode(password);
            User user = new User(name, email, password, role);
            userRepository.save(user);
            return user;
        } catch (Exception e) {
            throw new UserRegisterException(e.getClass().getSimpleName(), e);
        }
    }


    //
    //
    //
    //
    // login/token required

    // TODO

    public void changePassword(String token, String old, String password) throws IncorrectPasswordException, InvalidTokenException, UserNotFoundException {
        User user = queryUserById((tokenService.getIdByToken(token)));
        if (encoder.matches(old, user.getPassword())) {
            userRepository.updatePassword(user.getId(), encoder.encode(password));
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
    }

    public void changeBio(String token, String bio) throws InvalidTokenException, UserNotFoundException {
        Long id = tokenService.getIdByToken(token);
        User user = queryUserById((tokenService.getIdByToken(token)));
        userRepository.updateBio(id, bio);
    }

    public void uploadAvatar(String token, File file) throws InvalidTokenException, UserNotFoundException {
        User user = queryUserById((tokenService.getIdByToken(token)));
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
}