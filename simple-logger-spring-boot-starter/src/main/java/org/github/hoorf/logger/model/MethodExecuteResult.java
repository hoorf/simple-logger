package org.github.hoorf.logger.model;

import lombok.Data;

@Data
public class MethodExecuteResult {
    private boolean success;
    private Throwable throwable;
    private String msg;

    public MethodExecuteResult(boolean success, Throwable throwable, String msg) {
        this.success = success;
        this.throwable = throwable;
        this.msg = msg;
    }
}
