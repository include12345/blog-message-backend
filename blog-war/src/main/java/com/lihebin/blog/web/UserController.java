package com.lihebin.blog.web;

import com.lihebin.blog.bean.Request;
import com.lihebin.blog.bean.User;
import com.lihebin.blog.bean.UserMenu;
import com.lihebin.blog.service.UserService;
import com.lihebin.blog.utils.StringUtil;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lihebin on 2018/12/2.
 */
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/user/login", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    public Map login(@RequestBody Map params) throws Exception {
        String username = MapUtils.getString(params, User.USERNAME);
        String password = MapUtils.getString(params, User.PASSWORD);
        if (StringUtil.empty(username) || StringUtil.empty(password)) {
            return new HashMap();
        }
        return userService.login(username, password);
    }

    @RequestMapping(value = "/user/getMenus", method = RequestMethod.GET)
    public List<UserMenu> getMenus(@RequestParam(value = "token", required = true) String token) {
        return userService.listMenuByToken(token);
    }


    @RequestMapping(value = "/api/relevanceUserAndMenu", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public Map relevanceUserAndMenu(@RequestBody Map params) {
        String username = MapUtils.getString(params, User.USERNAME);
        int[] menuSns = (int[]) MapUtils.getObject(params, UserMenu.MENU_SNS);
        if (StringUtil.empty(username) || menuSns == null) {
            return new HashMap();
        }
        return userService.releaseMenuAndUser(params);
    }


    @RequestMapping(value = "/api/insertAndDeleteUser", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public Map insertAndDeleteUser(@RequestBody Map params) {
        String username = MapUtils.getString(params, User.USERNAME);
        String type = MapUtils.getString(params, Request.TYPE);
        if (StringUtil.empty(username) || StringUtil.empty(type)) {
            return new HashMap();
        }
        return userService.insertOrDeleteUser(params);
    }

    @RequestMapping(value = "/user/logout", method = RequestMethod.GET)
    public Map logout(@RequestParam(value = "token", required = true) String token) {
        return userService.logout(token);
    }
}
