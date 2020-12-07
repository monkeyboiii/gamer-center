package com.sustech.gamercenter.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;

@Entity
@Table(name = "game_comment")
public class GameComment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long game_comment_id;

    private long user_id;
    private long game_id;
    private String content;
    private double grade;

    @JsonIgnore
    private boolean visible;

    public Long getGame_comment_id() {
        return game_comment_id;
    }

    public void setGame_comment_id(Long game_comment_id) {
        this.game_comment_id = game_comment_id;
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

    public GameComment(Long game_comment_id, int user_id, int game_id, String content, double grade) {
        this.game_comment_id = game_comment_id;
        this.user_id = user_id;
        this.game_id = game_id;
        this.content = content;
        this.grade = grade;
    }

    public GameComment(@JsonProperty("UID") int UID,
                       @JsonProperty("GID") int GID,
                       @JsonProperty("content") String content,
                       @JsonProperty("grade") double grade) {
        this.user_id = UID;
        this.game_id = GID;
        this.content = content;
        this.grade = grade;
    }

    public GameComment() {
    }
}
