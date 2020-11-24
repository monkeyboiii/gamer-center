package com.sustech.gamercenter.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

@Entity
@Table(name = "users")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    @Column(unique = true, nullable = false)
    private String email;

    @JsonIgnore
    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String role = "p"; // combinations

    private Double balance = 0.0;

    private String avatar;

    private String bio = "";


    @Column(name = "is_online") // TODO change database name
    private Boolean online = false;
    @Column(name = "is_locked")
    private Boolean locked = false;

    @Column(name = "created_at")
//    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createdAt;

    public User() {
    }

    // can be used at register
    public User(String name, String email, String password, String role) {
        this(name, email, password, role, 0.0, false, false, new Date(System.currentTimeMillis()));
    }

    // for test purposes
    public User(Long id, String name, String email, String password, String role) {
        this(name, email, password, role);
        this.id = id;
    }

    public User(String name, String email, String password, String role, Double balance, Boolean online, Boolean locked, Date createdAt) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.balance = balance;
        this.role = role;
        this.online = online;
        this.locked = locked;
        this.createdAt = createdAt;
    }


    //


    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                ", online=" + online +
                ", locked=" + locked +
                ", createdAt=" + createdAt +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id.equals(user.id) || name.equals(user.name) || email.equals(user.email);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public Boolean getOnline() {
        return online;
    }

    public void setOnline(Boolean online) {
        this.online = online;
    }

    public Boolean getLocked() {
        return locked;
    }

    public void setLocked(Boolean locked) {
        this.locked = locked;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
