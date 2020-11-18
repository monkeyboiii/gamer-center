//package com.sustech.gamercenter.model;
//
//import javax.persistence.*;
//
//@Entity
//public class GameDiscount {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private long id;
//
//    private String start;
//    private String end;
//    private double rate;
//    private long game_id;
//
////    @ManyToOne(cascade={CascadeType.ALL})
////    private Game game;
////
////    public Game getGame() {
////        return game;
////    }
////
////    public void setGame(Game game) {
////        this.game = game;
////    }
//
//    public long getGame_id() {
//        return game_id;
//    }
//
//    public void setGame_id(long game_id) {
//        this.game_id = game_id;
//    }
//
//    public long getId() {
//        return id;
//    }
//
//    public void setId(long id) {
//        this.id = id;
//    }
//
//    public String getStart() {
//        return start;
//    }
//
//    public void setStart(String start) {
//        this.start = start;
//    }
//
//    public String getEnd() {
//        return end;
//    }
//
//    public void setEnd(String end) {
//        this.end = end;
//    }
//
//    public double getRate() {
//        return rate;
//    }
//
//    public void setRate(double rate) {
//        this.rate = rate;
//    }
//}
