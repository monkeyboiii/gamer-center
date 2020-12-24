package com.sustech.gamercenter.dao;

import com.sustech.gamercenter.model.Game;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GameRepository extends JpaRepository<Game, Long> {

    Game findByName(String name);

    Game findById(long id);

//    List<Game> findByTag(String tag);

    @Query(value = "select * " +
            "from game g " +
            "where g.announce_date <= ?3 and g.tag = ?1 and g.name like CONCAT('%', ?2 ,'%')", nativeQuery = true)
    Page<Game> findByTagOrNameLike(String tag, String name,
                                   String today, Pageable pageable);


    @Query(value = "select * " +
            "from game g " +
            "where g.announce_date <= ?2 and g.tag = ?1 ", nativeQuery = true)
    Page<Game> findAllByTag(String tag, String today, Pageable pageable);


    @Query(value = "select * " +
            "from game g " +
            "where g.announce_date <= ?2 and g.name like CONCAT('%', ?1 ,'%')", nativeQuery = true)
    Page<Game> findAllByNameLike(String name, String today, Pageable pageable);


    @Query(value = "select * " +
            "from game g " +
            "where g.announce_date <= ?1 ", nativeQuery = true)
    Page<Game> findAllGame(String today, Pageable pageable);


    List<Game> findAllByDeveloperIdAndTagIgnoreCase(Long id, String tag);


    List<Game> findAllByDeveloperId(Long id);
}
