package com.sustech.gamercenter.dao;

import com.sustech.gamercenter.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {


    @Modifying
    @Query(value = "insert into users_games (user_id, game_id, purchase_id, valid, user_tag) " +
            "values(?1, ?2, ?3, true, (select tag from game where id = ?2))", nativeQuery = true)
    @Transactional
    void receiveGame(Long user_id, Long game_id, Long purchase_id);


    @Modifying
    @Query(value = "insert into users_games (user_id, game_id, dlc_id, purchase_id, user_tag) " +
            "values(?1, ?2, ?3, ?4,'DLC')", nativeQuery = true)
    @Transactional
    void receiveGameDLC(Long user_id, Long game_id, Long dlc_id, Long purchase_id);
}
