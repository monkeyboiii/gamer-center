package com.sustech.gamercenter.dao;

import com.sustech.gamercenter.model.Game;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GameRepository extends JpaRepository<Game, Long> {

    Game findByName(String name);

    Game findById(long id);

//    List<Game> findByTag(String tag);

    @Query(value = "select * from game g where g.tag=:tag and g.name like CONCAT('%',:name,'%')", nativeQuery = true)
    List<Game> findByTagOrNameLike(@Param("tag") String tag, @Param("name") String name);

    List<Game> findAllByTag(String tag);

    @Query(value = "select * from game g where g.name like CONCAT('%',:name,'%')", nativeQuery = true)
    List<Game> findAllByNameLike(@Param("name") String name);

    List<Game> findAll();
}
