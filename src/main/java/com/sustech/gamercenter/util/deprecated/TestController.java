package com.sustech.gamercenter.util.deprecated;

import com.sustech.gamercenter.dao.MessageRepository;
import com.sustech.gamercenter.dao.UserRepository;
import com.sustech.gamercenter.service.AdminService;
import com.sustech.gamercenter.service.MailService;
import com.sustech.gamercenter.service.UserService;
import com.sustech.gamercenter.util.exception.EmailNotSendException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    MessageRepository messageRepository;

    @Autowired
    UserService userService;

    @Autowired
    AdminService adminService;

    @Autowired
    MailService mailService;


    //
    //
    //
    //
    //


    @GetMapping("/game")
    public Object userHasGames(@RequestParam("user_id") Long id) {
        return userRepository.userHasGames(id);
    }

    @GetMapping("/message")
    public Object userHasMessage(@RequestParam("user_id") Long id) {
        return messageRepository.findAllByUserIdAndUnread(id, true);
    }

    @GetMapping("/message/read")
    public Object userHasNotMessage(@RequestParam("user_id") Long id) {
        return messageRepository.findAllByUnreadIsFalse();
    }

    @PostMapping("/friend")
    public Object friendReq() {
        userRepository.friendRequest(15732L, 15734L);
        return "ok";
    }

    @PostMapping("/manual")
    public Object manual(@RequestParam("manual") MultipartFile manual) throws IOException {
        adminService.uploadManual("user", manual);
        return "agf";
    }

    @PostMapping("/mail")
    public Object mailTest() throws EmailNotSendException {
        mailService.sendMail("11813010@mail.sustech.edu.cn", "hello", "spring boot says hello");
        return "Sent";
    }


    //


    @GetMapping
    public byte[] testManula() throws IOException {
        return AdminService.getManual("user");
    }

}
