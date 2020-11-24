package com.sustech.gamercenter.util.model;

import com.sustech.gamercenter.model.User;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

/**
 * returned when logged in for full info request
 */
public class UserInfo extends User {

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date registeredAt;

    private List<?> friends;
    private List<?> games;


    public UserInfo(User user, List<?> friends, List<?> games) {
        super(user.getName(), user.getEmail(), null, user.getRole(), user.getBalance(), user.getOnline(), user.getLocked(), user.getCreatedAt());
        this.setId(user.getId());
        this.registeredAt = new Date(user.getCreatedAt().getTime());
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

    public Date getRegisteredAt() {
        return registeredAt;
    }

    public void setRegisteredAt(Date registeredAt) {
        this.registeredAt = registeredAt;
    }

    public List<?> getFriends() {
        return friends;
    }

    public List<?> getGames() {
        return games;
    }
}
