package com.sustech.gamercenter.controller;

import com.sustech.gamercenter.model.GameComment;
import com.sustech.gamercenter.service.GameCommentService;
import com.sustech.gamercenter.util.exception.DuplicateCommentException;
import com.sustech.gamercenter.util.exception.InvalidTokenException;
import com.sustech.gamercenter.util.exception.MyException;
import com.sustech.gamercenter.util.model.ResultService;
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
    public Object addComment(@RequestBody GameComment gameComment) throws DuplicateCommentException {
        if (gameCommentService.addComment(gameComment) == -1)
            return new MyException(-1, "User has commented.");
        else
            return ResultService.success("");
    }

    @GetMapping
    public Object getAllComment() {
        return ResultService.success(gameCommentService.getAllComment());
    }

    @DeleteMapping
    public Object deleteComment(@RequestParam("id") long commentId) {
        gameCommentService.deleteComment(commentId);
        return ResultService.success("");
    }

    @GetMapping("/GID")
    public Object getCommentByGame(@RequestParam("id") long GID) {
        return ResultService.success(gameCommentService.getCommentByGame(GID));
    }

    @GetMapping("/UID")
    public Object getCommentByUser(@RequestHeader("token") String token) throws InvalidTokenException {
        return ResultService.success(gameCommentService.getCommentByUser(token));
    }
}
