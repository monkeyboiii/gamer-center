package com.sustech.gamercenter.controller;

import com.sustech.gamercenter.config.MyException;
import com.sustech.gamercenter.model.Game;
//import com.sustech.gamercenter.model.GameDiscount;
import com.sustech.gamercenter.service.GameService;
import com.sustech.gamercenter.service.ResultService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/game")
public class GameController {

    @Autowired
    private GameService gameService;

    @GetMapping("/info")
    public Object getGame(@RequestParam("id") long id) {
        Map<String, Object> map = new HashMap<>();
        map.put("game", gameService.findById(id));
        map.put("game_content", gameService.findAllByGameId(id));
        return ResultService.success(map);
    }

    @PostMapping("/create")
    public Object createGame(Game game) {
        if (gameService.existedName(game.getName(), -1)) {
            throw new MyException(1, "游戏名已存在");
        }
        return ResultService.success(gameService.save(game));
    }

    @PostMapping("/upload")
    public Object fileUpload(@RequestParam("id") long id, @RequestParam("upload_file") MultipartFile uploadPackage, @RequestParam("type") String type) throws IOException {
        gameService.uploadFile(type, uploadPackage, id);
        return ResultService.success("");
    }


    @PostMapping("/update")
    public Object updateGame(Game game) {
        if (gameService.existedName(game.getName(), game.getId())) {
            throw new MyException(1, "游戏名已存在");
        }
        return ResultService.success(gameService.save(game));
    }

    @RequestMapping(value = "/getPhoto/{url:[a-zA-Z0-9_.]+}", produces = MediaType.IMAGE_JPEG_VALUE)
    @ResponseBody
    public byte[] getPhoto(@PathVariable("url") String url) throws IOException {
        return gameService.getFile(url, "image");
    }

    @RequestMapping("/download")
    public Object fileDownLoad(HttpServletResponse response, @RequestParam("name") String fileName,
                               @RequestParam("type") String type) throws IOException {
        gameService.download(response, fileName, type);
        return ResultService.success("");
    }

    @GetMapping("/purchase")
    public Object purchase(@RequestParam("user_id") long userId, @RequestParam("game_id") long gameId) {
        gameService.purchase(userId, gameId);
        return ResultService.success("");
    }

    @GetMapping("/list")
    public Object search(@RequestParam("tag") String tag, @RequestParam("name") String name){
        return ResultService.success(gameService.search(tag, name));
    }

//    @PostMapping("/discount")
//    public Object setDiscount(GameDiscount gameDiscount, long id){
//        return gameService.setDiscount(gameDiscount, id);
//    }
}
