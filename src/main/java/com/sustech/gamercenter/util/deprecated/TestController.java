package com.sustech.gamercenter.util.deprecated;

import com.sustech.gamercenter.dao.MessageRepository;
import com.sustech.gamercenter.dao.UserRepository;
import com.sustech.gamercenter.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    MessageRepository messageRepository;

    @Autowired
    UserService userService;

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


}
