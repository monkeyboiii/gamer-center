package com.sustech.gamercenter.controller;

import com.sustech.gamercenter.model.Game;
import com.sustech.gamercenter.model.GameDiscount;
import com.sustech.gamercenter.service.GameService;
import com.sustech.gamercenter.service.ResultService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/game")
public class GameController {

    @Autowired
    private GameService gameService;


    @PostMapping("/create")
    public Object createGame(Game game){
        if(gameService.existedName(game.getName())){
            return ResultService.jsonResult(-1,"游戏名已存在", game);
        }
        Game newGame = gameService.save(game);
        return ResultService.jsonResult(0, "success", newGame);
    }

    @PostMapping("/upload")
    public Object upload(@RequestParam("id") long id, @RequestParam("upload_file") MultipartFile uploadPackage, @RequestParam("type")String type) throws IOException{
        gameService.uploadFile(type, uploadPackage, id);
        return ResultService.jsonResult(0, "success", "");
    }


    @PostMapping("/update")
    public Object updateGame(@RequestParam long id, @RequestParam String name, @RequestParam double price,
                             @RequestParam boolean is_announced, @RequestParam boolean is_downloadable,
                             @RequestParam String description, @RequestParam long developer_id){
        if(gameService.existedName(name)){
            return ResultService.jsonResult(-1,"游戏名已存在", "");
        }
        Game game = new Game();
        game.setId(id);
        game.setName(name);
        game.setPrice(price);
        game.setIs_announced(is_announced);
        game.setIs_downloadable(is_downloadable);
        game.setDescription(description);
        game.setDeveloper_id(developer_id);
        return ResultService.jsonResult(0,"success",gameService.save(game));
    }

    @PostMapping("/discount")
    public Object setDiscount(GameDiscount gameDiscount,@RequestParam("game_id") long gameId){
        return ResultService.jsonResult(0, "success", gameService.setDiscount(gameDiscount, gameId));
    }
}
