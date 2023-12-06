package online.fadai.buytickets.aop;

import jakarta.annotation.Resource;
import online.fadai.buytickets.service.AccountService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AuthConfig {
    @Resource
    private AccountService accountService;
    @Around("execution(* online.fadai.buytickets.controller.user.*.*(..))")
    public Object userAuth(ProceedingJoinPoint pjp){
        try {
            Object[] args = pjp.getArgs();
            if (!"NotJWT".equals(args[0])){
                if ("1".equals(accountService.getUserAuth((String) args[0]))){
                    return pjp.proceed();
                }else {
                    throw new  RuntimeException("您没有权限");
                }
            }else {
                throw new  RuntimeException("您未登录");
            }
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }
    @Around("execution(* online.fadai.buytickets.controller.admin.*.*(..))")
    public Object adminAuth(ProceedingJoinPoint pjp){
        try {
            Object[] args = pjp.getArgs();
            if (!"NotJWT".equals(args[0])){
                if ("1".equals(accountService.getUserAuth((String) args[0]))){
                    return pjp.proceed();
                }else {
                    throw new  RuntimeException("您没有权限");
                }
            }else {
                throw new  RuntimeException("您未登录");
            }
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }
}

