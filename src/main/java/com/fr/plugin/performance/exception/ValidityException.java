package com.fr.plugin.performance.exception;

/**
 * Created by yuwh on 2019/4/10
 * Description:未通过门槛式校验直接异常
 * @author yuwh
 */
public class ValidityException extends ReadableException {
    public ValidityException(String message) {
        super(message);
    }
}
