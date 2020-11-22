package com.sustech.gamercenter.controller;


import com.sustech.gamercenter.security.AuthToken;
import com.sustech.gamercenter.security.AuthorizationInterceptor;
import com.sustech.gamercenter.service.UserService;
import com.sustech.gamercenter.service.token.SimpleTokenService;
import com.sustech.gamercenter.util.exception.InvalidTokenException;
import com.sustech.gamercenter.util.exception.UserNotFoundException;
import com.sustech.gamercenter.util.model.JsonResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * login/token required
 * <p>
 * intercepted by {@link AuthorizationInterceptor}
 * and processed with role role based authorization model
 **/
@RestController
@RequestMapping("/api/user")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    UserService userService; // dummy

    @Autowired
    SimpleTokenService tokenService;

    @AuthToken
    @GetMapping("/info")
    public JsonResponse getFullUserInfo(@RequestHeader("token") String token) {
        logger.info("token received: " + token);
        return new JsonResponse(0, "Success");
    }


    @AuthToken
    @GetMapping("/game")
    public JsonResponse userHasGames(@RequestHeader("token") String token,
                                     @RequestParam("tag") String tag
    ) {
        logger.info("token received: " + token);
        logger.info("tag received: " + tag);
        return new JsonResponse(0, "Successfully retrieved");
    }


    @AuthToken
    @GetMapping("/collection")
    public JsonResponse getCollection(@RequestHeader("token") String token) {
        logger.info("token received: " + token);
        return new JsonResponse(0, "Successfully retrieved");
    }


    @AuthToken
    @GetMapping("/account/topup")
    public JsonResponse topUpAccount(@RequestHeader("token") String token,
                                     @RequestParam("amount") Double amount
    ) throws UserNotFoundException, InvalidTokenException {
        userService.topup(token,amount);
        return new JsonResponse(0, "Successfully topped up");
    }

}
