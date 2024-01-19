package com.example.newsservicesecurity.aspects;

import com.example.newsservicesecurity.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
public class AspectEditingAndDeletingNewsAndComment {

    private final UserService userService;

    @Pointcut("@annotation(GetUserIdAop)")
    public void handleGetUserIdAop() {
    }

    @Before("handleGetUserIdAop()")
    public void getUserAop(JoinPoint joinPoint) {

        Object[] args = joinPoint.getArgs();
        log.info("Aspect user id: " + args[0] + ", " + args[1]);
        userService.testAccessUserGetId((Long) args[0], (UserDetails) args[1]);
    }

}
