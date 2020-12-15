package com.sustech.gamercenter.controller;

import com.sustech.gamercenter.service.AdminService;
import com.sustech.gamercenter.service.UserService;
import com.sustech.gamercenter.util.exception.UserNotFoundException;
import com.sustech.gamercenter.util.exception.UserRegisterException;
import com.sustech.gamercenter.util.model.JsonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    AdminService adminService;

    @Autowired
    UserService userService;


    //    @AuthToken(role = "a")
    @PostMapping("/user/create")
    public JsonResponse createUser(
//            @RequestHeader("token") String token,
            @RequestParam("user_name") String name,
            @RequestParam("user_email") String email,
            @RequestParam("password") String password,
            @RequestParam("role") String role
    ) throws UserRegisterException {
        return new JsonResponse(0, "Successfully created", adminService.createUser(name, email, password, role));
    }


    //@AuthToken(role = "a")
    @GetMapping("/user/info")
    public JsonResponse getUserInfo(
//            @RequestHeader("token") String token,
            @RequestParam(value = "user_id", required = false) Long user_id,
            @RequestParam(value = "user_name", required = false) String name,
            @RequestParam(value = "user_email", required = false) String email
    ) throws UserNotFoundException {
        if (user_id != null) {
            return new JsonResponse(0, "Successfully retrieved", userService.queryUserById(user_id));
        } else if (!StringUtils.isEmpty(name)) {
            return new JsonResponse(0, "Successfully retrieved", userService.queryUserByName(name));
        } else if (!StringUtils.isEmpty(email)) {
            return new JsonResponse(0, "Successfully retrieved", userService.queryUserByEmail(email));
        } else {
            return new JsonResponse(-1, "At least one field is required");
        }
    }


    //    @AuthToken(role = "a")
    @GetMapping("/user")
    public JsonResponse getUserList(
//            @RequestHeader("token") String token,
            @RequestParam(value = "filter", required = false) String filter,
            @RequestParam(value = "value", required = false) String value,
            @RequestParam(value = "page_num", required = false, defaultValue = "0") Integer pageNum,
            @RequestParam(value = "page_size", required = false, defaultValue = "6") Integer pageSize
    ) {
        return new JsonResponse(0, "Successfully retrieved", adminService.getUserList(filter, value, pageNum, pageSize));
    }


    //@AuthToken(role = "a")
    @PostMapping("/user/account/lock")
    public JsonResponse lockOrUnlockAccount(
//            @RequestHeader("token") String token,
            @RequestParam(value = "user_id", required = false) Long user_id,
            @RequestParam(value = "user_name", required = false) String name,
            @RequestParam(value = "user_email", required = false) String email,
            @RequestParam("lock") Boolean lock
    ) throws UserNotFoundException {
        if (user_id != null) {
            adminService.lockOrUnlockAccount("id", user_id.toString(), lock);
        } else if (!StringUtils.isEmpty(name)) {
            adminService.lockOrUnlockAccount("name", name, lock);
        } else if (!StringUtils.isEmpty(email)) {
            adminService.lockOrUnlockAccount("email", email, lock);
        } else {
            return new JsonResponse(-1, "At least one field is required");
        }

        return new JsonResponse(0, "Successfully " + (lock ? "locked" : "unlocked"));
    }


    //@AuthToken(role = "a")
    @PostMapping("/user/assign")
    public JsonResponse assignUserRoleById(
//            @RequestHeader("token") String token,
            @RequestParam("user_id") Long user_id,
            @RequestParam("role") String role
    ) throws UserNotFoundException {
        adminService.assignRole(user_id, role);
        return new JsonResponse(0, "Successfully assigned");
    }


    //
    //
    //
    //
    // manual


    //@AuthToken(role = "a")
    @PostMapping("/manual")
    public JsonResponse uploadUserManual(@RequestParam(value = "type", defaultValue = "user", required = false) String type,
                                         @RequestParam("manual") MultipartFile manual) throws IOException {
        adminService.uploadManual(type, manual);
        return new JsonResponse(0, "Success");
    }

    //
    //
    //
    //
    // community


    //@AuthToken(role = "a")
    @DeleteMapping("/comment")
    public JsonResponse deleteCommentById(
//            @RequestHeader("token") String token,
            @RequestParam("comment_id") Integer comment_id
    ) {
        adminService.deleteCommentById(comment_id);
        return new JsonResponse(0, "Successfully deleted");
    }


    //@AuthToken(role = "a")
    @PostMapping("/comment/visible")
    public JsonResponse setCommentVisibilityById(
//            @RequestHeader("token") String token,
            @RequestParam("comment_id") Integer comment_id,
            @RequestParam("visible") Boolean visible
    ) {
        adminService.setCommentVisibility(comment_id, visible);
        return new JsonResponse(0, "Successfully deleted");
    }


    //@AuthToken(role = "a")
    @DeleteMapping("/community/post")
    public JsonResponse deletePostById(
//            @RequestHeader("token") String token,
            @RequestParam("post_id") Integer post_id
    ) {

        return new JsonResponse(0, "Successfully deleted");
    }


}
