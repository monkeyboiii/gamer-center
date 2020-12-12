package com.sustech.gamercenter.controller;

import com.sustech.gamercenter.model.Game;
import com.sustech.gamercenter.service.GameService;
import com.sustech.gamercenter.util.exception.InsufficientBalanceException;
import com.sustech.gamercenter.util.exception.MyException;
import com.sustech.gamercenter.util.exception.UserNotFoundException;
import com.sustech.gamercenter.util.model.JsonResponse;
import com.sustech.gamercenter.util.model.ResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

//import com.sustech.gamercenter.model.GameDiscount;

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
            throw new MyException(-1, "Game's name already existed.");
        }
        return ResultService.success(gameService.save(game));
    }

    @PostMapping("/upload")
    public Object fileUpload(@RequestParam("id") long id, @RequestParam("upload_file") MultipartFile uploadPackage, @RequestParam("type") String type) throws IOException {
        gameService.uploadFile(type, uploadPackage, id);
        return ResultService.success("");
    }

    @PostMapping("/cloudUpload")
    public Object cloudUpload(@RequestParam("game_id") long gameId, @RequestParam("user_id") long userId,
                              @RequestParam("upload_file") MultipartFile uploadFile) throws IOException {
        gameService.cloudUpload(gameId, userId, uploadFile);
        return ResultService.success("");
    }

    @GetMapping("/cloudDownload")
    public Object cloudDownload(HttpServletResponse response, @RequestParam("game_id") long gameId,
                                @RequestParam("user_id") long userId, @RequestParam("name") String fileName)
            throws IOException {
        gameService.cloudDownload(response, gameId, userId, fileName);
        return ResultService.success("");
    }

    @GetMapping("/cloudList")
    public Object cloudList(HttpServletResponse response, @RequestParam("game_id") long gameId, @RequestParam("user_id")
            long userId) throws IOException {
        gameService.getCloudList(response, gameId, userId);
        return ResultService.success("");
    }

    @PostMapping("/update")
    public Object updateGame(Game game) {
        if (gameService.existedName(game.getName(), game.getId())) {
            throw new MyException(-1, "Game's name already existed");
        }
        return ResultService.success(gameService.save(game));
    }

    @GetMapping(value = "/getPhoto/{url:.+}", produces = MediaType.IMAGE_JPEG_VALUE)
//    @GetMapping(value = "/getPhoto/{url:[a-zA-Z0-9_.%]+}", produces = MediaType.IMAGE_JPEG_VALUE)
    @ResponseBody
    public byte[] getPhoto(@PathVariable("url") String url) throws IOException {
        return gameService.getFile(url, "image");
    }

    @GetMapping("/download")
    public Object fileDownLoad(HttpServletResponse response, @RequestParam("name") String fileName,
                               @RequestParam("type") String type) throws IOException {
        gameService.download(response, fileName, type);
        return ResultService.success("");
    }

    @GetMapping("/purchase")
    public Object purchase(@RequestParam("user_id") long userId, @RequestParam("game_id") long gameId) throws InsufficientBalanceException, UserNotFoundException {
        gameService.purchase(userId, gameId);
        return ResultService.success("");
    }

    @GetMapping("/list")
    public Object search(@RequestParam("tag") String tag, @RequestParam("name") String name, @RequestParam("page") int page) {
        return ResultService.success(gameService.search(tag, name, page));
//        return ResultService.success(gameService.search(tag, name));
    }


    //
    //
    //
    //
    //

    // check game
    // blurry search game


    @GetMapping
    public JsonResponse checkUserHasGame(@RequestHeader("token") String token,
                                         @RequestParam("game_id") Long game_id) {
        return new JsonResponse(0,"success");
    }

//    @PostMapping("/discount")
//    public Object setDiscount(GameDiscount gameDiscount, long id){
//        return gameService.setDiscount(gameDiscount, id);
//    }
}
