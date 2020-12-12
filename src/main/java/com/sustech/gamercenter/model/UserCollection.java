package com.sustech.gamercenter.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "users_collections")
public class UserCollection {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "user_id")
    Long userId;

    String name;

    String type;

    String path;

    Boolean valid;

    @Column(name = "uploaded_at")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    Timestamp uploadedAt;


    public UserCollection() {
    }

    public UserCollection(Long userId, String name, String type, String path) {
        this.userId = userId;
        this.name = name;
        this.type = type;
        this.path = path;
        this.valid = true;
        this.uploadedAt = new Timestamp(System.currentTimeMillis());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Boolean getValid() {
        return valid;
    }

    public void setValid(Boolean valid) {
        this.valid = valid;
    }

    public Timestamp getUploadedAt() {
        return uploadedAt;
    }

    public void setUploadedAt(Timestamp uploadedAt) {
        this.uploadedAt = uploadedAt;
    }
}
