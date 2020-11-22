package com.sustech.gamercenter.controller;

import com.sustech.gamercenter.security.AuthToken;
import com.sustech.gamercenter.service.AdminService;
import com.sustech.gamercenter.util.exception.UnauthorizedAttemptException;
import com.sustech.gamercenter.util.exception.UserRegisterException;
import com.sustech.gamercenter.util.model.JsonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    AdminService adminService; // dummy


    @AuthToken
    @PostMapping("/user/create")
    public JsonResponse createUser(@RequestHeader("token") String token,
                                   @RequestParam("user_name") String name,
                                   @RequestParam("user_email") String email,
                                   @RequestParam("password") String password,
                                   @RequestParam("role") String role
    ) throws UserRegisterException, UnauthorizedAttemptException {
        return new JsonResponse(0, "Successfully created", adminService.createUser(name, email, password, role));
    }


    @AuthToken
    @GetMapping("/user")
    public JsonResponse getUserList(@RequestHeader("token") String token,
                                    @RequestParam(value = "filter", required = false) String filter,
                                    @RequestParam(value = "value", required = false) String value,
                                    @RequestParam(value = "page", required = false) Integer page
    ) {

        return new JsonResponse(0, "Successfully retrieved");
    }


    @AuthToken
    @GetMapping("/user/info")
    public JsonResponse getUserInfo(@RequestHeader("token") String token,
                                    @RequestParam(value = "user_id", required = false) Integer user_id,
                                    @RequestParam(value = "user_name", required = false) String name,
                                    @RequestParam(value = "user_email", required = false) String email
    ) {

        return new JsonResponse(0, "Successfully retrieved");
    }


    @AuthToken
    @GetMapping("/user/account/lock")
    public JsonResponse lockOrUnlcokAccount(@RequestHeader("token") String token,
                                            @RequestParam(value = "user_id", required = false) Integer user_id,
                                            @RequestParam(value = "user_name", required = false) String name,
                                            @RequestParam(value = "user_email", required = false) String email,
                                            @RequestParam("lock") Boolean lock
    ) {

        return new JsonResponse(0, "Successfully " + (lock ? "locked" : "unlocked"));
    }


    @AuthToken
    @PostMapping("/user/assign")
    public JsonResponse assignUserRoleById(@RequestHeader("token") String token,
                                           @RequestParam("user_id") Integer user_id,
                                           @RequestParam("role") String role
    ) {

        return new JsonResponse(0, "Successfully assigned");
    }


    //
    //
    //
    //
    // community


    @AuthToken
    @DeleteMapping("/community/comment")
    public JsonResponse deleteCommentById(@RequestHeader("token") String token,
                                          @RequestParam("comment_id") Integer comment_id
    ) {

        return new JsonResponse(0, "Successfully deleted");
    }


    @AuthToken
    @DeleteMapping("/community/post")
    public JsonResponse deletePostById(@RequestHeader("token") String token,
                                       @RequestParam("post_id") Integer post_id
    ) {

        return new JsonResponse(0, "Successfully deleted");
    }


}
