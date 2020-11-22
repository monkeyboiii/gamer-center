package com.sustech.gamercenter.repository;

import com.sustech.gamercenter.model.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Optional;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class UserRepositoryTest2 {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void findByIdTest() {
        Optional<User> optional = userRepository.findById(1L);
        assert optional.isPresent();
        Assert.assertNotNull(optional.get());
        System.out.println(optional);
    }

}