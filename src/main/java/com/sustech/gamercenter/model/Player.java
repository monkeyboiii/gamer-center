package com.sustech.gamercenter.model;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;


@Entity
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long Id;


//    @OneToMany
//    private List<String> games;

    @OneToMany
    private List<Friend> friends;

    public Player() {
    }


//    public List<String> getGames() {
//        return games;
//    }
//
//    public void setGames(List<String> games) {
//        this.games = games;
//    }

    public List<Friend> getFriends() {
        return friends;
    }

    public void setFriends(List<Friend> friends) {
        this.friends = friends;
    }
}
