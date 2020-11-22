package com.sustech.gamercenter.controller;

import com.sustech.gamercenter.security.AuthToken;
import com.sustech.gamercenter.service.UserService;
import com.sustech.gamercenter.util.exception.IncorrectPasswordException;
import com.sustech.gamercenter.util.exception.InvalidTokenException;
import com.sustech.gamercenter.util.exception.UserNotFoundException;
import com.sustech.gamercenter.util.model.JsonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user/edit")
public class UserEditController {

    @Autowired
    UserService userService; // need revision


    @AuthToken
    @PostMapping("/password")
    public JsonResponse changePassword(@RequestHeader("token") String token,
                                       @RequestParam("old_password") String old_password,
                                       @RequestParam("new_password") String new_password) throws InvalidTokenException, UserNotFoundException, IncorrectPasswordException {
        userService.changePassword(token, old_password, new_password);
        return new JsonResponse(0, "Success");
    }


    @AuthToken
    @PostMapping("/email")
    public JsonResponse changeEmail(@RequestHeader("token") String token,
                                    @RequestParam("email") String email) throws InvalidTokenException, UserNotFoundException {
        userService.changeEmail(token, email);
        return new JsonResponse(0, "Success");
    }


    @AuthToken
    @PostMapping("/email/confirm")
    public JsonResponse changeEmailConfirm(@RequestHeader("token") String token,
                                           @RequestParam("email") String email,
                                           @RequestParam("confirm") String confirmation_code) throws InvalidTokenException, UserNotFoundException {
        userService.changeEmailConfirm(token, email, confirmation_code);
        return new JsonResponse(0, "Success");
    }


    @AuthToken
    @PostMapping("/bio")
    public JsonResponse changeBio(@RequestHeader("token") String token,
                                  @RequestParam("bio") String bio) throws UserNotFoundException, InvalidTokenException {
        userService.changeBio(token, bio);
        return new JsonResponse(0, "Success");
    }
}
