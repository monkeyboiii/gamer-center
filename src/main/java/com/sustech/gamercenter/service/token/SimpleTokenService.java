package com.sustech.gamercenter.service.token;

import com.sustech.gamercenter.model.User;
import com.sustech.gamercenter.util.exception.UserHasNoTokenException;
import com.sustech.gamercenter.util.exception.InvalidTokenException;
import com.sustech.gamercenter.util.exception.UserNotFoundException;

public interface SimpleTokenService {

    String createToken(User user);

    String createTokenFromId(Long id) throws UserNotFoundException;

    /**
     * currently used by interceptor
     */
    void checkToken(String token) throws InvalidTokenException;

    //

    Long getIdByToken(String token) throws InvalidTokenException;

    String getTokenById(Long id) throws UserHasNoTokenException;

    //

    void deleteToken(String token) throws InvalidTokenException, UserHasNoTokenException;

    void deleteToken(Long id) throws UserHasNoTokenException, InvalidTokenException;
}
