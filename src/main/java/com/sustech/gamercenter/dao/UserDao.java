package com.sustech.gamercenter.dao;

import com.sustech.gamercenter.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends JpaRepository<User, Long> {
    User findByNameIgnoreCase(String name);

    User findByEmail(String email);

}