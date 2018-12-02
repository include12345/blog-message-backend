package com.lihebin.blog.dao;

import com.lihebin.blog.bean.User;
import com.lihebin.blog.bean.UserMenu;

import java.util.List;

/**
 * Created by lihebin on 2018/12/2.
 */
public interface UserDao {
    /**
     * 根据用户名查用户信息
     *
     * @param username
     * @return
     */
    User queryUserByUsername(String username);

    /**
     * 根据用户名查权限列表
     *
     * @param username
     * @return
     */
    List<UserMenu> listUserMenu(String username);


    /**
     * 创建账号
     *
     * @param username
     * @param name
     * @param password
     * @param role
     * @return
     */
    boolean insertUsername(String username, String name, String password, int role);

    /**
     * 删除账号
     *
     * @param username
     * @return
     */
    boolean deleteUsername(String username);

    /**
     * 根据用户名和sn查是否有数据
     *
     * @param username
     * @param menu
     * @return
     */
    boolean queryUserMenuByUserAndSn(String username, int menu);

    /**
     * 写入user_menu数据库
     *
     * @param username
     * @param menu
     * @return
     */
    boolean insertUserMenuByUserAndSn(String username, int menu);

    /**
     * 创建账号
     * @param username
     * @param menus
     * @return
     */
    boolean insertUserMenus(String username, int[] menus);



    /**
     * 删除user_menu数据
     *
     * @param username
     * @param menu
     * @return
     */
    boolean relieveUserMenuByUserAndSn(String username, int menu);


    /**
     * 修改账户密码
     *
     * @param cellphone
     * @param password
     * @return
     */
    boolean updatePasswordByUsername(String cellphone, String password);

    /**
     * 查所有小工具用户信息
     * @return
     */
    List<User> queryAllUsername();

    /**
     * 根据用户名和功能查询是否有该权限
     *
     * @param username
     * @param menu
     * @return
     */
    boolean queryUserAndMenu(String username, String menu);

    /**
     * 根据type查menu
     * @param type
     * @return
     */
    List queryMenuByType(String type);

}
