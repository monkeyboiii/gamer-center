package com.sustech.gamercenter.service;

import com.sustech.gamercenter.dao.GameCommentRepository;
import com.sustech.gamercenter.model.GameComment;
import com.sustech.gamercenter.service.token.SimpleTokenService;
import com.sustech.gamercenter.util.exception.DuplicateCommentException;
import com.sustech.gamercenter.util.exception.InvalidTokenException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("commentAccessDB")
public class GameCommentServiceImpl implements GameCommentService {
    @Autowired
    private GameCommentRepository gameCommentRepository;

    @Autowired
    private SimpleTokenService tokenService;

    @Override
    public int addComment(GameComment gameComment) throws DuplicateCommentException {
        long UID = gameComment.getUser_id();
        long GID = gameComment.getGame_id();
        String content = gameComment.getContent();
        double grade = gameComment.getGrade();

        try {
            return gameCommentRepository.addComment(UID, GID, content, grade);
        } catch (Exception exception) {
            throw new DuplicateCommentException("User has already commented");
        }
    }

    @Override
    public List<GameComment> getCommentByGame(long GID) {
        return gameCommentRepository.getCommentByGame(GID);
    }

    @Override
    public List<GameComment> getCommentByUser(String token) throws InvalidTokenException {
        return gameCommentRepository.getCommentByUser(tokenService.getIdByToken(token));
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
