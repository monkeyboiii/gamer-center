package com.sustech.gamercenter.repository;

import com.sustech.gamercenter.model.User;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void whenFindByName_thenReturnEmployee() {
        // given
        User calvin = new User();
        calvin.setName("calvin");
        calvin.setEmail("1283045105@qq.com");
        calvin.setPassword("password.hashed");
        calvin.setRole("adpt");
//        User calvin = new User(1L,
//                "calvin",
//                "email@email.com",
//                "password.hashed",
//                "adpt",
//                true,
//                false,
//                new Timestamp(System.currentTimeMillis())
//        );

        entityManager.persist(calvin);
        entityManager.flush();

        // when
        User found = userRepository
                .findByNameIgnoreCase(calvin.getName())
                .orElse(new User());

        // then
        assertEquals("Should be equal", found.getName(), calvin.getName());
    }
}
