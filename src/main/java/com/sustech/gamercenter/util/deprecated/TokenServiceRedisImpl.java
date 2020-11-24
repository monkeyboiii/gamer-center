package com.sustech.gamercenter.util.deprecated;

import com.sustech.gamercenter.model.User;
import com.sustech.gamercenter.dao.UserRepository;
import com.sustech.gamercenter.util.exception.InvalidTokenException;
import com.sustech.gamercenter.util.exception.UserNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

//@Component("redis")
public class TokenServiceRedisImpl implements TokenService {

    private static Logger logger = LoggerFactory.getLogger(TokenServiceRedisImpl.class);

    private RedisTemplate<String, String> redisTemplate;
    private UserRepository userRepository;
//    private BoundSetOperations<String, String> userPool;

    @Autowired
    public TokenServiceRedisImpl(RedisTemplate<String, String> redisTemplate, UserRepository userRepository) {
        this.redisTemplate = redisTemplate;
        this.userRepository = userRepository;
//        this.userPool = redisTemplate.boundSetOps("UserPool");
    }

    @Override
    public TokenModel createToken(User user) {
        String token = UUID.randomUUID().toString();
        TokenModel TokenModel = new TokenModel(user.getId(), token, user.getRole());
        String value = JsonUtil.toJsonString(TokenModel);

        redisTemplate.boundValueOps(token).set(value, 24, TimeUnit.HOURS);
//        userPool.add(String.valueOf(user.getId()), token);

        return TokenModel;
    }

    @Override
    public TokenModel createTokenFromId(Long id) throws UserNotFoundException {
        try {
            return createToken(userRepository.getOne(id));
        } catch (Exception e) {
            throw new UserNotFoundException("User with id " + id.toString() + " doesn't exist");
        }
    }

    @Override
    public TokenModel checkToken(String token) throws InvalidTokenException {
        if (!(StringUtils.isEmpty(token))) {
            String json = redisTemplate.boundValueOps(token).get();
            logger.warn(json);
            TokenModel tokenModel = JsonUtil.fromJsonString(json, TokenModel.class);
            if (token.equals(tokenModel.getToken())) {
                redisTemplate.boundValueOps(token).expire(24, TimeUnit.HOURS);
                return tokenModel;
            }
        }
        throw new InvalidTokenException();
    }

    @Override
    public void deleteToken(String token) {
        redisTemplate.delete(token);
    }

}
