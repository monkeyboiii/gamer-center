package com.sustech.gamercenter.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "game_comment")
public class GameComment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private long user_id;
    private long game_id;
    private String content;
    private double grade;

    @Column(name = "created_at")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp createAt;

    @JsonIgnore
    private boolean visible;

    public Long getId() {
        return id;
    }

    public void setId(Long game_comment_id) {
        this.id = game_comment_id;
    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public long getGame_id() {
        return game_id;
    }

    public void setGame_id(int game_id) {
        this.game_id = game_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public double getGrade() {
        return grade;
    }

    public void setGrade(double grade) {
        this.grade = grade;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public Timestamp getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Timestamp createAt) {
        this.createAt = createAt;
    }

    public GameComment(Long id, int user_id, int game_id, String content, double grade) {
        this.id = id;
        this.user_id = user_id;
        this.game_id = game_id;
        this.content = content;
        this.grade = grade;
        this.createAt = new Timestamp(System.currentTimeMillis());
    }

    public GameComment(@JsonProperty("UID") int UID,
                       @JsonProperty("GID") int GID,
                       @JsonProperty("content") String content,
                       @JsonProperty("grade") double grade) {
        this.user_id = UID;
        this.game_id = GID;
        this.content = content;
        this.grade = grade;
        this.createAt = new Timestamp(System.currentTimeMillis());
    }

    public GameComment() {
    }
}
