package com.sustech.gamercenter.controller;


import com.sustech.gamercenter.security.AuthToken;
import com.sustech.gamercenter.security.AuthorizationInterceptor;
import com.sustech.gamercenter.service.UserService;
import com.sustech.gamercenter.util.exception.InvalidTokenException;
import com.sustech.gamercenter.util.exception.UserNotFoundException;
import com.sustech.gamercenter.util.model.JsonResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
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
    UserService userService;


    @AuthToken
    @GetMapping("/info")
    public JsonResponse getUserInfo(@RequestHeader("token") String token) throws InvalidTokenException {
        return new JsonResponse(0, "Success", userService.getUserInfo(token));
    }


    @AuthToken
    @GetMapping("/game")
    public JsonResponse userHasGames(@RequestHeader("token") String token,
                                     @RequestParam("tag") String tag,
                                     @RequestParam(value = "page_num", defaultValue = "0", required = false) Integer pageNum,
                                     @RequestParam(value = "page_size", defaultValue = "6", required = false) Integer pageSize
    ) throws InvalidTokenException {
        return new JsonResponse(0, "Successfully retrieved", userService.userHasGamesInTag(token, tag));
    }


    @AuthToken
    @GetMapping("/collection")
    public JsonResponse getCollection(@RequestHeader("token") String token) {
        // todo
        logger.info("token received: " + token);
        return new JsonResponse(0, "Successfully retrieved");
    }


    @AuthToken
    @GetMapping("/account/topup")
    public JsonResponse topUp(@RequestHeader("token") String token,
                              @RequestParam("amount") Double amount
    ) throws UserNotFoundException, InvalidTokenException {
        userService.topUp(token, amount);
        return new JsonResponse(0, "Successfully topped up");
    }


    @AuthToken
    @PostMapping("/friend/request")
    public JsonResponse sendFriendRequest(@RequestHeader("token") String token,
                                          @RequestParam(value = "user_id", required = false) Long id,
                                          @RequestParam(value = "user_name", required = false) String name,
                                          @RequestParam(value = "user_email", required = false) String email) throws UserNotFoundException, InvalidTokenException {
        if (id != null) {
            userService.sendFriendRequest(token, id.toString(), "id");
        } else if (!StringUtils.isEmpty(name)) {
            userService.sendFriendRequest(token, name, "name");
        } else if (!StringUtils.isEmpty(email)) {
            userService.sendFriendRequest(token, email, "email");
        } else {
            return new JsonResponse(-1, "At least one field is required");
        }
        return new JsonResponse(0, "Successfully sent");
    }


    @AuthToken
    @PostMapping("/friend/confirm")
    public JsonResponse confirmFriendRequest(@RequestHeader("token") String token,
                                             @RequestParam("from") Long from) throws InvalidTokenException {
        userService.confirmFriendRequest(token, from);
        return new JsonResponse(0, "Successfully confirmed");
    }


    @AuthToken
    @PostMapping("/message/read")
    public JsonResponse readMessage(@RequestHeader("token") String token,
                                    @RequestParam("id") Long id) throws InvalidTokenException {
        userService.readMessage(id);
        return new JsonResponse(0, "Message marked as read");
    }
}
