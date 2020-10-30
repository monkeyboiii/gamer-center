package com.sustech.gamercenter.dao;

import com.sustech.gamercenter.model.GameDiscount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameDiscountRepository extends JpaRepository<GameDiscount, Long> {
}
