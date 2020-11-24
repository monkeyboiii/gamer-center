package com.sustech.gamercenter.model;

import javax.persistence.*;

@Entity
@Table(name = "users_messages")
public class Message {

    @Id
    @GeneratedValue
    private Long Id;
    private Long source = 0L;

    @Column(name = "user_id")
    private Long userId;
    private String message;
    private Boolean unread = false;

    public Message(Long id, Long source, Long userId, String message, Boolean unread) {
        Id = id;
        this.source = source;
        this.userId = userId;
        this.message = message;
        this.unread = unread;
    }

    public Message() {
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public Long getSource() {
        return source;
    }

    public void setSource(Long source) {
        this.source = source;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getUnread() {
        return unread;
    }

    public void setUnread(Boolean unread) {
        this.unread = unread;
    }
}
