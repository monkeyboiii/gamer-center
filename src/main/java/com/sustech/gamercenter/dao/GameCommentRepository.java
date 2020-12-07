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

    @Query(value = "select * from game_comment where user_id = ?1 and visible = true ", nativeQuery = true)
    List<GameComment> getCommentByUser(long UID);


    @Query(value = "select * from game_comment where game_id = ?1 and visible = true ", nativeQuery = true)
    List<GameComment> getCommentByGame(long GID);


    @Query(value = "select * from game_comment where visible = true ", nativeQuery = true)
    List<GameComment> getAllComment();


    @Transactional
    @Modifying
    @Query(value = "update game_comment set visible = False where game_comment_id = :game_comment_id", nativeQuery = true)
    int deleteComment(long game_comment_id);

    @Transactional
    @Modifying
    @Query(value = "insert into game_comment(user_id, game_id, content, grade) value(?1,?2,?3,?4)", nativeQuery = true)
    int addComment(long UID, long GID, String content, double grade);
}
