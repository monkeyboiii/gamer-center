package com.sustech.gamercenter.dao;

import com.sustech.gamercenter.model.GameContent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameContentRepository extends JpaRepository<GameContent, Long> {
}
