package com.sustech.gamercenter.service;

import com.sustech.gamercenter.dao.UserRepository;
import com.sustech.gamercenter.model.User;
import com.sustech.gamercenter.service.token.SimpleTokenService;
import com.sustech.gamercenter.util.exception.UnauthorizedAttemptException;
import com.sustech.gamercenter.util.exception.UserNotFoundException;
import com.sustech.gamercenter.util.exception.UserRegisterException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @Autowired
    SimpleTokenService tokenService;

    @Autowired
    PasswordEncoder encoder;


    public User createUser(String name, String email, String password, String role) throws UserRegisterException, UnauthorizedAttemptException {
        try {
            password = encoder.encode(password);
            User user = new User(name, email, password, role);
            userRepository.save(user);
            return user;
        } catch (Exception e) {
            throw new UserRegisterException(e.getClass().getSimpleName(), e);
        }
    }

    public void assignRole(Long Id, String role) throws UserNotFoundException {
        User user = userService.queryUserById(Id);
        user.setRole(role);
        userRepository.flush();
    }

    public void lockOrUnlockAccount(Long Id, Boolean lock) throws UserNotFoundException {
        User user = userService.queryUserById(Id);
        user.setIs_locked(lock);
        userRepository.flush();
    }

}
