package com.sustech.gamercenter.model;


import org.junit.jupiter.api.Test;

import java.sql.Timestamp;

public class PlayerTest {

    @Test
    public void UserGenerate() {
        User user = new Player(1L,
                "calvin",
                "email@email.com",
                "password.hashed",
                "adpt",
                true,
                false,
                new Timestamp(System.currentTimeMillis())
        );

        System.out.println(user);
        System.out.println(user.hashCode());

    }
}
