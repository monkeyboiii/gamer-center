package com.sustech.gamercenter.model;

import com.sun.istack.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.List;

@Entity
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotNull
    private String name;

    private double price;
    private double score;
    private String description;
    private long developer_id;
    private String tag;
    private String branch;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private String discount_start;
    private String discount_end;

    private String announce_date;
    private String release_date;

    private double discount_rate;

    private String front_image;

    @OneToMany(cascade = {CascadeType.ALL})
    private List<GameContent> gameContents;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getDeveloper_id() {
        return developer_id;
    }

    public void setDeveloper_id(long developer_id) {
        this.developer_id = developer_id;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getDiscount_start() {
        return discount_start;
    }

    public void setDiscount_start(String discount_start) {
        this.discount_start = discount_start;
    }

    public String getDiscount_end() {
        return discount_end;
    }

    public void setDiscount_end(String discount_end) {
        this.discount_end = discount_end;
    }

    public String getAnnounce_date() {
        return announce_date;
    }

    public void setAnnounce_date(String announce_date) {
        this.announce_date = announce_date;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public double getDiscount_rate() {
        return discount_rate;
    }

    public void setDiscount_rate(double discount_rate) {
        this.discount_rate = discount_rate;
    }

    public String getFront_image() {
        return front_image;
    }

    public void setFront_image(String front_image) {
        this.front_image = front_image;
    }

    public List<GameContent> getGameContents() {
        return gameContents;
    }

    public void setGameContents(List<GameContent> gameContents) {
        this.gameContents = gameContents;
    }
}
