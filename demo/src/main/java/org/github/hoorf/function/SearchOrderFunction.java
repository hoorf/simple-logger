package org.github.hoorf.function;

import org.github.hoorf.logger.parse.IParseFunction;
import org.springframework.stereotype.Component;

@Component
public class SearchOrderFunction implements IParseFunction {

    public String functionName() {
        return "searchOrder";
    }

    public String apply(String value) {
        return "查询订单" + value;
    }
}
