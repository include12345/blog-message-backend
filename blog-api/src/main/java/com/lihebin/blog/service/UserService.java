package com.lihebin.blog.service;

import com.lihebin.blog.annotation.BackendAnnotation;
import com.lihebin.blog.bean.User;
import com.lihebin.blog.bean.UserMenu;
import com.sun.org.glassfish.external.probe.provider.annotations.ProbeParam;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Map;

/**
 * Created by lihebin on 2018/12/2.
 */
@BackendAnnotation
@Validated
public interface UserService {

    /**
     * 登录
     * @param username
     * @param password
     * @return
     */
    Map login(String username, String password);

    /**
     * 根据token获取功能列表
     * @param token
     * @return
     */
    List<UserMenu> listMenuByToken(String token);


    /**
     * 变更权限
     * @param params
     * @return
     */
    Map releaseMenuAndUser(Map params);


    /**
     * 添加或删除账号
     * @param params
     * @return
     */
    Map insertOrDeleteUser(Map params);


    Map updatePassword(Map params);

    /**
     * 退出
     * @param token
     * @return
     */
    Map logout(String token);


}
