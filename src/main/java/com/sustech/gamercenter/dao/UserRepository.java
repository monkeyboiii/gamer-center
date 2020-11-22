package com.sustech.gamercenter.dao;

import com.sustech.gamercenter.model.User;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByNameIgnoreCase(String name);

    Optional<User> findByEmail(String email);

    @Transactional
    @Modifying
    @Query(value = "update users set bio = :bio  where id = :id", nativeQuery = true)
    int updateBio(@Param("id") Long id, @Param("bio") String bio);

    @Transactional
    @Modifying
    @Query(value = "update users set email = :email  where id = :id", nativeQuery = true)
    int updateEmail(@Param("id") Long id, @Param("email") String email);

    @Transactional
    @Modifying
    @Query(value = "update users set password = :password  where id = :id", nativeQuery = true)
    int updatePassword(@Param("id") Long id, @Param("password") String password);

}