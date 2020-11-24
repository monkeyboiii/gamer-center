package com.sustech.gamercenter.dao;

import com.sustech.gamercenter.model.Game;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.parameters.P;

import java.util.List;

public interface GameRepository extends JpaRepository<Game, Long> {

    Game findByName(String name);

    Game findById(long id);

//    List<Game> findByTag(String tag);

    @Query(value = "select * from game g where g.announce_date < :today and g.tag=:tag and g.name like CONCAT('%',:name,'%')", nativeQuery = true)
    Page<Game> findByTagOrNameLike(@Param("tag") String tag, @Param("name") String name,
                                   @Param("today") String today, Pageable pageable);

    @Query(value = "select * from game g where g.announce_date < :today and and g.tag=:tag", nativeQuery = true)
    Page<Game> findAllByTag(String tag, @Param("today") String today, Pageable pageable);

    @Query(value = "select * from game g where g.announce_date < :today and g.name like CONCAT('%',:name,'%')", nativeQuery = true)
    Page<Game> findAllByNameLike(String name, @Param("today") String today, Pageable pageable);

    @Query(value = "select * from game g where g.announce_date < :today", nativeQuery = true)
    Page<Game> findAllGame(@Param("today") String today, Pageable pageable);
}
