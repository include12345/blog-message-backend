package com.lihebin.blog.config;

import com.lihebin.blog.dao.CacheDao;
import com.lihebin.blog.dao.UserDao;
import com.lihebin.blog.exception.BackendLoginException;
import com.lihebin.blog.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by lihebin on 03/01/2018.
 */
@Configuration
public class TokenInterceptor extends HandlerInterceptorAdapter {
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private CacheDao cacheDao;

    @Autowired
    private UserDao userDao;



    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token;
        try {
            token = request.getHeader("token");
        } catch (Exception e) {
            throw new BackendLoginException("登录超时");
        }
        log.info("TokenInterceptor:{}", token);
        String[] param = token.split("-");
        String method = request.getRequestURI();
//        String timeOut = cacheService.getCache(token);
//        if (!StringUtil.empty(timeOut)) {
//            throw new BackendRepeatException("请求过于频繁");
//        }
//        cacheService.setCache(token,  "timeOut",  100, "milliseconds");
        if (!method.contains(param[1])) {
            if (!((method.contains("getMenuAll") || method.contains("getAllUsername") || method.contains("getMenuByUsername")) && param[1].equals("relevanceUserAndMenu"))) {
                throw new BackendLoginException("登录超时");
            }
        }
        String username = cacheDao.getValue(param[0]);
        log.info("request:{},{}", method, username);
        if(!StringUtil.empty(username) && userDao.queryUserAndMenu(username, param[1])){
            return true;
        } else {
            throw new BackendLoginException("登录超时");
        }
    }
}
