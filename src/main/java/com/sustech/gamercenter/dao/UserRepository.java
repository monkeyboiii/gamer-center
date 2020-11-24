package com.sustech.gamercenter.dao;

import com.sustech.gamercenter.dao.projection.FriendView;
import com.sustech.gamercenter.dao.projection.GameView;
import com.sustech.gamercenter.model.Message;
import com.sustech.gamercenter.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByNameIgnoreCase(String name);


    Optional<User> findByEmail(String email);


    @Query(value = "select * from users where id in " +
            "(select friend from " +
            "(select to_user_id as friend, status from users_friends where from_user_id = ?1 " +
            "union all " +
            "select from_user_id as friend, status from users_friends where to_user_id = ?1) as res " +
            "where status = 'established')", nativeQuery = true)
    List<FriendView> userHasFriends(Long id);


    @Query(value = "select * " +
            "from game " +
            "where id in (select game_id from users_games where user_id = ?1)", nativeQuery = true)
    List<GameView> userHasGames(Long id);


    @Modifying
    @Query(value = "insert into users_friends values(?1,?2,'pending')", nativeQuery = true)
    @Transactional
    void friendRequest(Long from, Long to);


    @Modifying
    @Query(value = "update users_friends set status = 'established' where from_user_id = ?1 and to_user_id =?2", nativeQuery = true)
    @Transactional
    void confirmFriendRequest(Long from, Long to);


    @Modifying
    @Query(value = "insert into users_purchases (user_id, balance_change, rest_balance)" +
            " values(?1,?2,(select balance from users where id = ?1))", nativeQuery = true)
    @Transactional
    void auditPurchase(Long id, Double price);
}
