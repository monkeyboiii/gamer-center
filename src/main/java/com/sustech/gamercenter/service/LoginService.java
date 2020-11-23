package com.sustech.gamercenter.service;

import com.sustech.gamercenter.model.User;
import com.sustech.gamercenter.service.token.SimpleTokenService;
import com.sustech.gamercenter.util.exception.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    /**
     * necessary, still in dev process
     **/
    private static final Logger logger = LoggerFactory.getLogger(LoginService.class);

    @Autowired
    private UserService userService;

    @Autowired
    private SimpleTokenService tokenService;

    @Autowired
    private PasswordEncoder encoder;


    public String loginAuthentication(String email, String password, String role) throws UserNotFoundException, IncorrectPasswordException, UserHasNoRoleException {
        User user = userService.queryUserByEmail(email);
        if (role.length() != 1 || !user.getRole().contains(role)) {
            throw new UserHasNoRoleException("User " + user.getName() + " has no such role");
        } else if (encoder.matches(password, user.getPassword())) {
            return tokenService.createToken(user);
        } else {
            throw new IncorrectPasswordException("Password incorrect");
        }
    }


    public void logout(String token) throws UserHasNoTokenException, InvalidTokenException {
        logger.warn("pretend that this token " + token + " is destroyed or something");
        tokenService.deleteToken(token);
    }

}
