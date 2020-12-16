package com.sustech.gamercenter.dao;

import com.sustech.gamercenter.dao.projection.CommentStat;
import com.sustech.gamercenter.model.GameComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface GameCommentRepository extends JpaRepository<GameComment, Long> {

    Optional<GameComment> findById(Integer comment_id);


    //
    //
    //
    //
    //


    @Query(value = "select * from game_comment where user_id = ?1 and visible = true ", nativeQuery = true)
    List<GameComment> getCommentByUser(long UID);


    @Query(value = "select * from game_comment where game_id = ?1 and visible = true ", nativeQuery = true)
    List<GameComment> getCommentByGame(long GID);


    @Query(value = "select * from game_comment where visible = true ", nativeQuery = true)
    List<GameComment> getAllComment();


    @Transactional
    @Modifying
    @Query(value = "update game_comment set visible = False where id = :game_comment_id", nativeQuery = true)
    int deleteComment(long game_comment_id);


    @Transactional
    @Modifying
    @Query(value = "insert into game_comment(user_id, game_id, content, grade) value(?1,?2,?3,?4)", nativeQuery = true)
    int addComment(long UID, long GID, String content, double grade);


    //
    //
    //
    //
    // report


    @Modifying
    @Query(value = "insert into comment_report(comment_id, user_id, reason) " +
            "values(?1, ?2, ?3) ", nativeQuery = true)
    @Transactional
    void reportComment(Long comment_id, Long id, String reason);


    @Query(value = "select cr.comment_id as comment_id, " +
            "gc.content as content, " +
            "gc.user_id as reportee_id, " +
            "gc.game_id as game_id, " +
            "count(cr.comment_id) as total " +
            "from comment_report cr " +
            "    left join game_comment gc on cr.comment_id = gc.id " +
            "where resolved = false " +
            "group by cr.comment_id " +
            "limit ?2 offset ?1 ", nativeQuery = true)
    List<CommentStat> getCommentStat(Integer offset, Integer pageSize);

}
