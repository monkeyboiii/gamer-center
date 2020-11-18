package com.sustech.gamercenter.dao;

import com.sustech.gamercenter.model.Game;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GameRepository extends JpaRepository<Game, Long> {

    Game findByName(String name);

    Game findById(long id);

    List<Game> findByTag(String tag);
}
