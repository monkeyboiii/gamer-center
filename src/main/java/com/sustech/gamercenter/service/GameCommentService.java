package com.sustech.gamercenter.service;

import com.sustech.gamercenter.model.GameComment;
import com.sustech.gamercenter.util.exception.DuplicateCommentException;
import com.sustech.gamercenter.util.exception.InvalidTokenException;

import java.util.List;

public interface GameCommentService {
    int addComment(GameComment gameComment) throws DuplicateCommentException;

    List<GameComment> getCommentByGame(long GID);

    List<GameComment> getCommentByUser(String token) throws InvalidTokenException;

    List<GameComment> getAllComment();

    int deleteComment(long commentId);
}
