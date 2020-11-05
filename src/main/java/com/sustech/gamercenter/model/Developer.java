package com.sustech.gamercenter.model;

import javax.persistence.Entity;
import java.util.List;
import java.util.UUID;


public class Developer extends User {
    protected UUID uuid;

    List<String> game;

    public Developer() {
    }
}
