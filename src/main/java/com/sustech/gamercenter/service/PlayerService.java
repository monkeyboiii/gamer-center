package com.sustech.gamercenter.service;

//import com.sustech.gamercenter.dao.PlayerRepository;

import com.sustech.gamercenter.dao.UserRepository;
import com.sustech.gamercenter.dao.projection.FriendView;
import com.sustech.gamercenter.service.token.SimpleTokenService;
import com.sustech.gamercenter.util.exception.InvalidTokenException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlayerService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    SimpleTokenService tokenService;


    public List<FriendView> getPlayerInfo(String token) throws InvalidTokenException {
        return userRepository.userHasFriends(tokenService.getIdByToken(token));
    }
}
