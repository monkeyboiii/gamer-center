package com.sustech.gamercenter.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Entity
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
public class GameContent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;
    private String type;
    private String path;
    private long game_id;


//
//    @ManyToOne(cascade={CascadeType.ALL})
//    private Game game;
//
//    public Game getGame() {
//        return game;
//    }

//    public void setGame(Game game) {
//        this.game = game;
//    }

    public long getGame_id() {
        return game_id;
    }

    public void setGame_id(long game_id) {
        this.game_id = game_id;
    }

    public void setGameId(long game_id) {
        this.game_id = game_id;
    }

    //
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

}
