package com.sustech.gamercenter.service;

import com.sustech.gamercenter.dao.GameDLCRepository;
import com.sustech.gamercenter.dao.GameRepository;
import com.sustech.gamercenter.dao.UserRepository;
import com.sustech.gamercenter.dao.projection.UserView;
import com.sustech.gamercenter.model.Game;
import com.sustech.gamercenter.model.UserInfo;
import com.sustech.gamercenter.service.token.SimpleTokenService;
import com.sustech.gamercenter.util.exception.GameOwnershipException;
import com.sustech.gamercenter.util.exception.InvalidTokenException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DeveloperService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @Autowired
    GameRepository gameRepository;

    @Autowired
    GameDLCRepository gameDLCRepository;

    @Autowired
    SimpleTokenService tokenService;


    public Game getGameByName(String name) {
        return gameRepository.findByName(name);
    }


    public Game getGameById(Long id) throws FileNotFoundException {
        Optional<Game> game = gameRepository.findById(id);
        if (!game.isPresent()) throw new FileNotFoundException("Game doesn't exist");
        return game.get();
    }


    public List<UserView> getPlayerInfoToGameNoOAuth(String token, Long game_id) throws InvalidTokenException, FileNotFoundException, GameOwnershipException {
        authenticate(tokenService.getIdByToken(token), game_id);
        List<Long> ids = userRepository.getPlayerIdsToGame(game_id);
        return getPlayerView(ids);
    }


    public List<Game> getDevelopersGamesWithTag(String token, String tag) throws InvalidTokenException {
        Long id = tokenService.getIdByToken(token);
        if (StringUtils.isEmpty(tag)) {
            return gameRepository.findAllByDeveloperId(id);
        } else {
            return gameRepository.findAllByDeveloperIdAndTagIgnoreCase(id, tag);
        }
    }


    public void pushNotification(String token, Long game_id, String type, String message, Long user_id) throws InvalidTokenException, FileNotFoundException, GameOwnershipException {
        Long dev = tokenService.getIdByToken(token);
        if (game_id != -1) authenticate(dev, game_id);
        if (user_id == -1) {
            userRepository.getPlayerIdsToGame(game_id).forEach(id -> userRepository.sendMessage(dev, id, type, message));
        } else {
            userRepository.sendMessage(dev, user_id, type, message);
        }
    }


    //
    //
    //
    //
    // helper component


    /**
     * takes in valid, authorized userIds
     **/
    public List<UserInfo> getPlayerInfo(List<Long> userIds) {
        return userIds.stream()
                .map(id -> new UserInfo.builder()
                        .user(userRepository.getOne(id))
                        .friends(userRepository.userHasFriends(id))
                        .games(userRepository.userHasGames(id))
                        .gameDLCs(gameDLCRepository.userHasGameDLCs(id))
                        .build())
                .collect(Collectors.toList());
    }


    private List<UserView> getPlayerView(List<Long> userIds) {
        return userIds.stream()
                .map(id -> userRepository.getUserViewById(id))
                .collect(Collectors.toList());
    }


    /**
     * authenticate the dev game ownership
     **/
    public void authenticate(Long dev, Long gameId) throws FileNotFoundException, GameOwnershipException {
        Optional<Game> game = gameRepository.findById(gameId);
        if (!game.isPresent()) throw new FileNotFoundException("Game doesn't exist");
        if (game.get().getDeveloperId() != dev) throw new GameOwnershipException("Game doesn't belong to you");
    }


}
