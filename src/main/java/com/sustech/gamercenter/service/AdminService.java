package com.sustech.gamercenter.service;

import com.sustech.gamercenter.model.User;
import com.sustech.gamercenter.util.exception.UnauthorizedAttemptException;
import com.sustech.gamercenter.util.exception.UserRegisterException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    @Autowired
    UserService userService;


    public User createUser(String name, String email, String password, String role) throws UserRegisterException, UnauthorizedAttemptException {
        // TODO
        // admin role check
        // only admin can register admin

        return userService.registerUser(name, email, password, role);
    }

}
