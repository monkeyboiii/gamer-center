package com.sustech.gamercenter.service;

import com.sustech.gamercenter.model.GameComment;

import java.util.List;

public interface GameCommentService {
    int addComment(GameComment gameComment);

    List<GameComment> getCommentByGame(int GID);

    List<GameComment> getCommentByUser(int UID);

    List<GameComment> getAllComment();

    int deleteComment(int commentId);
}
