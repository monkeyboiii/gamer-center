package com.sustech.gamercenter.service;


import com.sustech.gamercenter.dao.UserDao;
import com.sustech.gamercenter.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserDao userDao;

    public User queryUserById(Long id) {
        try {
            User result = userDao.getOne(id);
            log.info(result.toString());
            return result;
        } catch (Exception e) {
            log.info(e.toString(), e);
            return new User();

        }
    }

    public User queryUserByName(String name) {
        User result = userDao.findByNameIgnoreCase(name);
        return result;
    }

    public User queryUserByEmail(String email) {
        User result = userDao.findByEmail(email);
        return result;
    }

    public Integer registerUser(User user) {
        User res = userDao.save(user);
        log.info(res.toString());
        return 1;
    }


}
