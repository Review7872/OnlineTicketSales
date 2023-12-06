package online.fadai.buytickets.controller;

import lombok.extern.slf4j.Slf4j;
import online.fadai.buytickets.exception.*;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class ControllerExceptionAdvice {
    public static final int SERVER_ERROR_STATUS = 500;
    public static final int AUTH_ERROR_STATUS = 401;
    public static final int USER_ERROR_STATUS = 403;

    @ExceptionHandler({BuyTicketSaleBackException.class})
    public Map<String, Object> buyTicketSaleBackException(BuyTicketSaleBackException e) {
        log.error(e.getMessage());
        Map<String, Object> map = new HashMap<>();
        map.put("status", SERVER_ERROR_STATUS);
        map.put("message", "退票失败，请稍后再试");
        return map;
    }

    @ExceptionHandler({BuyTicketSaleException.class})
    public Map<String, Object> buyTicketSaleException(BuyTicketSaleException e) {
        log.error(e.getMessage());
        Map<String, Object> map = new HashMap<>();
        map.put("status", SERVER_ERROR_STATUS);
        map.put("message", "购票失败，请稍后再试");
        return map;
    }

    @ExceptionHandler({CreateAccountFailedException.class})
    public Map<String, Object> createAccountException(CreateAccountFailedException e) {
        log.error(e.getMessage());
        Map<String, Object> map = new HashMap<>();
        map.put("status", SERVER_ERROR_STATUS);
        map.put("message", "创建账号失败，请联系管理员");
        return map;
    }

    @ExceptionHandler({AuthErrorException.class})
    public Map<String, Object> authException(AuthErrorException e) {
        log.error(e.getMessage());
        Map<String, Object> map = new HashMap<>();
        map.put("status", AUTH_ERROR_STATUS);
        map.put("message", "身份信息错误");
        return map;
    }

    @ExceptionHandler({PasswordErrorException.class})
    public Map<String, Object> passwordErrorException(PasswordErrorException e) {
        log.error(e.getMessage());
        Map<String, Object> map = new HashMap<>();
        map.put("status", USER_ERROR_STATUS);
        map.put("message", "密码错误");
        return map;
    }
    @ExceptionHandler({RuntimeException.class})
    public Map<String, Object> runtimeException(RuntimeException e) {
        log.error(e.getMessage());
        Map<String, Object> map = new HashMap<>();
        map.put("status", USER_ERROR_STATUS);
        map.put("message", e.getMessage());
        return map;
    }


    @ExceptionHandler({Exception.class})
    public Map<String, Object> otherException(Exception e) {
        log.error(e.getMessage());
        Map<String, Object> map = new HashMap<>();
        map.put("status", SERVER_ERROR_STATUS);
        map.put("message", "网络繁忙，请稍后再试");
        return map;
    }
}
