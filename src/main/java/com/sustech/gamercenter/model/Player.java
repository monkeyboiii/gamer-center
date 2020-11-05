package com.sustech.gamercenter.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.sql.Timestamp;
import java.util.List;


public class Player extends User {


    List<String> games;

    List<String> friends;

    public Player() {
    }

    public Player(Long id, String name, String email, String password, String role, boolean is_online, boolean is_locked, Timestamp created_at) {
        super(id, name, email, password, role, is_online, is_locked, created_at);
    }


    public List<String> getGames() {
        return games;
    }

    public void setGames(List<String> games) {
        this.games = games;
    }

    public List<String> getFriends() {
        return friends;
    }

    public void setFriends(List<String> friends) {
        this.friends = friends;
    }
}
