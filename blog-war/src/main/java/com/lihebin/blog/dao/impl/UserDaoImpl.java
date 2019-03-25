package com.lihebin.blog.dao.impl;

import com.lihebin.blog.bean.User;
import com.lihebin.blog.bean.UserMenu;
import com.lihebin.blog.dao.UserDao;
import org.apache.commons.collections.MapUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lihebin on 2018/12/2.
 */
@Repository
public class UserDaoImpl implements UserDao{

    @Resource(name = "backendJdbcTemplate")
    JdbcTemplate backendUserTemplate;

    @Override
    public User queryUserByUsername(String username) {
        try {
            String sql = "select * from user where username= ? and deleted = 0";
            return backendUserTemplate.queryForObject(sql, new String[]{username}, User.class);
        } catch (Exception e) {
            return new User();
        }
    }

    @Override
    public User getUserNameByName(String name) {
        try {
            String sql = "select * from user where name= ? and deleted = 0";
            return backendUserTemplate.queryForObject(sql, new String[]{name}, User.class);
        } catch (Exception e) {
            return new User();
        }
    }

    @Override
    public List<UserMenu> listUserMenu(String username) {
        try {
            String sql = "select * from user_menu where username= ?";
            return backendUserTemplate.query(sql, new String[]{username}, new BeanPropertyRowMapper(UserMenu.class));
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    @Override
    public boolean insertUsername(String username, String name, String password, int role) {
        try {
            String sql = "INSERT INTO user(username,password,role,status,name) VALUES(?,?,?,1,?)";
            backendUserTemplate.update(sql, username, password, role, name);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean deleteUsername(String username) {
        try {
            String sql = "update user set status = 0 where username= ? ";
            backendUserTemplate.update(sql, username);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean queryUserMenuByUserAndSn(String username, int menu) {
        try {
            String sql = "SELECT username,menu_sn FROM user_menu WHERE username=? AND menu_sn=?";
            Map<String, Object> result = backendUserTemplate.queryForMap(sql, username, menu);
            return !MapUtils.isEmpty(result);
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean insertUserMenuByUserAndSn(String username, int menu) {
        try {
            String sql = "INSERT INTO user_menu(id,username,menu_sn) VALUES(uuid(),?,?)";
            backendUserTemplate.update(sql, username, menu);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean insertUserMenus(String username, int[] menus) {
        String sql = "INSERT INTO user_menu(id,username,menu_sn) VALUES(uuid(),?,?)";
        List<Object[]> dataList = new ArrayList<>();
        for (int menu : menus) {
            Object[] data = new Object[2];
            data[0] = username;
            data[1] = menu;
            dataList.add(data);
        }
        try {
            backendUserTemplate.batchUpdate(sql, dataList);
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    @Override
    public boolean relieveUserMenuByUserAndSn(String username, int menu) {
        try {
            String sql = "DELETE FROM user_menu WHERE username=?";
            if (menu == -1){
                backendUserTemplate.update(sql, username);
                return true;
            }
            sql = sql + " AND menu_sn=?";
            backendUserTemplate.update(sql, username, menu);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean updatePasswordByUsername(String cellphone, String password) {
        try {
            String sql = "update user set password=? where username=?";
            backendUserTemplate.update(sql, password, cellphone);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public List<User> queryAllUsername() {
        try {
            String sql = "select * from user where deleted = 0";
            return backendUserTemplate.query(sql, new BeanPropertyRowMapper(User.class));
        } catch (Exception e) {
           return new ArrayList<>();
        }
    }

    @Override
    public boolean queryUserAndMenu(String username, String menu) {
        try {
            String sql = "select name from user_menu where username = ? and name=? limit 1";
            Map<String, Object> menuParam = backendUserTemplate.queryForMap(sql, username, menu);
            return !MapUtils.isEmpty(menuParam);
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public List queryMenuByType(String type) {
        try {
            String sql = "select * from menu where type=? and deleted = 0";
            return backendUserTemplate.queryForList(sql, type);
        } catch (Exception e){
            return new ArrayList();
        }
    }
}
