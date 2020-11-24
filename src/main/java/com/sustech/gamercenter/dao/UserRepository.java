package com.sustech.gamercenter.dao;

import com.sustech.gamercenter.dao.projection.FriendView;
import com.sustech.gamercenter.dao.projection.GameView;
import com.sustech.gamercenter.dao.projection.UserView;
import com.sustech.gamercenter.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByNameIgnoreCase(String name);

    Optional<User> findByEmail(String email);

    /*
    @Transactional
    @Modifying
    @Query(value = "update users set bio = :bio  where id = :id", nativeQuery = true)
    int updateBio(@Param("id") Long id, @Param("bio") String bio);

    @Transactional
    @Modifying
    @Query(value = "update users set email = :email  where id = :id", nativeQuery = true)
    int updateEmail(@Param("id") Long id, @Param("email") String email);

    @Transactional
    @Modifying
    @Query(value = "update users set password = :password  where id = :id", nativeQuery = true)
    int updatePassword(@Param("id") Long id, @Param("password") String password);
    */

    @Query(value = "select * from users where id in " +
            "(select friend from " +
            "(select to_user_id as friend, state from user_friendships where from_user_id = ?1 " +
            "union all " +
            "select from_user_id as friend, state from user_friendships where to_user_id = ?1) as res " +
            "where state = 'established')", nativeQuery = true)
    List<FriendView> userHasFriends(Long id);


    @Query(value = "select * " +
            "from game " +
            "where id in (select game_id from users_has_games where user_id = ?1);", nativeQuery = true)
    List<GameView> userHasGames(Long id);


}