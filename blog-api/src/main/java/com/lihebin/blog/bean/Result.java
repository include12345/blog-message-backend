package com.lihebin.blog.bean;

/**
 * Created by lihebin on 2018/12/2.
 */
public class Result {
    public static final String CODE = "code";
    public static final String MESSAGE = "message";

    public static final int SUCCESS_CODE = 200;
    public static final int FAIL_CODE = 400;
    public static final int ERROR_CODE = 500;


    private Integer code;

    private String message;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
