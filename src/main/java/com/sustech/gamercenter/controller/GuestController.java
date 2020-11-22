package com.sustech.gamercenter.controller;

import com.sustech.gamercenter.service.UserService;
import com.sustech.gamercenter.util.exception.UnauthorizedAttemptException;
import com.sustech.gamercenter.util.exception.UserNotFoundException;
import com.sustech.gamercenter.util.exception.UserRegisterException;
import com.sustech.gamercenter.util.model.JsonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;


/**
 * no login required
 * <p>
 * throws custom exceptions defined in {@link com.sustech.gamercenter.util.exception}
 * that can be caught in {@link com.sustech.gamercenter.util.config.ExceptionHandlerConfig}
 **/
@RestController
@RequestMapping("/api/user")
public class GuestController {

    @Autowired
    UserService userService;


    @GetMapping
    public JsonResponse getUserInfo(@RequestParam(value = "user_id", required = false) Long user_id,
                                    @RequestParam(value = "user_name", required = false) String name,
                                    @RequestParam(value = "user_email", required = false) String email
    ) throws UserNotFoundException {
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
                                 @RequestParam("role") String role
    ) throws UserRegisterException, UnauthorizedAttemptException {
        userService.registerUser(name, email, password, role);
        return new JsonResponse(0, "Successful registered");
    }

}

