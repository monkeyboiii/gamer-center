package com.sustech.gamercenter.controller;

import com.sustech.gamercenter.model.Game;
import com.sustech.gamercenter.security.AuthToken;
import com.sustech.gamercenter.service.GameService;
import com.sustech.gamercenter.service.UserService;
import com.sustech.gamercenter.util.exception.*;
import com.sustech.gamercenter.util.model.JsonResponse;
import com.sustech.gamercenter.util.model.ResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/*import com.sustech.gamercenter.model.GameDiscount;*/

@RestController
@RequestMapping("/game")
public class GameController {

    @Autowired
    private GameService gameService;

    @Autowired
    UserService userService;


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

    @PostMapping("/update")
    public Object updateGame(Game game) {
        if (gameService.existedName(game.getName(), game.getId())) {
            throw new MyException(-1, "Game's name already existed");
        }
        return ResultService.success(gameService.save(game));
    }

    @AuthToken
    @PostMapping("/purchase")
    public Object purchase(@RequestParam("user_id") long userId,
                           @RequestParam("game_id") long gameId) throws InsufficientBalanceException, UserNotFoundException {
        gameService.purchase(userId, gameId);
        return ResultService.success("");
    }

    @GetMapping("/list")
    public Object search(@RequestParam(value = "tag", defaultValue = "", required = false) String tag,
                         @RequestParam(value = "name", defaultValue = "", required = false) String name,
                         @RequestParam(value = "page", defaultValue = "0", required = false) int page) {
        return ResultService.success(gameService.search(tag, name, page));
//        return ResultService.success(gameService.search(tag, name));
    }

    /*@PostMapping("/discount")
    public Object setDiscount(GameDiscount gameDiscount, long id) {
        return gameService.setDiscount(gameDiscount, id);
    }*/


    //
    //
    //
    //
    // upload, download


    @PostMapping("/upload")
    public Object fileUpload(@RequestParam("id") long id,
                             @RequestParam("upload_file") MultipartFile uploadPackage,
                             @RequestParam("type") String type) throws IOException {
        gameService.uploadFile(type, uploadPackage, id);
        return ResultService.success("");
    }


    @GetMapping(value = "/getPhoto/{url:.+}", produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_GIF_VALUE})
    public byte[] getPhoto(@PathVariable("url") String url) throws IOException {
        return gameService.getFile(url, "image");
    }

    @GetMapping(value = "/getVideo/{url:.+}", produces = MediaType.ALL_VALUE)
    public byte[] getVideo(@PathVariable("url") String url) throws IOException {
        return gameService.getFile(url, "video");
    }

    @GetMapping("/download")
    public void fileDownLoad(HttpServletResponse response,
                             @RequestParam("name") String fileName,
                             @RequestParam("type") String type) throws IOException {
        gameService.download(response, fileName, type);
    }


    @AuthToken
    @GetMapping("cloud/list")
    public JsonResponse listCloudSave(@RequestParam("game_id") Long game_id,
                                      @RequestParam("user_id") Long user_id) throws FileNotFoundException {
        return new JsonResponse(0, "Success", gameService.listCloudSave(game_id, user_id));
    }


    @PostMapping("/cloudUpload")
    public Object cloudUpload(@RequestParam("game_id") long gameId, @RequestParam("user_id") long userId,
                              @RequestParam("upload_file") MultipartFile uploadFile) throws IOException {
        gameService.cloudUpload(gameId, userId, uploadFile);
        return ResultService.success("");
    }


    @GetMapping("/cloudDownload")
    public void cloudDownload(HttpServletResponse response, @RequestParam("game_id") long gameId,
                              @RequestParam("user_id") long userId, @RequestParam("name") String fileName) throws IOException {
        response.setHeader("Content-Type", "application/octet-stream;charset=utf-8");
        gameService.cloudDownload(response, gameId, userId, fileName);
    }


    @GetMapping("/cloudList")
    public Object cloudList(HttpServletResponse response, @RequestParam("game_id") long gameId, @RequestParam("user_id")
            long userId) throws IOException {
        gameService.getCloudList(response, gameId, userId);
        return ResultService.success("");
    }


    //
    //
    //
    //
    //


    @AuthToken(role = "p")
    @GetMapping("/status")
    public JsonResponse checkUserHasGame(@RequestHeader("token") String token,
                                         @RequestParam("game_id") Long game_id) throws InvalidTokenException {
        return new JsonResponse(0, "success", userService.checkUserHasGame(token, game_id));
    }


    //
    //
    //
    //
    //
    // DLC


    @AuthToken(role = "d")
    @PostMapping("/dlc/create")
    public JsonResponse createDLC(@RequestHeader("token") String token,
                                  @RequestParam("game_id") Long game_id,
                                  @RequestParam("name") String name,
                                  @RequestParam("price") Double price,
                                  @RequestParam(value = "visible", defaultValue = "true", required = false) Boolean visible) throws InvalidTokenException {
        return new JsonResponse(0, "Successfully created", gameService.createDLC(token, game_id, name, price, visible));
    }


    /*@PostMapping("/dlc/update")
    public JsonResponse updateDLC(@RequestParam("game_id") Long game_id,
                                  @RequestParam("name") String name,
                                  @RequestParam("price") Double price,
                                  @RequestParam(value = "visible", defaultValue = "true", required = false) Boolean visible) {
        return new JsonResponse(0, "Successfully updated");
    }*/


    @GetMapping("/dlc/list")
    public JsonResponse listDLC(@RequestParam("game_id") Long game_id) {
        return new JsonResponse(0, "Successfully retrieved", gameService.getGameDLCs(game_id));
    }


    @AuthToken(role = "d")
    @PostMapping("/dlc/upload")
    public JsonResponse uploadDLC(@RequestParam("id") Long id,
                                  @RequestParam("content") MultipartFile file) throws IOException {
        gameService.uploadDLCContent(id, file);
        return new JsonResponse(0, "Successfully uploaded");
    }


    @GetMapping("/dlc/download")
    public byte[] downloadDLC(@RequestHeader("token") String token,
                              @RequestParam("id") Long id) throws IOException, InvalidTokenException, ContentNotPurchasedException {
        return gameService.downloadDLCContent(token, id);
    }


    @AuthToken(role = "p")
    @PostMapping("/dlc/purchase")
    public JsonResponse purchaseDLC(@RequestHeader("token") String token,
                                    @RequestParam("id") Long id) throws InvalidTokenException, InsufficientBalanceException, UserNotFoundException {
        gameService.purchaseDLC(token, id);
        return new JsonResponse(0, "Successfully purchased");
    }
}
