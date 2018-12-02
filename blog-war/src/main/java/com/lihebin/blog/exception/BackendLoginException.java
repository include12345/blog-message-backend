package com.lihebin.blog.exception;

/**
 * Created by lihebin on 2018/6/25.
 */
public class BackendLoginException extends Exception {

    public BackendLoginException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public BackendLoginException(String message) {
        super(message);
    }
}
