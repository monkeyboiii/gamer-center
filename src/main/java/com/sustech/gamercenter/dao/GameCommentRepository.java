package com.sustech.gamercenter.dao;

import com.sustech.gamercenter.model.GameComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface GameCommentRepository extends JpaRepository<GameComment, Long> {
    @Query(value = "select * from game_comment where user_id = :UID and visible = True", nativeQuery = true)
    List<GameComment> getCommentByUser(int UID);

    @Query(value = "select * from game_comment where game_id = :GID and visible = True", nativeQuery = true)
    List<GameComment> getCommentByGame(int GID);

    @Query(value = "select * from game_comment where visible = True", nativeQuery = true)
    List<GameComment> getAllComment();

    @Transactional
    @Modifying
    @Query(value = "update game_comment set visible = False where game_comment_id = :game_comment_id", nativeQuery = true)
    int  deleteComment(int game_comment_id);

    @Transactional
    @Modifying
    @Query(value = "insert into game_comment(user_id, game_id, content, grade) value(?1,?2,?3,?4)",nativeQuery = true)
    int addComment(int UID, int GID, String content, double grade);
}
