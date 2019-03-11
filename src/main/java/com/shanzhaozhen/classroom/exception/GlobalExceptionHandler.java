package com.shanzhaozhen.classroom.exception;

import com.shanzhaozhen.classroom.common.JwtErrorConst;
import com.shanzhaozhen.classroom.config.MyJwtTokenProvider;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import com.shanzhaozhen.classroom.param.ResultParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Date;


//@RestControllerAdvice
//@RestController
public class GlobalExceptionHandler extends BasicErrorController {

    private static final Logger logger = LoggerFactory.getLogger(MyJwtTokenProvider.class);

    public GlobalExceptionHandler() {
        super(new DefaultErrorAttributes(), new ErrorProperties());
    }

    /**
     * 底层异常，通常由未知错误抛出
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = Exception.class)
    public ResultParam exceptionHandler(Exception e) {
        logger.error("位置错误：" + e.getMessage());
        Date date = new Date(System.currentTimeMillis());
        return new ResultParam(date, HttpStatus.INTERNAL_SERVER_ERROR.value(), 5000, e.getClass().getName(), e.getMessage());
    }


    /**
     * jwt 签名异常
     */
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(value = SignatureException.class)
    public ResultParam signatureExceptionHandler(SignatureException e) {
        logger.error("位置错误：" + e.getMessage());
        Date data = new Date(System.currentTimeMillis());
        return new ResultParam(data, HttpStatus.INTERNAL_SERVER_ERROR.value(),
                JwtErrorConst.JWT_SIGNATURE.getCode(), e.getClass().getName(), JwtErrorConst.JWT_SIGNATURE.getReason());
    }


    /**
     * jwt 格式错误
     */
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(value = MalformedJwtException.class)
    public ResultParam malformedJwtExceptionHandler(MalformedJwtException e) {
        logger.error("位置错误：" + e.getMessage());
        Date date = new Date(System.currentTimeMillis());
        return new ResultParam(date, HttpStatus.INTERNAL_SERVER_ERROR.value(),
                JwtErrorConst.JWT_MALFORMED.getCode(), e.getClass().getName(), JwtErrorConst.JWT_MALFORMED.getReason());
    }

    /**
     * jwt Token已过期
     */
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(value = ExpiredJwtException.class)
    public ResultParam expiredJwtExceptionHandler(ExpiredJwtException e) {
        logger.error("位置错误：" + e.getMessage());
        Date date = new Date(System.currentTimeMillis());
        return new ResultParam(date, HttpStatus.INTERNAL_SERVER_ERROR.value(),
                JwtErrorConst.JWT_EXPIRED.getCode(), e.getClass().getName(), JwtErrorConst.JWT_EXPIRED.getReason());


    }

    /**
     * jwt 不支持该token
     */
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(value = UnsupportedJwtException.class)
    public ResultParam unsupportedJwtExceptionHandler(UnsupportedJwtException e) {
        logger.error("位置错误：" + e.getMessage());
        Date date = new Date(System.currentTimeMillis());
        return new ResultParam(date, HttpStatus.INTERNAL_SERVER_ERROR.value(),
                JwtErrorConst.JWT_UNSUPPORTED.getCode(), e.getClass().getName(), JwtErrorConst.JWT_UNSUPPORTED.getReason());

    }

    /**
     * jwt 参数错误异常
     */
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(value = IllegalArgumentException.class)
    public ResultParam illegalArgumentExceptionHandler(IllegalArgumentException e) {
        logger.error("位置错误：" + e.getMessage());
        Date date = new Date(System.currentTimeMillis());
        return new ResultParam(date, HttpStatus.INTERNAL_SERVER_ERROR.value(),
                JwtErrorConst.JWT_ERROR.getCode(), e.getClass().getName(), JwtErrorConst.JWT_ERROR.getReason());

    }



}
