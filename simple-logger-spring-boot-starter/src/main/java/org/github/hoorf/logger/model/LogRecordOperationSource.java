package org.github.hoorf.logger.model;

import org.github.hoorf.logger.annotation.LogRecord;
import org.springframework.util.StringUtils;

public class LogRecordOperationSource {

    private LogRecord annotation;

    public LogRecordOperationSource(LogRecord annotation) {
        this.annotation = annotation;
    }

    public boolean hasBefore() {
        return StringUtils.hasText(annotation.before());
    }
    public boolean hasSuccess() {
        return StringUtils.hasText(annotation.success());
    }
    public boolean hasError() {
        return StringUtils.hasText(annotation.error());
    }

    public String getBefore() {
        return annotation.before();
    }

    public String getAfterSuccess() {
        return annotation.success();
    }

    public String getAfterError() {
        return annotation.error();
    }


}
