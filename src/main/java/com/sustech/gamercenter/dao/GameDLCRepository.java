package com.sustech.gamercenter.dao;

import com.sustech.gamercenter.model.GameDLC;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GameDLCRepository extends JpaRepository<GameDLC, Long> {

    List<GameDLC> findAllByGameId(Long game_id);


    @Query(value = "select count(*) " +
            "from users_games " +
            "where user_id = ?1 and dlc_id = ?2 ", nativeQuery = true)
    int validateUserHasDLC(Long user_id, Long id);


    @Query(value = "select * " +
            "from game_dlc " +
            "where id in (select dlc_id from users_games where user_id = ?1)", nativeQuery = true)
    List<GameDLC> userHasGameDLCs(Long id);


}
