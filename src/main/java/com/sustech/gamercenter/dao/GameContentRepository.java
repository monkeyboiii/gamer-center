package com.sustech.gamercenter.dao;

import com.sustech.gamercenter.model.GameContent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GameContentRepository extends JpaRepository<GameContent, Long> {


    GameContent findById(long id);


    @Query(value = "select * " +
            "from game_content gc " +
            "where gc.game_id = ?1 ", nativeQuery = true)
    List<GameContent> findAllByGameId(long game_id);


}
