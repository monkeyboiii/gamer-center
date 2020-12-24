package com.sustech.gamercenter.dao;

import com.sustech.gamercenter.dao.projection.FriendView;
import com.sustech.gamercenter.dao.projection.GameView;
import com.sustech.gamercenter.dao.projection.MessageView;
import com.sustech.gamercenter.dao.projection.UserView;
import com.sustech.gamercenter.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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


    Page<User> findAllByNameLike(String value, Pageable pageable);


    @Query(value = "select * from users u where name like CONCAT(?1,'%') ", nativeQuery = true)
    Page<User> findAllByNameStartsWith(String value, Pageable pageable);


    Page<User> findAllByCreatedAtAfter(String createdAt, Pageable pageable);


    //
    //
    //
    //
    // friend, game


    @Query(value = "select * from users where id in " +
            "(select friend from " +
            "(select to_user_id as friend, status from users_friends where from_user_id = ?1 " +
            "union all " +
            "select from_user_id as friend, status from users_friends where to_user_id = ?1) as res " +
            "where status = 'established')", nativeQuery = true)
    List<FriendView> userHasFriends(Long id);


    @Query(value = "select * " +
            "from game " +
            "where id in (select game_id from users_games where user_id = ?1 and dlc_id is null)", nativeQuery = true)
    List<GameView> userHasGames(Long id);


    @Query(value = "select distinct status " +
            "from users_games " +
            "where user_id =?1 and game_id = ?2 and dlc_id is null ", nativeQuery = true)
    String userHasGameStatus(Long user_id, Long game_id);


    @Query(value = "select * " +
            "from game " +
            "where tag = ?2 and " +
            "id in (select distinct game_id from users_games where user_id = ?1)", nativeQuery = true)
    List<GameView> userHasGamesWithTag(Long id, String tag);


    @Query(value = "select distinct user_tag " +
            "from users_games " +
            "where user_id = ?1", nativeQuery = true)
    List<String> userHasGameTags(Long id);


    @Query(value = "select * " +
            "from users " +
            "where id in " +
            "(select user_id from users_games " +
            "where game_id = " +
            "(select id " +
            "from game " +
            "where id = ?2 " +
            "and developer_id = ?1) and dlc_id is null) ", nativeQuery = true)
    List<UserView> getPlayerToGame(Long dev, Long game_id);


    @Query(value = "select * " +
            "from users " +
            "where id = ?1 ", nativeQuery = true)
    UserView getUserViewById(Long id);


    //
    //
    //
    //
    // message


    @Modifying
    @Query(value = "update users_messages " +
            "set unread = false " +
            "where id = ?1", nativeQuery = true)
    @Transactional
    void readMessage(Long id);


    @Modifying
    @Query(value = "insert into " +
            "users_messages(source, user_id, type, message) " +
            "values(?1, ?2, ?3, ?4)", nativeQuery = true)
    @Transactional
    void sendMessage(Long from, Long to, String type, String message);


    @Modifying
    @Query(value = "update users_friends set status = 'established' where from_user_id = ?1 and to_user_id =?2", nativeQuery = true)
    @Transactional
    void confirmFriendRequest(Long from, Long to);


    @Modifying
    @Query(value = "insert into users_friends values(?1,?2,'pending')", nativeQuery = true)
    @Transactional
    void friendRequest(Long from, Long to);


    @Query(value = "select um.id, u.name, um.source, um.type, um.message, um.unread, um.created_at " +
            "from users_messages um " +
            "left join users u on um.source = u.id " +
            "where user_id = ?1 and unread =?2 ", nativeQuery = true)
    List<MessageView> userHasUnreadMessages(Long id, Boolean unread);


    @Query(value = "select um.id, u.name, um.source, um.type, um.message, um.unread, um.created_at " +
            "from users_messages um " +
            "left join users u on um.source = u.id " +
            "where user_id = ?1 ", nativeQuery = true)
    List<MessageView> userHasAllMessages(Long id);


    //
    //
    //
    //
    // oAuth


    @Modifying
    @Query(value = "insert into users_games_tokens " +
            "(user_id, game_id, developer_id, token) " +
            "values(?1, ?2, (select developer_id from game where id = ?2), ?3) ", nativeQuery = true)
    @Transactional
    void createOAuthToken(Long user_id, Long game_id, String token);


    @Query(value = "select distinct user_id " +
            "from users_games " +
            "where game_id = ?1 ", nativeQuery = true)
    List<Long> getPlayerIdsToGame(Long game_id);
}
