package com.sustech.gamercenter.controller;

import com.sustech.gamercenter.config.MyException;
import com.sustech.gamercenter.model.GameComment;
import com.sustech.gamercenter.service.GameCommentService;
import com.sustech.gamercenter.service.ResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/comment")
@RestController
public class GameCommentController {
    private final GameCommentService gameCommentService;

    @Autowired
    public GameCommentController(GameCommentService gameCommentService) {
        this.gameCommentService = gameCommentService;
    }

    @PostMapping
    public Object addComment(@RequestBody GameComment gameComment){
        try{
            gameCommentService.addComment(gameComment);
        }catch (Exception e){
            return new MyException(5, "该用户已有评论");
        }
        return ResultService.success("");
    }

    @GetMapping
    public Object getAllComment(){
        return ResultService.success(gameCommentService.getAllComment());
    }

    @DeleteMapping(path = "{id}")
    public Object deleteComment(@PathVariable("id") int commentId){
        gameCommentService.deleteComment(commentId);
        return ResultService.success("");
    }

    @GetMapping(path = "GID/{id}")
    public Object getCommentByGame(@PathVariable("id") int GID){
        return ResultService.success(gameCommentService.getCommentByGame(GID));
    }

    @GetMapping(path = "UID/{id}")
    public Object getCommentByUser(@PathVariable("id") int UID){
        return ResultService.success(gameCommentService.getCommentByUser(UID));
    }
}
