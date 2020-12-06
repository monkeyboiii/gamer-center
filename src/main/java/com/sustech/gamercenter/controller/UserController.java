package com.sustech.gamercenter.controller;


import com.sustech.gamercenter.security.AuthToken;
import com.sustech.gamercenter.security.AuthorizationInterceptor;
import com.sustech.gamercenter.service.UserService;
import com.sustech.gamercenter.util.exception.*;
import com.sustech.gamercenter.util.model.JsonResponse;
import com.sustech.gamercenter.util.model.ResultService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


/**
 * for methods without {@link AuthToken} annotation:
 * no login required.
 * throws custom exceptions defined in {@link com.sustech.gamercenter.util.exception}
 * that can be caught in {@link com.sustech.gamercenter.util.config.ExceptionHandlerConfig}
 * <p>
 * for methods with {@link AuthToken} annotation:
 * login/token required.
 * intercepted by {@link AuthorizationInterceptor}
 * and processed with role role based authorization model
 **/
@RestController
@RequestMapping("/api/user")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    UserService userService;


    @AuthToken(role = "p")
    @GetMapping("/info")
    public JsonResponse getUserInfo(@RequestHeader("token") String token) throws InvalidTokenException {
        return new JsonResponse(0, "Success", userService.getUserInfo(token));
    }


    @AuthToken(role = "p")
    @GetMapping("/game")
    public JsonResponse userHasGames(@RequestHeader("token") String token,
                                     @RequestParam(value = "tag", defaultValue = "", required = false) String tag,
                                     @RequestParam(value = "page_num", defaultValue = "0", required = false) Integer pageNum,
                                     @RequestParam(value = "page_size", defaultValue = "6", required = false) Integer pageSize
    ) throws InvalidTokenException {
        return new JsonResponse(0, "Successfully retrieved", userService.userHasGamesWithTag(token, tag));
    }


    @AuthToken(role = "p")
    @GetMapping("/game/tag")
    public JsonResponse userHasGameTags(@RequestHeader("token") String token) throws InvalidTokenException {
        return new JsonResponse(0, "Successfully retrieved", userService.userHasGameTags(token));
    }


    @AuthToken(role = "p")
    @GetMapping("/collection")
    public JsonResponse getCollection(@RequestHeader("token") String token) {
        // todo
        logger.info("token received: " + token);
        return new JsonResponse(0, "Successfully retrieved");
    }


    //
    //
    //
    //
    // message


    @AuthToken
    @PostMapping("/friend/chat")
    public JsonResponse sendMessageTo(@RequestHeader("token") String token,
                                      @RequestParam("message") String message,
                                      @RequestParam("to") Long to) throws InvalidTokenException {
        userService.sendChatTo(token, to, message);
        return new JsonResponse(0, "Successfully sent");
    }

    @AuthToken(role = "p")
    @PostMapping("/message/read")
    public JsonResponse readMessage(@RequestHeader("token") String token,
                                    @RequestParam("id") Long id) throws InvalidTokenException {
        userService.readMessage(id);
        return new JsonResponse(0, "Message marked as read");
    }


    @AuthToken(role = "p")
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


    @AuthToken(role = "p")
    @PostMapping("/friend/confirm")
    public JsonResponse confirmFriendRequest(@RequestHeader("token") String token,
                                             @RequestParam("from") Long from) throws InvalidTokenException {
        userService.confirmFriendRequest(token, from);
        return new JsonResponse(0, "Successfully confirmed");
    }


    //
    //
    //
    //
    // log in/out


    @PostMapping("/login")
    public JsonResponse loginAuthentication(
            @RequestParam("email") String email,
            @RequestParam("password") String password,
            @RequestParam(value = "role", required = false, defaultValue = "p") String role
    ) throws UserNotFoundException, IncorrectPasswordException, UserHasNoRoleException {
        return new JsonResponse.builder()
                .code(0)
                .msg("Successfully logged in")
                .data(userService.loginAuthentication(email, password, role))
                .build();
    }


    @AuthToken
    @PostMapping("/logout")
    public JsonResponse logout(@RequestHeader("token") String token) throws UserHasNoTokenException, InvalidTokenException {
        userService.logout(token);
        return new JsonResponse(0, "Successfully logged out");
    }


    //
    //
    //
    //
    // avatar


    @GetMapping(value = "/avatar/{id:[0-9]+}", produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] getAvatar(@PathVariable("id") String id) throws IOException {
        return userService.getAvatar(id);
    }


    @AuthToken
    @PostMapping("/edit/avatar")
    public Object uploadAvatar(@RequestHeader("token") String token,
                               @RequestParam("avatar") MultipartFile avatar) throws UserNotFoundException, InvalidTokenException, IOException {
        userService.uploadAvatar(token, avatar);
        return ResultService.success("");
    }


    //
    //
    //
    //
    // guest


    @GetMapping
    public JsonResponse getUserInfo(@RequestParam(value = "user_id", required = false) Long user_id,
                                    @RequestParam(value = "user_name", required = false) String name,
                                    @RequestParam(value = "user_email", required = false) String email
    ) throws UserNotFoundException {
        // TODO return less information
        if (user_id != null) {
            return new JsonResponse(0, "Success", userService.queryUserById(user_id));
        } else if (!StringUtils.isEmpty(name)) {
            return new JsonResponse(0, "Success", userService.queryUserByName(name));
        } else if (!StringUtils.isEmpty(email)) {
            return new JsonResponse(0, "Success", userService.queryUserByEmail(email));
        } else {
            return new JsonResponse(-1, "At least one field is required");
        }
    }


    @PostMapping("/register")
    public JsonResponse register(@RequestParam("name") String name,
                                 @RequestParam("email") String email,
                                 @RequestParam("password") String password,
                                 @RequestParam(value = "role", defaultValue = "p", required = false) String role
    ) throws UserRegisterException, UnauthorizedAttemptException {
        userService.registerUser(name, email, password, role);
        return new JsonResponse(0, "Successful registered");
    }


    @PostMapping("/register/confirm")
    public JsonResponse registerConfirm(@RequestParam("email") String enail,
                                        @RequestParam("confirm") String confirmationCode) {
        // confirmationCode in email
        return new JsonResponse(0, "No impl. Successfully registered");
    }

}
