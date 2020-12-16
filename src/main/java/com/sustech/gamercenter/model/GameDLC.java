package com.sustech.gamercenter.model;


import javax.persistence.*;

@Entity
@Table(name = "game_dlc")
public class GameDLC {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "game_id")
    private Long gameId;

    private String name;

    private Double price;

    private String path;

    private Boolean visible;


    public GameDLC() {
    }

    public GameDLC(Long gameId, String name, Double price, Boolean visible) {
        this.gameId = gameId;
        this.name = name;
        this.price = price;
        this.visible = visible;
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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Long getGameId() {
        return gameId;
    }

    public void setGameId(Long gameId) {
        this.gameId = gameId;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Boolean getVisible() {
        return visible;
    }

    public void setVisible(Boolean visible) {
        this.visible = visible;
    }
}
