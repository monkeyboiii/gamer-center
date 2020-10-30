package com.sustech.gamercenter.model;

import com.sun.istack.NotNull;

import javax.persistence.*;

@Entity
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotNull
    private String name;
    private double price;
    private boolean is_announced;
    private boolean is_downloadable;
    private double score;
    private String description;
    private long developer_id;

//    @OneToMany(cascade = CascadeType.ALL)
//    @JoinColumn(name = "game_id")
//    private List<GameContent> gameContents;
//
//    @OneToMany(cascade = CascadeType.ALL)
//    @JoinColumn(name = "game_id")
//    private List<GameDiscount> gameDiscounts;
//
//    public List<GameDiscount> getGameDiscounts() {
//        return gameDiscounts;
//    }
//
//    public void setGameDiscounts(List<GameDiscount> gameDiscounts) {
//        this.gameDiscounts = gameDiscounts;
//    }

    public long getDeveloper_id() {
        return developer_id;
    }

    public void setDeveloper_id(long developer_id) {
        this.developer_id = developer_id;
    }
//
//    public List<GameContent> getGameContents() {
//        return gameContents;
//    }
//
//    public void setGameContents(List<GameContent> gameContents) {
//        this.gameContents = gameContents;
//    }
//


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

    public boolean isIs_announced() {
        return is_announced;
    }

    public void setIs_announced(boolean is_announced) {
        this.is_announced = is_announced;
    }

    public boolean isIs_downloadable() {
        return is_downloadable;
    }

    public void setIs_downloadable(boolean is_downloadable) {
        this.is_downloadable = is_downloadable;
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


}
