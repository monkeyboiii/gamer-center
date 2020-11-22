package com.sustech.gamercenter.service;

import com.sustech.gamercenter.dao.GameCommentRepository;
import com.sustech.gamercenter.model.GameComment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("commentAccessDB")
public class GameCommentServiceImpl implements GameCommentService {
    @Autowired
    private GameCommentRepository gameCommentRepository;

    @Override
    public int addComment(GameComment gameComment) {
        long UID = gameComment.getUser_id();
        long GID = gameComment.getGame_id();
        String content = gameComment.getContent();
        double grade = gameComment.getGrade();
        List<GameComment> U = getCommentByUser(UID);
        for(GameComment c : U){
            if(c.getGame_id() == GID)
                return -1;
        }

        return gameCommentRepository.addComment(UID, GID, content, grade);
    }

    @Override
    public List<GameComment> getCommentByGame(long GID) {
        return gameCommentRepository.getCommentByGame(GID);
    }

    @Override
    public List<GameComment> getCommentByUser(long UID) {
        return gameCommentRepository.getCommentByUser(UID);
    }

    @Override
    public List<GameComment> getAllComment() {
        return gameCommentRepository.getAllComment();
    }

    @Override
    public int deleteComment(long commentId) {
        return gameCommentRepository.deleteComment(commentId);
    }
}
