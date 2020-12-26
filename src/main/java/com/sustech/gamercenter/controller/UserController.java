package com.sustech.gamercenter.controller;


import com.sustech.gamercenter.security.AuthToken;
import com.sustech.gamercenter.security.AuthorizationInterceptor;
import com.sustech.gamercenter.service.AdminService;
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


    @GetMapping("/find")
    public JsonResponse getUsersByName(@RequestParam("name") String name) throws UserNotFoundException {
        return new JsonResponse(0, "Success", userService.queryUserByName(name));
    }


    @AuthToken
    @GetMapping("/info")
    public JsonResponse getUserInfo(@RequestHeader("token") String token) throws InvalidTokenException {
        return new JsonResponse(0, "Success", userService.getUserInfo(token));
    }


    @AuthToken
    @GetMapping("/game")
    public JsonResponse userHasGames(@RequestHeader("token") String token,
                                     @RequestParam(value = "tag", defaultValue = "", required = false) String tag,
                                     @RequestParam(value = "page_num", defaultValue = "0", required = false) Integer pageNum,
                                     @RequestParam(value = "page_size", defaultValue = "6", required = false) Integer pageSize
    ) throws InvalidTokenException {
        return new JsonResponse(0, "Successfully retrieved", userService.userHasGamesWithTag(token, tag));
    }


    @AuthToken
    @GetMapping("/game/tag")
    public JsonResponse userHasGameTags(@RequestHeader("token") String token) throws InvalidTokenException {
        return new JsonResponse(0, "Successfully retrieved", userService.userHasGameTags(token));
    }


    @AuthToken
    @PostMapping("/collection/upload")
    public JsonResponse uploadCollection(@RequestHeader("token") String token,
                                         @RequestParam(value = "name", defaultValue = "", required = false) String name,
                                         @RequestParam("type") String type,
                                         @RequestParam("collection") MultipartFile file) throws UploadFileException, InvalidTokenException {
        userService.uploadCollection(token, name, type, file);
        return new JsonResponse(0, "Successfully uploaded");
    }


    @AuthToken
    @GetMapping("/collection")
    public JsonResponse getCollection(@RequestHeader("token") String token,
                                      @RequestParam(value = "type", defaultValue = "", required = false) String type) throws InvalidTokenException {
        System.out.println(userService.getCollection(token, type).size());
        return new JsonResponse(0, "Successfully retrieved", userService.getCollection(token, type));
    }


    @GetMapping(value = "/collection/download/**", produces =
            {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_GIF_VALUE, MediaType.IMAGE_PNG_VALUE})
    public byte[] downloadCollection(HttpServletRequest request) throws IOException {
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
                                      @RequestParam(value = "to", required = false) String to_name,
                                      @RequestParam(value = "to_id", defaultValue = "-1", required = false) Long to_id
    ) throws InvalidTokenException, UserNotFoundException {
        if (!StringUtils.isEmpty(to_name))
            userService.sendMessageTo(token, to_name, type, message);
        else if (to_id != null)
            userService.sendMessageTo(token, to_id, type, message);
        else
            return new JsonResponse(-1, "Either user name or user id is required");

        return new JsonResponse(0, "Successfully sent");
    }

    @AuthToken
    @PostMapping("/message/read")
    public JsonResponse readMessage(@RequestParam("id") Long id) {
        userService.readMessage(id);
        return new JsonResponse(0, "Message marked as read");
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


    @PostMapping("/login/game")
    public JsonResponse SDKUserLogin(
            @RequestParam("email") String email,
            @RequestParam("password") String password,
            @RequestParam("game_id") Long gameId) throws UserNotFoundException, UserAccountLockedException, IncorrectPasswordException, UserHasNoRoleException, GameOwnershipException, InvalidTokenException {
        return new JsonResponse.builder()
                .code(0)
                .msg("Successfully logged in")
                .data(userService.loginAuthentication(email, password, gameId))
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


    @AuthToken
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


    @GetMapping("/other")
    public JsonResponse getUserInfo(@RequestParam("user_id") Long user_id) throws InvalidTokenException {
        return new JsonResponse(0, "Success", userService.getUserInfoForOther(user_id));
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


    @AuthToken
    @PostMapping("/edit/email")
    public JsonResponse changeEmail(@RequestHeader("token") String token,
                                    @RequestParam("email") String email) throws InvalidTokenException, UserNotFoundException, EmailNotSendException {
        userService.changeEmail(token, email);
        return new JsonResponse(0, "Confirmation code sent");
    }


    @AuthToken
    @PostMapping("/edit/email/confirm")
    public JsonResponse changeEmailConfirm(@RequestHeader("token") String token,
                                           @RequestParam("email") String email,
                                           @RequestParam("confirm") String confirmation_code) throws InvalidTokenException, UserNotFoundException, InvalidConfirmationCodeException {
        userService.changeEmailConfirm(token, email, confirmation_code);
        return new JsonResponse(0, "Successfully changed");
    }

    @AuthToken
    @PostMapping("/edit/password")
    public JsonResponse changePassword(@RequestHeader("token") String token,
                                       @RequestParam("old_password") String old_password,
                                       @RequestParam("new_password") String new_password) throws InvalidTokenException, UserNotFoundException, IncorrectPasswordException {
        userService.changePassword(token, old_password, new_password);
        return new JsonResponse(0, "Success");
    }


    @AuthToken
    @PostMapping("/edit/bio")
    public JsonResponse changeBio(@RequestHeader("token") String token,
                                  @RequestParam("bio") String bio) throws UserNotFoundException, InvalidTokenException {
        userService.changeBio(token, bio);
        return new JsonResponse(0, "Success");
    }


    @AuthToken
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


    @GetMapping(value = "/avatar/{id:[0-9]+}", produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_GIF_VALUE})
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


    @GetMapping(value = "/manual", produces = MediaType.APPLICATION_PDF_VALUE)
    public byte[] getManual(@RequestParam(value = "type", defaultValue = "user", required = false) String type) throws IOException {
        return AdminService.getManual(type);
    }


    //
    //
    //
    //
    //


    @AuthToken
    @PostMapping("/comment/report")
    public JsonResponse reportComment(@RequestHeader("token") String token,
                                      @RequestParam("comment_id") Long comment_id,
                                      @RequestParam(value = "reason", defaultValue = "inappropriate content", required = false) String reason) throws InvalidTokenException {
        userService.reportComment(token, comment_id, reason);
        return new JsonResponse(0, "Successfully reported");
    }

}
