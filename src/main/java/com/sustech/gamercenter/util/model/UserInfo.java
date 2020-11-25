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
    private List<?> messages;


    public UserInfo(User user, List<?> friends, List<?> games, List<?> messages) {
        super(user.getName(), user.getEmail(), null, user.getRole(), user.getBalance(), user.getOnline(), user.getLocked(), user.getCreatedAt());
        this.setId(user.getId());
        this.registeredAt = new Date(user.getCreatedAt().getTime());
        this.friends = friends;
        this.games = games;
        this.messages = messages;
    }

    public UserInfo() {
    }

    public static final class builder {
        User user;
        List<?> friends;
        List<?> games;
        List<?> messages;

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

        public builder messages(List<?> messages) {
            this.messages = messages;
            return this;
        }

        public UserInfo build() {
            return new UserInfo(user, friends, games, messages);
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

    public void setFriends(List<?> friends) {
        this.friends = friends;
    }

    public void setGames(List<?> games) {
        this.games = games;
    }

    public List<?> getMessages() {
        return messages;
    }

    public void setMessages(List<?> messages) {
        this.messages = messages;
    }
}
