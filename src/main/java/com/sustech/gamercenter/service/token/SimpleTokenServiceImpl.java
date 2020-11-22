package com.sustech.gamercenter.service.token;

import com.sustech.gamercenter.model.User;
import com.sustech.gamercenter.repository.UserRepository;
import com.sustech.gamercenter.util.exception.UserHasNoTokenException;
import com.sustech.gamercenter.util.exception.InvalidTokenException;
import com.sustech.gamercenter.util.exception.UserNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class SimpleTokenServiceImpl implements SimpleTokenService {

    private static final Logger logger = LoggerFactory.getLogger(TokenServiceRedisImpl.class);

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private UserRepository userRepository;


    @Override
    public String createToken(User user) {
        String token = UUID.randomUUID().toString();
        String id = user.getId().toString();

        redisTemplate.opsForValue().set(token, id, 24, TimeUnit.HOURS);
        redisTemplate.opsForValue().set(id, token, 24, TimeUnit.HOURS);

        logger.warn(token);
        logger.warn(id);

//        redisTemplate.boundValueOps(token).set(id, 24, TimeUnit.HOURS);
//        redisTemplate.boundValueOps(id).set(token, 24, TimeUnit.HOURS);

//        userPool.add(String.valueOf(user.getId()), token);

        return token;
    }

    @Override
    public String createTokenFromId(Long id) throws UserNotFoundException {
        try {
            return createToken(userRepository.getOne(id));
        } catch (Exception e) {
            throw new UserNotFoundException("User with id " + id + " not found", e);
        }
    }


    //


    @Override
    public void checkToken(String token) throws InvalidTokenException {
        Boolean result = redisTemplate.hasKey(token);
        if (result == null || result.equals(Boolean.FALSE)) {
            throw new InvalidTokenException("Invalid token " + token);
        }
    }

    //


    @Override
    public Long getIdByToken(String token) throws InvalidTokenException {
        checkToken(token);
        return Long.valueOf(Objects.requireNonNull(redisTemplate.boundValueOps(token).get()));
    }

    @Override
    public String getTokenById(Long id) throws UserHasNoTokenException {
        try {
            checkToken(id.toString());
        } catch (Exception e) {
            throw new UserHasNoTokenException("User " + id + " has no token", e);
        }
        return redisTemplate.boundValueOps(id.toString()).get();
    }


    //


    @Override
    public void deleteToken(String token) throws InvalidTokenException, UserHasNoTokenException {
        Long id = getIdByToken(token);
        deleteToken(id);
        redisTemplate.delete(token);
    }

    @Override
    public void deleteToken(Long id) throws UserHasNoTokenException, InvalidTokenException {
        String token = getTokenById(id);
        deleteToken(token);
    }
}
