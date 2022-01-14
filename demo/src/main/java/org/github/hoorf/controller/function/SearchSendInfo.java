package org.github.hoorf.controller.function;

import org.github.hoorf.logger.parse.IParseFunction;
import org.springframework.stereotype.Component;

@Component
public class SearchSendInfo implements IParseFunction {
    public String functionName() {
        return "searchSendInfo";
    }

    public String apply(String orderId) {
        String op = "张三";
        String time = "20211202";

        return "发货人: " + op + " 发货时间: " + time;
    }
}
