package com.sustech.gamercenter.service;

import com.sustech.gamercenter.model.GameComment;

import java.util.List;

public interface GameCommentService {
    int addComment(GameComment gameComment);

    List<GameComment> getCommentByGame(long GID);

    List<GameComment> getCommentByUser(long UID);

    List<GameComment> getAllComment();

    int deleteComment(long commentId);
}
