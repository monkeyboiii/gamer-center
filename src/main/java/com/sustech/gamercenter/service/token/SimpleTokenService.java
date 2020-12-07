package com.sustech.gamercenter.service.token;

import com.sustech.gamercenter.model.User;
import com.sustech.gamercenter.util.exception.InvalidTokenException;
import com.sustech.gamercenter.util.exception.UnauthorizedAttemptException;
import com.sustech.gamercenter.util.exception.UserHasNoTokenException;
import com.sustech.gamercenter.util.exception.UserNotFoundException;

public interface SimpleTokenService {

    String createToken(User user);

    String createTokenFromId(Long id) throws UserNotFoundException;

    /**
     * currently used by interceptor
     */
    void checkToken(String token) throws InvalidTokenException;

    void checkTokenRole(String token, String role) throws InvalidTokenException, UnauthorizedAttemptException;

    //

    Long getIdByToken(String token) throws InvalidTokenException;

    String getTokenById(Long id) throws UserHasNoTokenException;

    //

    void deleteToken(String token) throws InvalidTokenException, UserHasNoTokenException;

    void deleteToken(Long id) throws UserHasNoTokenException, InvalidTokenException;

    //
    //

    void createConfirmationCode(String email, String code, Integer expire);

    boolean compareConfirmationCode(String email, String code);
}
