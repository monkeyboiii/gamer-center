package com.sustech.gamercenter.controller;

import com.sustech.gamercenter.model.JsonResponse;
import com.sustech.gamercenter.model.User;
import com.sustech.gamercenter.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/api/user")
public class UserController {

    private static Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserService userService;


    @GetMapping
    public JsonResponse getUserInfo(@RequestParam("user_id") Long user_id,
                                    @RequestParam("user_name") String name,
                                    @RequestParam("user_email") String email
    ) {
        User user1 = userService.queryUserById(user_id);
        User user2 = userService.queryUserByName(name);
        User user3 = userService.queryUserByEmail(email);

        List<User> list = new ArrayList<>();
        list.add(user1);
        list.add(user2);
        list.add(user3);
        return new JsonResponse(0, "Success", list);
    }


    @GetMapping("/info")
    public JsonResponse getFullUserInfo(@RequestParam("token") String token) {
        return new JsonResponse(0, "Success");
    }


    @PostMapping("/register")
    public JsonResponse register(@RequestParam("name") String name,
                                 @RequestParam("email") String email,
                                 @RequestParam("password") String password,
                                 @RequestParam("role") String role
    ) {
        logger.info(password);
//        logger.info(String.valueOf(avatar.canRead()));
        userService.registerUser(new User(name,email,password,role));
        return new JsonResponse(0, "Successful registered");
    }


    @PostMapping("/logout")
    public JsonResponse logout(@RequestParam("token") String token) {
        // TODO
        return new JsonResponse(0, "Successfully logged out");
    }
}
