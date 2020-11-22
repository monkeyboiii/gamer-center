package com.sustech.gamercenter.service;

import com.sustech.gamercenter.dao.PlayerRepository;
import com.sustech.gamercenter.model.Player;
import com.sustech.gamercenter.service.token.SimpleTokenService;
import com.sustech.gamercenter.util.exception.InvalidTokenException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlayerService {

    @Autowired
    PlayerRepository playerRepository;

    @Autowired
    SimpleTokenService tokenService;


    public Player getPlayerInfo(String token) throws InvalidTokenException {
        Player player = playerRepository.getOne(tokenService.getIdByToken(token));
        return player;
    }
}
