package com.sustech.gamercenter.controller;

import com.sustech.gamercenter.security.AuthToken;
import com.sustech.gamercenter.service.DeveloperService;
import com.sustech.gamercenter.util.exception.GameOwnershipException;
import com.sustech.gamercenter.util.exception.InvalidTokenException;
import com.sustech.gamercenter.util.model.JsonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;

@RestController
@RequestMapping("/api/developer")
public class DeveloperController {

    @Autowired
    DeveloperService developerService;


    @AuthToken(role = "d")
    @GetMapping("/game")
    public JsonResponse getSpecificGame(@RequestParam(value = "name", required = false) String name,
                                        @RequestParam(value = "id", required = false) Long id) throws FileNotFoundException {
        if (!StringUtils.isEmpty(name))
            return new JsonResponse(0, "Success", developerService.getGameByName(name));
        else if (id != null)
            return new JsonResponse(0, "Success", developerService.getGameById(id));
        else
            return new JsonResponse(-1, "Parameters missing");
    }


    @AuthToken(role = "d")
    @GetMapping("/games")
    public JsonResponse getDevelopersGames(@RequestHeader("token") String token,
                                           @RequestParam(value = "tag", defaultValue = "", required = false) String tag) throws InvalidTokenException {
        return new JsonResponse(0, "Success", developerService.getDevelopersGamesWithTag(token, tag));
    }


    @AuthToken(role = "d")
    @GetMapping("/player/to/game")
    public JsonResponse getPlayerToGame(@RequestHeader("token") String token,
                                        @RequestParam("game_id") Long game_id) throws InvalidTokenException, FileNotFoundException, GameOwnershipException {
        return new JsonResponse(0, "Success", developerService.getPlayerInfoToGameNoOAuth(token, game_id));
    }


    @AuthToken(role = "d")
    @PostMapping("/player/notify")
    public JsonResponse pushNotification(@RequestHeader("token") String token,
                                         @RequestParam(value = "game_id", defaultValue = "-1", required = false) Long game_id,
                                         @RequestParam(value = "type", defaultValue = "notification", required = false) String type,
                                         @RequestParam("message") String message,
                                         @RequestParam(value = "user_id", defaultValue = "-1", required = false) Long user_id
    ) throws FileNotFoundException, InvalidTokenException, GameOwnershipException {
        developerService.pushNotification(token, game_id, type, message, user_id);
        return new JsonResponse(0, "Success");
    }


    /*@AuthToken(role = "d")
    @GetMapping("/player/info/unsafe")
    public JsonResponse getPlayerInfoNoOAuth(@RequestHeader("token") String token,
                                             @RequestParam("user_id") Long user_id,
                                             @RequestParam("game_id") Long game_id) throws InvalidTokenException {
        return new JsonResponse(0, "Success", developerService.getPlayerInfoNoOAuth(token, user_id, game_id));
    }


    @AuthToken(role = "d")
    @GetMapping("/player/info/batch/unsafe")
    public JsonResponse getPlayerInfoNoOAuth(@RequestHeader("token") String token,
                                             @RequestParam("user_ids") List<Long> user_ids,
                                             @RequestParam("game_id") Long game_id) {
        return new JsonResponse(0, "Success", developerService.getPlayerInfoBatchNoOAuth(token, user_ids, game_id));
    }*/


    //
    //
    //
    //
    //

    private static final String STORAGE = System.getProperty("user.dir") + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator + "static";
    private final static String SDK_NAME = "gamer-center-developer-sdk-1.0-SNAPSHOT-jar-with-dependencies.jar";


    @GetMapping("/sdk/download")
    public JsonResponse downloadSDK(HttpServletResponse response) {
        File file = new File(STORAGE + File.separator + SDK_NAME);
        if (!file.exists()) {
            return new JsonResponse(-1, "No SDK file available");
        }

        response.reset();
        response.setContentType("application/octet-stream");
        response.setCharacterEncoding("utf-8");
        response.setContentLength((int) file.length());
        response.setHeader("Content-Disposition", "attachment;filename=" + SDK_NAME);

        try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file))) {
            byte[] buff = new byte[1024];
            OutputStream os = response.getOutputStream();
            int i = 0;
            while ((i = bis.read(buff)) != -1) {
                os.write(buff, 0, i);
                os.flush();
            }
        } catch (IOException e) {
            return new JsonResponse(-1, "Download failed");
        }
        return new JsonResponse(0, "Successfully downloaded");
    }


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
