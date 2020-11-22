package com.sustech.gamercenter.service.token;


import com.sustech.gamercenter.model.User;
import com.sustech.gamercenter.util.exception.InvalidTokenException;
import com.sustech.gamercenter.util.exception.UserNotFoundException;
import com.sustech.gamercenter.util.model.TokenModel;


public interface TokenService {

    TokenModel createToken(User user);

    TokenModel createTokenFromId(Long id) throws UserNotFoundException;

    TokenModel checkToken(String token) throws InvalidTokenException;

    void deleteToken(String token);
}
