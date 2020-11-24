package com.sustech.gamercenter.controller;

import com.sustech.gamercenter.security.AuthToken;
import com.sustech.gamercenter.service.UserService;
import com.sustech.gamercenter.util.exception.InvalidTokenException;
import com.sustech.gamercenter.util.exception.UserNotFoundException;
import com.sustech.gamercenter.util.model.JsonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequestMapping("/api/user")
public class AvatarController {

    @Autowired
    UserService userService;

    @GetMapping(value = "/avatar/{id:[0-9]+}", produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] getAvatar(@PathVariable("id") String id) throws IOException {
        return userService.getAvatar(id);
    }

    @AuthToken
    @PostMapping("/edit/avatar")
    @ResponseBody
    public JsonResponse uploadAvatar(@RequestHeader("token") String token,
                                     @RequestParam("avatar") MultipartFile avatar) throws UserNotFoundException, InvalidTokenException, IOException {
        userService.uploadAvatar(token, avatar);
        return new JsonResponse(0, "Successfully uploaded");
    }

}
