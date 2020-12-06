package com.sustech.gamercenter.controller;

import com.sustech.gamercenter.security.AuthToken;
import com.sustech.gamercenter.service.UserService;
import com.sustech.gamercenter.util.exception.*;
import com.sustech.gamercenter.util.model.JsonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user/edit")
public class UserEditController {

    @Autowired
    UserService userService;


    @AuthToken
    @PostMapping("/email")
    public JsonResponse changeEmail(@RequestHeader("token") String token,
                                    @RequestParam("email") String email) throws InvalidTokenException, UserNotFoundException, EmailNotSendException {
        userService.changeEmail(token, email);
        return new JsonResponse(0, "Success");
    }


    @AuthToken
    @PostMapping("/email/confirm")
    public JsonResponse changeEmailConfirm(@RequestHeader("token") String token,
                                           @RequestParam("email") String email,
                                           @RequestParam("confirm") String confirmation_code) throws InvalidTokenException, UserNotFoundException, InvalidConfirmationCodeException {
        userService.changeEmailConfirm(token, email, confirmation_code);
        return new JsonResponse(0, "Success");
    }

    @AuthToken
    @PostMapping("/password")
    public JsonResponse changePassword(@RequestHeader("token") String token,
                                       @RequestParam("old_password") String old_password,
                                       @RequestParam("new_password") String new_password) throws InvalidTokenException, UserNotFoundException, IncorrectPasswordException {
        userService.changePassword(token, old_password, new_password);
        return new JsonResponse(0, "Success");
    }


    @AuthToken
    @PostMapping("/bio")
    public JsonResponse changeBio(@RequestHeader("token") String token,
                                  @RequestParam("bio") String bio) throws UserNotFoundException, InvalidTokenException {
        userService.changeBio(token, bio);
        return new JsonResponse(0, "Success");
    }


    @AuthToken(role = "p")
    @PostMapping("/topup")
    public JsonResponse changeBalance(@RequestHeader("token") String token,
                              @RequestParam("amount") Double amount
    ) throws UserNotFoundException, InvalidTokenException {
        return new JsonResponse(0, "Successfully topped up", userService.changeBalance(token, amount));
    }


}
