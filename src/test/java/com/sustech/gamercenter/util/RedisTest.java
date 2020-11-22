package com.sustech.gamercenter.util;

import com.sustech.gamercenter.model.User;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;

import java.io.Serializable;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisTest {

    @Autowired
    private RedisTemplate<String, Serializable> redisTemplate;

    @Test
    public void testObj() throws Exception {
        User user = new User(11345246L, "name", "email", "password-hashed", "apd");
        ValueOperations<String, Serializable> operations = redisTemplate.opsForValue();
        operations.set("fdd2", user);

        Boolean exists = redisTemplate.hasKey("fdd2");
        Assert.notNull(exists,"not exist. /flow assertion");
        System.out.println("redis是否存在相应的key" + exists.toString());

        User getUser = (User) redisTemplate.opsForValue().get("fdd2");
        System.out.println("从redis数据库获取的user:" + getUser.toString());
    }
}
