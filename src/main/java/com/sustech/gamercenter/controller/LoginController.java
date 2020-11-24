package com.sustech.gamercenter.controller;

import com.sustech.gamercenter.security.AuthToken;
import com.sustech.gamercenter.service.LoginService;
import com.sustech.gamercenter.service.token.SimpleTokenService;
import com.sustech.gamercenter.util.exception.*;
import com.sustech.gamercenter.util.model.JsonResponse;
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
            @RequestParam("password") String password,
            @RequestParam(value = "role", required = false, defaultValue = "p") String role
    ) throws UserNotFoundException, IncorrectPasswordException, UserHasNoRoleException {
        return new JsonResponse.builder()
                .code(0)
                .msg("Successfully logged in")
                .data(loginService.loginAuthentication(email, password, role))
                .build();
    }

    @AuthToken
    @PostMapping("/logout")
    public JsonResponse logout(@RequestHeader("token") String token) throws UserHasNoTokenException, InvalidTokenException {
        loginService.logout(token);
        return new JsonResponse(0, "Successfully logged out");
    }
}
