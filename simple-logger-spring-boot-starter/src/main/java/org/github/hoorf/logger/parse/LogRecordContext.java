package org.github.hoorf.logger.parse;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class LogRecordContext {
    private static final InheritableThreadLocal<Stack<Map<String, Object>>> variableMapStack = new InheritableThreadLocal<>();

    public static void putEmptySpan() {
        variableMapStack.get().push(new HashMap<>());
    }

    public static Map<String, Object> getVariables() {
        return variableMapStack.get().peek();
    }

    public static void putVariable(String param, Object value) {
        getVariables().put(param, value);
    }

    public static void clear() {
        variableMapStack.get().pop();
    }

}
