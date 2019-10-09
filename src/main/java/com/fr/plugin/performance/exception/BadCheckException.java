package com.fr.plugin.performance.exception;

/**
 *  @author yuwh
 *  @version 1.0.0
 *  time:2019/04/10
 */
public class BadCheckException extends ReadableException {
    static final long serialVersionUID = -7034897190745766989L;
    public BadCheckException(String message) {
        super(message);
    }
}
