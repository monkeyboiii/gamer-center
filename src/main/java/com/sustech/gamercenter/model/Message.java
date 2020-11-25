package com.sustech.gamercenter.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "users_messages")
public class Message {

    @Id
    @GeneratedValue
    private Long id;
    private Long source = 0L;

    @Column(name = "user_id")
    private Long userId;
    private String type;
    private String message;
    private Boolean unread = true;

    @Column(name = "created_at")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp createdAt;

    public Message(Long id, Long source, Long userId, String type, String message, Boolean unread) {
        this.id = id;
        this.source = source;
        this.type = type;
        this.userId = userId;
        this.message = message;
        this.unread = unread;
        this.createdAt = new Timestamp(System.currentTimeMillis());
    }

    public Message() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
}
