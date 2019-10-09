package com.fr.plugin.performance.exception;

import com.fr.plugin.performance.ui.Readable;

/**
 * @author yuwh
 * Created by yuwh on 2019/4/15
 * Description: 一般运行时异常才需要可读，这里目的是这个
 */
public class ReadableException extends RuntimeException implements Readable {
    public ReadableException(String message) {
        super(message);
    }

    @Override
    public String getReadableMessage() {
        /* TODO 这里还是没有把信息输出到response*/
        return getMessage();
    }
}
