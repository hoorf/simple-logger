package org.github.hoorf.logger.model;

import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.Stack;

@Slf4j
public class LogRecordContext {

    private static ThreadLocal<Stack<Map<String, Object>>> contextLocal = new InheritableThreadLocal() {
        @Override
        protected Stack<Map<String, Object>> initialValue() {
            return new Stack<>();
        }
    };

    public static Map<String, Object> get() {
        return contextLocal.get().peek();
    }

    public static void put(String key, Object obj) {
        contextLocal.get().peek().put(key, obj);
    }

    public static void open() {
        log.info("open");
        contextLocal.get().push(Maps.newHashMap());
    }

    public static void close() {
        contextLocal.get().pop();
    }
}
