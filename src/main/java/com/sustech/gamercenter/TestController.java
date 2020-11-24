package com.sustech.gamercenter;

import com.sustech.gamercenter.dao.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    UserRepository userRepository;


    @GetMapping("/game")
    public Object userHasGames(@RequestParam("user_id") Long id) {
        return userRepository.userHasGames(id);
    }


}
