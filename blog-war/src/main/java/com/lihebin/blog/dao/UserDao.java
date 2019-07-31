package com.lihebin.blog.dao;

import com.lihebin.blog.bean.User;
import com.lihebin.blog.bean.UserMenu;
import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by lihebin on 2018/12/2.
 */
@Repository
public class UserDao {

    private final static Logger log = LoggerFactory.getLogger(UserDao.class);

    @Resource(name = "blogJdbcTemplate")
    JdbcTemplate blogTemplate;

    /**
     * 根据用户名查用户信息
     *
     * @param username
     * @return
     */
    public User queryUserByUsername(String username) {
        try {
            String sql = "select * from user where username= ? and deleted = 0";
            RowMapper<User> rowMapper = new BeanPropertyRowMapper<>(User.class);
            return blogTemplate.queryForObject(sql, new String[]{username}, rowMapper);
        } catch (Exception e) {
            log.error("queryUserByUsername:{}", username, e);
            return new User();
        }
    }

    /**
     * 根据 name查询
     * @param name
     * @return
     */
    public User getUserNameByName(String name) {
        try {
            String sql = "select * from user where name= ? and deleted = 0";
            RowMapper<User> rowMapper = new BeanPropertyRowMapper<>(User.class);
            return blogTemplate.queryForObject(sql, new String[]{name}, rowMapper);
        } catch (Exception e) {
            return new User();
        }
    }

    /**
     * 根据用户名查权限列表
     *
     * @param username
     * @return
     */
    public List<UserMenu> listUserMenu(String username) {
        try {
            String sql = "select * from user_menu where username= ?";
            return blogTemplate.query(sql, new String[]{username}, new BeanPropertyRowMapper(UserMenu.class));
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    /**
     * 创建账号
     *
     * @param username
     * @param name
     * @param password
     * @param role
     * @return
     */
    public boolean insertUsername(String username, String name, String password, int role) {
        try {
            String sql = "INSERT INTO user(username,password,role,name) VALUES(?,?,?,?)";
            blogTemplate.update(sql, username, password, role, name);
            return true;
        } catch (Exception e) {
            log.error("insertUsername:{},{},{},{}", username, name, password, role, e);
            return false;
        }
    }

    /**
     * 删除账号
     *
     * @param username
     * @return
     */
    public boolean deleteUsername(String username) {
        try {
            String sql = "update user set deleted = 1 where username= ? ";
            blogTemplate.update(sql, username);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 根据用户名和sn查是否有数据
     *
     * @param username
     * @param menu
     * @return
     */
    public boolean queryUserMenuByUserAndSn(String username, int menu) {
        try {
            String sql = "SELECT username,menu_sn FROM user_menu WHERE username=? AND menu_sn=?";
            Map<String, Object> result = blogTemplate.queryForMap(sql, username, menu);
            return !MapUtils.isEmpty(result);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 写入user_menu数据库
     *
     * @param username
     * @param menu
     * @return
     */
    public boolean insertUserMenuByUserAndSn(String username, int menu) {
        try {
            String sql = "INSERT INTO user_menu(id,username,menu_sn) VALUES(uuid(),?,?)";
            blogTemplate.update(sql, username, menu);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 创建账号
     * @param username
     * @param menus
     * @return
     */
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
            blogTemplate.batchUpdate(sql, dataList);
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    /**
     * 删除user_menu数据
     *
     * @param username
     * @param menu
     * @return
     */
    public boolean relieveUserMenuByUserAndSn(String username, int menu) {
        try {
            String sql = "DELETE FROM user_menu WHERE username=?";
            if (menu == -1){
                blogTemplate.update(sql, username);
                return true;
            }
            sql = sql + " AND menu_sn=?";
            blogTemplate.update(sql, username, menu);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 修改账户密码
     *
     * @param cellphone
     * @param password
     * @return
     */
    public boolean updatePasswordByUsername(String cellphone, String password) {
        try {
            String sql = "update user set password=? where username=?";
            blogTemplate.update(sql, password, cellphone);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 查所有用户信息
     * @return
     */
    public List<User> queryAllUsername() {
        try {
            String sql = "select * from user where deleted = 0";
            return blogTemplate.query(sql, new BeanPropertyRowMapper(User.class));
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    /**
     * 根据用户名和功能查询是否有该权限
     *
     * @param username
     * @param menu
     * @return
     */
    public boolean queryUserAndMenu(String username, String menu) {
        try {
            String sql = "select name from user_menu where username = ? and name=? limit 1";
            Map<String, Object> menuParam = blogTemplate.queryForMap(sql, username, menu);
            return !MapUtils.isEmpty(menuParam);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 根据type查menu
     * @param type
     * @return
     */
    public List queryMenuByType(String type) {
        try {
            String sql = "select * from menu where type=? and deleted = 0";
            return blogTemplate.queryForList(sql, type);
        } catch (Exception e){
            return new ArrayList();
        }
    }
}
