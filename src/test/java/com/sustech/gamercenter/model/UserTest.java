package com.sustech.gamercenter.model;


import org.junit.jupiter.api.Test;

public class UserTest {

    @Test
    public void UserGenerate() {
        User user = new User(1L,
                "calvin",
                "email@email.com",
                "password.hashed",
                "adpt"
        );

        System.out.println(user);
        System.out.println(user.hashCode());

    }
}