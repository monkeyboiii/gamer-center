package com.sustech.gamercenter.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToMany(cascade = {CascadeType.ALL})
    List<Game> games;


    @OneToMany(cascade = {CascadeType.ALL})
    List<User> friends;


    public Player(Long id, List<Game> games, List<User> friends) {
        this.id = id;
        this.games = games;
        this.friends = friends;
    }

    public Player(Long id) {
        this.id = id;
    }

    public Player() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Game> getGames() {
        return games;
    }

    public void setGames(List<Game> games) {
        this.games = games;
    }

    public List<User> getFriends() {
        return friends;
    }

    public void setFriends(List<User> friends) {
        this.friends = friends;
    }
}
