package com.sustech.gamercenter.service;

import com.sustech.gamercenter.dao.UserRepository;
import com.sustech.gamercenter.dao.projection.UserView;
import com.sustech.gamercenter.service.token.SimpleTokenService;
import com.sustech.gamercenter.util.exception.InvalidTokenException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeveloperService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    SimpleTokenService tokenService;

    public boolean validateOAuthToken(String oAuthToken) {
        userRepository.validateOAuthToken(oAuthToken);
        return true;
    }

    public void getPlayerInfo(String oAuthToken) {

    }

    public List<UserView> getPlayerToGame(String token, Long game_id) throws InvalidTokenException {
        Long dev = tokenService.getIdByToken(token);
        return userRepository.getPlayerToGame(dev, game_id);
    }

    public List<UserView> getPlayerInfoNoOAuth(String token, Long user_id, Long game_id) {
        return null;
    }

    public List<UserView> getPlayerInfoBatchNoOAuth(String token, List<Long> user_id, Long game_id) {
        return null;
    }
}
