package com.lihebin.blog.service;

import com.lihebin.blog.bean.Request;
import com.lihebin.blog.bean.Result;
import com.lihebin.blog.bean.User;
import com.lihebin.blog.bean.UserMenu;
import com.lihebin.blog.dao.CacheDao;
import com.lihebin.blog.dao.UserDao;
import com.lihebin.blog.utils.MD5Util;
import com.lihebin.blog.utils.StringUtil;
import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Created by lihebin on 2018/12/2.
 */
@Service
public class UserServiceImpl implements UserService {
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Value("${salt_password}")
    private String SALT;

    @Autowired
    private UserDao userDao;

    @Autowired
    private CacheDao cacheDao;


    @Override
    public Map login(String username, String password) {
        if (StringUtil.empty(username) || StringUtil.empty(password)) {
            return StringUtil.hashMap(Result.CODE, Result.FAIL_CODE, Result.MESSAGE, "用户名或密码为空");
        }
        User user = userDao.queryUserByUsername(username);
        if (!password.equals(user.getPassword())) {
            return StringUtil.hashMap(Result.CODE, Result.FAIL_CODE, Result.MESSAGE, "密码错误");
        }
        String uuid = UUID.randomUUID().toString();
        String con = username.concat(SALT).concat(uuid);
        String sign = MD5Util.getSign(con);
        cacheDao.removeValue(sign);
        cacheDao.cacheValue(sign, username, 6, TimeUnit.HOURS);
        return StringUtil.hashMap(Result.CODE, Result.SUCCESS_CODE, User.NAME, user.getName(), User.USERNAME, username, User.TOKEN, sign);
    }

    @Override
    public List<UserMenu> listMenuByToken(String token) {
        String username = cacheDao.getValue(token);
        if (StringUtil.empty(username)) {
            return new ArrayList<>();
        }
        return listMenuByUsername(username);
    }

    @Override
    public Map releaseMenuAndUser(Map params) {
        String username = MapUtils.getString(params, User.USERNAME);
        try {
            int[] menuSns = (int[]) MapUtils.getObject(params, UserMenu.MENU_SNS);
            if (!userDao.relieveUserMenuByUserAndSn(username, -1)) {
                return StringUtil.hashMap(Result.CODE, Result.FAIL_CODE, Result.MESSAGE, "删除权限失败");
            }
            if (!userDao.insertUserMenus(username, menuSns)) {
                return StringUtil.hashMap(Result.CODE, Result.FAIL_CODE, Result.MESSAGE, "添加权限失败");
            }
            remove(username);
            return StringUtil.hashMap(Result.CODE, Result.SUCCESS_CODE, Result.MESSAGE, "添加权限成功");

        } catch (Exception e) {
            return StringUtil.hashMap(Result.CODE, Result.FAIL_CODE, Result.MESSAGE, "功能编号错误");
        }
    }

    @Override
    public Map insertOrDeleteUser(Map params) {
        String username = MapUtils.getString(params, User.USERNAME);
        String name = MapUtils.getString(params, User.NAME);
        String type = MapUtils.getString(params, Request.TYPE);

        switch (type) {
            case Request.INSERT:
                User user = userDao.queryUserByUsername(username);
                if (!StringUtil.empty(user.getUsername())) {
                    return StringUtil.hashMap(Result.CODE, Result.SUCCESS_CODE, Result.MESSAGE, "账号已存在", User.USERNAME, username);
                }
                String passward = StringUtil.randomPassword();
                String con = passward.concat(SALT);
                String sign = MD5Util.getSign(con);
                boolean status = userDao.insertUsername(username, name, sign, 0);
                if (status) {
                    return StringUtil.hashMap(Result.CODE, Result.SUCCESS_CODE, Result.MESSAGE, "账号创建成功", User.USERNAME, username, User.PASSWORD, passward);
                }
                return StringUtil.hashMap(Result.CODE, Result.FAIL_CODE, Result.MESSAGE, "账号创建失败:" + params.toString());
            case Request.DELETE:
                userDao.deleteUsername(username);
                userDao.relieveUserMenuByUserAndSn(username, -1);
                return StringUtil.hashMap(Result.CODE, Result.SUCCESS_CODE, Result.MESSAGE, "账号注销成功", User.USERNAME, username);
            default:
                return new HashMap();

        }
    }

    @Override
    public Map updatePassword(Map params) {
        return null;
    }

    @Override
    public Map logout(String token) {
        boolean result = cacheDao.removeValue(token);
        if (result) {
            return StringUtil.hashMap(Result.CODE, Result.SUCCESS_CODE);
        }
        return StringUtil.hashMap(Result.CODE, Result.FAIL_CODE);
    }

    @Cacheable(value = "listMenu", key = "username")
    public List<UserMenu> listMenuByUsername(String username) {
        return userDao.listUserMenu(username);
    }

    @CacheEvict(value = "listMenu", key = "username")
    public void remove(String username) {
        log.info("remove cache:{}", username);
    }
}
