package com.sustech.gamercenter.dao;

import com.sustech.gamercenter.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PlayerRepository extends JpaRepository<Player, Long> {

    Optional<Player> findById(Long id);

}
