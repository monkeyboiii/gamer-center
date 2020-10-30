package com.sustech.gamercenter.dao;

import com.sustech.gamercenter.model.Game;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameRepository extends JpaRepository<Game, Long> {

    Game findByName(String name);

}
