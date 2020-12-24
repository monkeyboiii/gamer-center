package com.sustech.gamercenter.service;

import com.sustech.gamercenter.dao.GameCommentRepository;
import com.sustech.gamercenter.dao.GameRepository;
import com.sustech.gamercenter.dao.projection.GameCommentView;
import com.sustech.gamercenter.model.Game;
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
    private GameRepository gameRepository;

    @Autowired
    private SimpleTokenService tokenService;

    @Override
    public int addComment(GameComment gameComment) throws DuplicateCommentException {
        long UID = gameComment.getUser_id();
        long GID = gameComment.getGame_id();
        String content = gameComment.getContent();
        double grade = gameComment.getGrade();

        try {
            int t = gameCommentRepository.addComment(UID, GID, content, grade);

            int size = getCommentByGame(GID).size();
            Game game = gameRepository.getOne(GID);
            game.setScore((game.getScore() * size + grade) / (size + 1));
            gameRepository.flush();

            return t;
        } catch (Exception exception) {
            throw new DuplicateCommentException("User has already commented");
        }
    }

    @Override
    public List<GameCommentView> getCommentByGame(long GID) {
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
