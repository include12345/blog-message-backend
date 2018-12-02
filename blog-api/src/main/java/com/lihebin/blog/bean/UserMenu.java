package com.lihebin.blog.bean;

/**
 * Created by lihebin on 2018/12/2.
 */
public class UserMenu {

    public static final String MENU_SNS = "menuSns";


    private String username;
    private String menuSn;
    private String name;
    private String subject;
    private String type;


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMenuSn() {
        return menuSn;
    }

    public void setMenuSn(String menuSn) {
        this.menuSn = menuSn;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
