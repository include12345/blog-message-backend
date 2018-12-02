package com.lihebin.blog.exception;

/**
 * Created by lihebin on 2018/6/25.
 */
public class BackendRepeatException extends Exception {

    public BackendRepeatException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public BackendRepeatException(String message) {
        super(message);
    }
}
