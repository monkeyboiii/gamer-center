package com.sustech.gamercenter.util.config;

import com.sustech.gamercenter.util.model.GlobalResponse;
import com.sustech.gamercenter.util.exception.MyException;
import com.sustech.gamercenter.util.model.JsonResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;

@ControllerAdvice
public class ExceptionHandlerConfig {

    private static final Logger logger = LoggerFactory.getLogger(ExceptionHandlerConfig.class);
    private static final String msg = "Something has gone terribly wrong!";


    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public JsonResponse globalExceptionHandler(Exception e) {
        String customMessage = e.getMessage() == null ? msg : e.getMessage();

        logger.error("caught in GlobalExceptionHandler ".toUpperCase() + e.getClass().getName() + customMessage);

        Arrays.stream(e.getStackTrace())
                .filter(x -> x.toString().startsWith("com.sustech.gamercenter"))
                .forEach(x -> logger.warn(x.toString()));

        // debug
        e.printStackTrace();

        return new JsonResponse.builder()
                .code(-1)
                .msg(e.getClass().getSimpleName())
                .data(customMessage)
                .build();
    }


    //
    //
    // zeng ming


    @ExceptionHandler(value = MyException.class)
    @ResponseBody
    public GlobalResponse<String> exceptionHandler(MyException e) {
        System.out.println("未知异常！原因是:" + e);
        return GlobalResponse.<String>builder()
                .code(e.getCode())
                .msg(e.getMessage())
                .build();
    }
}
