package com.sustech.gamercenter.repository;

import com.sustech.gamercenter.dao.UserDao;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Optional;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class UserDaoTest2 {

    @Autowired
    private UserDao userDao;

    @Test
    public void findByIdTest() {
        Optional optional = userDao.findById(1L);
        Assert.assertNotNull(optional.get());
        System.out.println(optional);
    }

}