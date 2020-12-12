package com.sustech.gamercenter.controller;

import com.sustech.gamercenter.security.AuthToken;
import com.sustech.gamercenter.service.DeveloperService;
import com.sustech.gamercenter.util.exception.InvalidTokenException;
import com.sustech.gamercenter.util.model.JsonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/developer")
public class DeveloperController {

    @Autowired
    DeveloperService developerService;


    @AuthToken(role = "d")
    @GetMapping("/player/info/unsafe")
    public JsonResponse getPlayerInfoNoOAuth(@RequestHeader("token") String token,
                                             @RequestParam("user_id") Long user_id,
                                             @RequestParam("game_id") Long game_id) {
        return new JsonResponse(0, "Success", developerService.getPlayerInfoNoOAuth(token, user_id, game_id));
    }

    @AuthToken(role = "d")
    @GetMapping("/player/info/batch/unsafe")
    public JsonResponse getPlayerInfoNoOAuth(@RequestHeader("token") String token,
                                             @RequestParam("user_ids") List<Long> user_ids,
                                             @RequestParam("game_id") Long game_id) {
        return new JsonResponse(0, "Success", developerService.getPlayerInfoBatchNoOAuth(token, user_ids, game_id));
    }

    @AuthToken(role = "d")
    @GetMapping("/player/to/game")
    public JsonResponse getPlayerToGame(@RequestHeader("token") String token,
                                        @RequestParam("game_id") Long game_id) throws InvalidTokenException {
        return new JsonResponse(0, "Success", developerService.getPlayerToGame(token, game_id));
    }


    //
    //
    //
    //
    //


    @AuthToken(role = "d")
    @GetMapping("/player/info")
    public JsonResponse getPlayerInfo(@RequestParam("oauth") String oathToken) {

        return new JsonResponse(0, "success");
    }


    @AuthToken(role = "d")
    @GetMapping("/player/info/batch")
    public JsonResponse getPlayerInfoInBatch(@RequestParam("oauths") List<String> tokens) {
        return new JsonResponse(0, "success");
    }

}
