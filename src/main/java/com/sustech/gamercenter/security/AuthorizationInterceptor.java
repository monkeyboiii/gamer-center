package com.sustech.gamercenter.security;

import com.sustech.gamercenter.service.token.SimpleTokenService;
import com.sustech.gamercenter.util.exception.InvalidTokenException;
import com.sustech.gamercenter.util.exception.UnauthorizedAttemptException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * intercept httpRequest
 * take the token in header
 * authenticate/authorize
 */
public class AuthorizationInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(AuthorizationInterceptor.class);

    private static final String httpHeaderName = "token";
    private final String unauthorizedErrorMessage = "401 unauthorized";
    private final int unauthorizedErrorCode = HttpServletResponse.SC_UNAUTHORIZED;
    public static final String REQUEST_CURRENT_KEY = "REQUEST_CURRENT_KEY";

    @Autowired
    SimpleTokenService tokenService;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws InvalidTokenException, UnauthorizedAttemptException {
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        AuthToken auth = method.getAnnotation(AuthToken.class);

        if (auth != null || handlerMethod.getBeanType().getAnnotation(AuthToken.class) != null) {
            String token = request.getHeader(httpHeaderName);
            logger.info("Get token from request is {} ", token);

            if (StringUtils.isEmpty(auth.role())) {
                tokenService.checkToken(token); // pass or throws
            } else {
                tokenService.checkTokenRole(token, auth.role()); // pass or throws
            }
        }

        request.setAttribute(REQUEST_CURRENT_KEY, null);

        return true;
    }


    //
    //
    //

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
    }
}
