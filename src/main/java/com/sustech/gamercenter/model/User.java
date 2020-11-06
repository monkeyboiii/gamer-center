package com.sustech.gamercenter.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "users")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;

    @Column(name = "name", unique = true, nullable = false)
    protected String name;

    @Column(name = "email", unique = true, nullable = false)
    protected String email;

    @Column(name = "password", nullable = false)
    protected String password;

//    private String token; // access token

    @Column(name = "role", nullable = false)
    protected String role; // combinations

    @Column
    protected Double balance;

    @Column
    protected String avatar;

    @Column
    protected String bio;


    @Column
    protected Boolean is_online;
    @Column
    protected Boolean is_locked;

    @Column
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    protected Timestamp created_at;


    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
//                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                ", is_online=" + is_online +
                ", is_locked=" + is_locked +
                ", created_at=" + created_at +
                '}';
    }

    public User() {
    }

    // can be used at register
    public User(String name, String email, String password, String role) {
        this(name, email, password, role, false, false, new Timestamp(System.currentTimeMillis()));
    }

    // for test purposes
    public User(Long id, String name, String email, String password, String role) {
        this(name, email, password, role);
        this.id = id;
    }

    public User(String name, String email, String password, String role, Boolean is_online, Boolean is_locked, Timestamp created_at) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
        this.is_online = is_online;
        this.is_locked = is_locked;
        this.created_at = created_at;
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

    public Boolean isIs_online() {
        return is_online;
    }

    public void setIs_online(Boolean is_online) {
        this.is_online = is_online;
    }

    public Boolean isIs_locked() {
        return is_locked;
    }

    public void setIs_locked(Boolean is_locked) {
        this.is_locked = is_locked;
    }

    public Timestamp getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
    }
}
