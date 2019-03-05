package com.shanzhaozhen.classroom.config;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import com.shanzhaozhen.classroom.param.ResultParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Date;


//@ControllerAdvice()
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(MyJwtTokenProvider.class);

    /**
     * 底层异常，通常由未知错误抛出
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = Exception.class)
    public ResultParam exceptionHandler(Exception e) {
        logger.error("位置错误：" + e.getMessage());
        Date data = new Date(System.currentTimeMillis());
        return new ResultParam(data, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), "发生未知错误");
    }


    /**
     * jwt 签名异常
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(value = SignatureException.class)
    public ResultParam signatureExceptionHandler(SignatureException e) {
        logger.error("位置错误：" + e.getMessage());
        Date data = new Date(System.currentTimeMillis());
        return new ResultParam(data, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), "签名异常");
    }


    /**
     * jwt 格式错误
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(value = MalformedJwtException.class)
    public ResultParam malformedJwtExceptionHandler(MalformedJwtException e) {
        logger.error("位置错误：" + e.getMessage());
        Date data = new Date(System.currentTimeMillis());
        return new ResultParam(data, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), "JWT格式错误");
    }

    /**
     * jwt Token已过期
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(value = ExpiredJwtException.class)
    public ResultParam expiredJwtExceptionHandler(ExpiredJwtException e) {
        logger.error("位置错误：" + e.getMessage());
        Date data = new Date(System.currentTimeMillis());
        return new ResultParam(data, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), "token已过期");
    }

    /**
     * jwt 不支持该token
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(value = UnsupportedJwtException.class)
    public ResultParam unsupportedJwtExceptionHandler(UnsupportedJwtException e) {
        logger.error("位置错误：" + e.getMessage());
        Date data = new Date(System.currentTimeMillis());
        return new ResultParam(data, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), "不支持该token");
    }

    /**
     * jwt 参数错误异常
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(value = IllegalArgumentException.class)
    public ResultParam illegalArgumentExceptionHandler(IllegalArgumentException e) {
        logger.error("位置错误：" + e.getMessage());
        Date data = new Date(System.currentTimeMillis());
        return new ResultParam(data, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), "参数错误异常");
    }



}
