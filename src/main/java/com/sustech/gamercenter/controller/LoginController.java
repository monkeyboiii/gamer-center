package com.sustech.gamercenter.controller;

import com.sustech.gamercenter.security.AuthToken;
import com.sustech.gamercenter.service.token.SimpleTokenService;
import com.sustech.gamercenter.service.token.SimpleTokenServiceImpl;
import com.sustech.gamercenter.util.exception.IncorrectPasswordException;
import com.sustech.gamercenter.util.exception.InvalidTokenException;
import com.sustech.gamercenter.util.exception.UserHasNoTokenException;
import com.sustech.gamercenter.util.exception.UserNotFoundException;
import com.sustech.gamercenter.util.model.JsonResponse;
import com.sustech.gamercenter.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class LoginController {

    @Autowired
    LoginService loginService;

    @Autowired
    SimpleTokenService simpleTokenService; // alternative to TokenService


    @PostMapping("/login")
    public JsonResponse loginAuthentication(
            @RequestParam("email") String email,
            @RequestParam("password") String password) throws UserNotFoundException, IncorrectPasswordException {
        return new JsonResponse.builder()
                .code(0)
                .msg("Successfully logged in")
                .data(loginService.loginAuthentication(email, password))
                .build();
    }

    @AuthToken
    @PostMapping("/logout")
    public JsonResponse logout(@RequestHeader("token") String token) throws UserHasNoTokenException, InvalidTokenException {
        loginService.logout(token);
        return new JsonResponse(0, "Successfully logged out");
    }
}
