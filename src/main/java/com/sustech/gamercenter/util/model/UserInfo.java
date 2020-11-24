package com.sustech.gamercenter.util.model;

import com.sustech.gamercenter.model.User;

import java.util.List;

public class UserInfo extends User {

    private List<?> friends;
    private List<?> games;


    public UserInfo(User user, List<?> friends, List<?> games) {
        super(user.getName(), user.getEmail(), null, user.getRole(), user.getBalance(), user.getOnline(), user.getLocked(), user.getCreatedAt());
        this.setId(user.getId());
        this.friends = friends;
        this.games = games;
    }

    public UserInfo() {
    }

    public void setFriends(List<?> friends) {
        this.friends = friends;
    }

    public void setGames(List<?> games) {
        this.games = games;
    }

    public static final class builder {
        User user;
        List<?> friends;
        List<?> games;

        public builder user(User user) {
            this.user = user;
            return this;
        }

        public builder friends(List<?> friends) {
            this.friends = friends;
            return this;
        }

        public builder games(List<?> games) {
            this.games = games;
            return this;
        }

        public UserInfo build() {
            return new UserInfo(user, friends, games);
        }
    }

    public List<?> getFriends() {
        return friends;
    }

    public List<?> getGames() {
        return games;
    }
}
