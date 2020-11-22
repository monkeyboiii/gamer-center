package com.sustech.gamercenter.util;

import com.sustech.gamercenter.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.Assert;

import java.io.Serializable;

@SpringBootTest
public class SerializationTest {

    @Autowired
    private RedisTemplate<String, Serializable> redisTemplate;


    @Test
    void contextLoads() {
        User user = new User();
        user.setName("15");
        user.setId(12L);
        user.setPassword("aosjvnoai");
        user.setBalance(133.213);

        redisTemplate.opsForValue().set(user.getName(), user);
        User getUser = (User) redisTemplate.opsForValue().get(user.getName());

        System.out.println(getUser);
        Assert.notNull(getUser, "user from redis is null");
        System.out.println(getUser.toString());
    }
}
