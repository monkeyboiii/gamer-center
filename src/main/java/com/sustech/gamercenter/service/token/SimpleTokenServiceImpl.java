package com.sustech.gamercenter.service.token;

import com.sustech.gamercenter.dao.UserRepository;
import com.sustech.gamercenter.model.User;
import com.sustech.gamercenter.util.exception.InvalidTokenException;
import com.sustech.gamercenter.util.exception.UnauthorizedAttemptException;
import com.sustech.gamercenter.util.exception.UserHasNoTokenException;
import com.sustech.gamercenter.util.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * saves
 * login id-token pair, login role,
 * mail confirmation code
 * in memory database redis
 */
@Service
public class SimpleTokenServiceImpl implements SimpleTokenService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private UserRepository userRepository;


    @Override
    public String createToken(User user, String role) {
        String token = UUID.randomUUID().toString();
        String id = user.getId().toString();
        String id_role = id + "_role";

        int expire = 24;
        redisTemplate.opsForValue().set(token, id, expire, TimeUnit.HOURS);
        redisTemplate.opsForValue().set(id, token, expire, TimeUnit.HOURS);
        redisTemplate.opsForValue().set(id_role, role, expire, TimeUnit.HOURS);

        return token;
    }

    @Override
    public String createTokenFromId(Long id, String role) throws UserNotFoundException {
        try {
            return createToken(userRepository.getOne(id), role);
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

    @Override
    public void checkTokenRole(String token, String role) throws InvalidTokenException, UnauthorizedAttemptException {
        Long id = getIdByToken(token);
        String loggedInAs = redisTemplate.boundValueOps(id.toString() + "_role").get();
        if (!role.equals(loggedInAs)) {
            throw new UnauthorizedAttemptException("Your role has no authority");
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
    public void deleteToken(String token) throws InvalidTokenException {
        Long id = getIdByToken(token);
        redisTemplate.delete(token);
        redisTemplate.delete(id.toString());
        redisTemplate.delete("id_role");
    }

    @Override
    public void deleteToken(Long id) throws UserHasNoTokenException, InvalidTokenException {
        String token = getTokenById(id);
        deleteToken(token);
    }


    //
    // mail confirmation


    @Override
    public void createConfirmationCode(String email, String code, Integer expire) {
        redisTemplate.opsForValue().set(email, code, expire, TimeUnit.MINUTES);
    }

    public boolean compareConfirmationCode(String email, String code) {
        return code.equals(redisTemplate.boundValueOps(email).get());
    }
}
