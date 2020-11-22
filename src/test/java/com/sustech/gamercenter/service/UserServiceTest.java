//package com.sustech.gamercenter.service;
//
//import com.sustech.gamercenter.dao.UserDao;
//import com.sustech.gamercenter.model.User;
//import org.junit.Assert;
//import org.junit.Before;
//import org.junit.jupiter.api.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.TestConfiguration;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.context.annotation.Bean;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import static org.mockito.BDDMockito.given;
//
//
//@RunWith(SpringRunner.class)
//public class UserServiceTest {
//    @TestConfiguration
//    static class UserServiceTestContextConfiguration {
//        @Bean
//        public UserService userService() {
//            return new UserService();
//        }
//    }
//
//    @Autowired
//    private UserService userService;
//
//    @Autowired
//    private UserDao userDao;
//
//    private String name;
//    private Long id;
//
//    @Before
//    public void setUp() {
//        this.id = 1L;
//        this.name = "calvin";
//
//        User user = new User(id, name,
//                "email@email.com",
//                "password.hashed",
//                "adpt"
//        );
//
//        given(userDao.findById(user.getId())).willReturn(java.util.Optional.of(user));
//
//        System.out.println(user.toString());
//    }
//
//    @Test
//    public void testGetByName() {
//        User result = this.userService.queryUserById(1L);
//        Assert.assertEquals(name.concat(" is not equal to").concat(result.getName()), name, result.getName());
//    }
//
//}