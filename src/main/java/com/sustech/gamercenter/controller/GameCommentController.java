package com.sustech.gamercenter.controller;

import com.sustech.gamercenter.util.exception.MyException;
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
        if(gameCommentService.addComment(gameComment) == -1)
            return new MyException(-1, "User has commented.");
        else
            return ResultService.success("");
    }

    @GetMapping
    public Object getAllComment(){
        return ResultService.success(gameCommentService.getAllComment());
    }

    @DeleteMapping(path = "{id}")
    public Object deleteComment(@PathVariable("id") long commentId){
        gameCommentService.deleteComment(commentId);
        return ResultService.success("");
    }

    @GetMapping(path = "GID/{id}")
    public Object getCommentByGame(@PathVariable("id") long GID){
        return ResultService.success(gameCommentService.getCommentByGame(GID));
    }

    @GetMapping(path = "UID/{id}")
    public Object getCommentByUser(@PathVariable("id") long UID){
        return ResultService.success(gameCommentService.getCommentByUser(UID));
    }
}
