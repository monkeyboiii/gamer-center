package com.sustech.gamercenter.model;


import org.junit.jupiter.api.Test;

import java.sql.Timestamp;

public class UserTest {

    @Test
    public void UserGenerate() {
        User user = new User(1L,
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
