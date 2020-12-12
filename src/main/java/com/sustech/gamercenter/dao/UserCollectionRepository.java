package com.sustech.gamercenter.dao;

import com.sustech.gamercenter.model.UserCollection;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserCollectionRepository extends JpaRepository<UserCollection, Long> {

    List<UserCollection> findAllByUserId(Long userId);

    List<UserCollection> findAllByUserIdAndType(Long userId, String type);
}
