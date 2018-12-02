package com.lihebin.blog.config;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * Created by lihebin on 29/01/2018.
 */
@Aspect
@Component
@Order(1)
public class ServiceLogAspect {
  private final Logger log= LoggerFactory.getLogger(getClass());


  //申明一个切点 里面是 execution表达式
  @Pointcut("execution(public * com.lihebin.blog.service.serviceImpl.*.*(..))")
    private void serviceAspect(){

  }

  //请求method前打印内容
    @Before(value = "serviceAspect()")
    public void methodBefore(JoinPoint joinPoint){
        log.info("[ID{}][{}] request>>{}", Thread.currentThread().getId(), joinPoint.getSignature().getName(), Arrays.toString(joinPoint.getArgs()));
    }

    //在方法执行完结后打印返回内容
    @AfterReturning(returning = "returnObject",pointcut = "serviceAspect()")
    public void methodAfterReturning(JoinPoint joinPoint,Object returnObject){
        log.info("[ID{}][{}] response>>{}", Thread.currentThread().getId(), joinPoint.getSignature().getName(),returnObject);
    }


//  //申明一个切点 里面是 execution表达式
//  @Pointcut("execution(public * com.wosai.support.dao.impl.*.*(..))")
//  private void daoAspect(){
//
//  }
//
//
//  //请求method前打印内容
//  @Before(value = "daoAspect()")
//  public void daoBefore(JoinPoint joinPoint){
//    log.info("[ID{}][{}] dao request>>{}", Thread.currentThread().getId(), joinPoint.getSignature().getName(), Arrays.toString(joinPoint.getArgs()));
//  }
//
//  //在方法执行完结后打印返回内容
//  @AfterReturning(returning = "returnObject",pointcut = "daoAspect()")
//  public void daoAfterReturning(JoinPoint joinPoint,Object returnObject){
//    log.info("[ID{}][{}] dao response>>{}", Thread.currentThread().getId(), joinPoint.getSignature().getName(), JSON.toJSONString(returnObject));
//
//  }


    //申明一个切点 里面是 execution表达式
  @Pointcut("execution(public * com.lihebin.blog.web.*.*(..))")
  private void webAspect(){

  }


  //请求method前打印内容
  @Before(value = "webAspect()")
  public void webBefore(JoinPoint joinPoint){
    log.info("[ID{}][{}] web request>>{}", Thread.currentThread().getId(), joinPoint.getSignature().getName(), Arrays.toString(joinPoint.getArgs()));
  }

  //在方法执行完结后打印返回内容
  @AfterReturning(returning = "returnObject",pointcut = "webAspect()")
  public void webAfterReturning(JoinPoint joinPoint,Object returnObject){
    log.info("[ID{}][{}] web response>>{}", Thread.currentThread().getId(), joinPoint.getSignature().getName(), returnObject);
  }


}
