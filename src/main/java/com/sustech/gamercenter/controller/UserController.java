package com.sustech.gamercenter.controller;


import com.sustech.gamercenter.security.AuthToken;
import com.sustech.gamercenter.security.AuthorizationInterceptor;
import com.sustech.gamercenter.service.UserService;
import com.sustech.gamercenter.util.exception.*;
import com.sustech.gamercenter.util.model.JsonResponse;
import com.sustech.gamercenter.util.model.ResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
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
 * and processed with role based authorization model
 **/
@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    UserService userService;


    @AuthToken()
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
    @PostMapping("/collection/upload")
    public JsonResponse uploadCollection(@RequestHeader("token") String token,
                                         @RequestParam(value = "name", defaultValue = "", required = false) String name,
                                         @RequestParam("type") String type,
                                         @RequestParam("collection") MultipartFile file) throws UploadFileException, InvalidTokenException {
        userService.uploadCollection(token, name, type, file);
        return new JsonResponse(0, "Successfully uploaded");
    }


    @AuthToken(role = "p")
    @GetMapping("/collection")
    public JsonResponse getCollection(@RequestHeader("token") String token,
                                      @RequestParam(value = "type", defaultValue = "", required = false) String type) throws InvalidTokenException {
        System.out.println(userService.getCollection(token, type).size());
        return new JsonResponse(0, "Successfully retrieved", userService.getCollection(token, type));
    }


    @GetMapping(value = "/collection/download/**", produces =
            {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_GIF_VALUE, MediaType.IMAGE_PNG_VALUE})
    public byte[] moduleStrings(HttpServletRequest request) throws IOException {
//    public byte[] downloadCollection(@RequestParam("path") String path) throws IOException {
        String requestURL = request.getRequestURL().toString();
        String path = requestURL.split("/collection/download/")[1];
        return userService.downloadCollection(path);
    }


    //
    //
    //
    //
    // message


    @AuthToken
    @PostMapping("/friend/chat")
    public JsonResponse sendMessageTo(@RequestHeader("token") String token,
                                      @RequestParam(value = "type", defaultValue = "chat", required = false) String type,
                                      @RequestParam("message") String message,
                                      @RequestParam("to") String to_name) throws InvalidTokenException, UserNotFoundException {
        userService.sendMessageTo(token, to_name, type, message);
        return new JsonResponse(0, "Successfully sent");
    }

    @AuthToken(role = "p")
    @PostMapping("/message/read")
    public JsonResponse readMessage(@RequestParam("id") Long id) {
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
    ) throws UserNotFoundException, IncorrectPasswordException, UserHasNoRoleException, UserAccountLockedException {
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
    // oauth


    @AuthToken(role = "p")
    @PostMapping("/oauth")
    public JsonResponse oAuth(@RequestHeader("token") String token,
                              @RequestParam("game_id") Long game_id) throws InvalidTokenException {
        return new JsonResponse(0, "Successfully created", userService.createOAuthToken(token, game_id));
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
    ) throws UserRegisterException, UnauthorizedAttemptException, EmailNotSendException {
        userService.registerUser(name, email, password, role);
        return new JsonResponse(0, "Confirmation code sent");
    }


    @PostMapping("/register/confirm")
    public JsonResponse registerConfirm(@RequestParam("email") String email,
                                        @RequestParam("confirm") String confirmationCode) throws InvalidConfirmationCodeException, UserNotFoundException {
        userService.registerUserConfirm(email, confirmationCode);
        return new JsonResponse(0, "Successfully registered");
    }


    @PostMapping("/register/resend")
    public JsonResponse resendRegisterConfirm(@RequestParam("email") String email) throws UserNotFoundException, EmailNotSendException {
        userService.resendRegisterConfirm(email);
        return new JsonResponse(0, "Confirmation code resent");
    }


    //
    //
    //
    //
    // edit


    @AuthToken(role = "p")
    @PostMapping("/edit/email")
    public JsonResponse changeEmail(@RequestHeader("token") String token,
                                    @RequestParam("email") String email) throws InvalidTokenException, UserNotFoundException, EmailNotSendException {
        userService.changeEmail(token, email);
        return new JsonResponse(0, "Confirmation code sent");
    }


    @AuthToken(role = "p")
    @PostMapping("/edit/email/confirm")
    public JsonResponse changeEmailConfirm(@RequestHeader("token") String token,
                                           @RequestParam("email") String email,
                                           @RequestParam("confirm") String confirmation_code) throws InvalidTokenException, UserNotFoundException, InvalidConfirmationCodeException {
        userService.changeEmailConfirm(token, email, confirmation_code);
        return new JsonResponse(0, "Successfully changed");
    }

    @AuthToken(role = "p")
    @PostMapping("/edit/password")
    public JsonResponse changePassword(@RequestHeader("token") String token,
                                       @RequestParam("old_password") String old_password,
                                       @RequestParam("new_password") String new_password) throws InvalidTokenException, UserNotFoundException, IncorrectPasswordException {
        userService.changePassword(token, old_password, new_password);
        return new JsonResponse(0, "Success");
    }


    @AuthToken(role = "p")
    @PostMapping("/edit/bio")
    public JsonResponse changeBio(@RequestHeader("token") String token,
                                  @RequestParam("bio") String bio) throws UserNotFoundException, InvalidTokenException {
        userService.changeBio(token, bio);
        return new JsonResponse(0, "Success");
    }


    @AuthToken(role = "p")
    @PostMapping("/edit/balance")
    public JsonResponse changeBalance(@RequestHeader("token") String token,
                                      @RequestParam("amount") Double amount
    ) throws UserNotFoundException, InvalidTokenException {
        return new JsonResponse(0, "Successfully topped up", userService.changeBalance(token, amount));
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


}
