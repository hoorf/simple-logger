package org.github.hoorf.logger.parse;

import org.springframework.context.expression.AnnotatedElementKey;
import org.springframework.context.expression.CachedExpressionEvaluator;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class LogRecordExpressionEvaluator extends CachedExpressionEvaluator {
    private Map<ExpressionKey, Expression> cache = new ConcurrentHashMap<>(64);
    private Map<AnnotatedElementKey, Method> targetMethodCache = new ConcurrentHashMap<>(64);

    public String parseExpression(String conditionExpression, AnnotatedElementKey methodKey, EvaluationContext evaluationContext) {
        return getExpression(cache, methodKey, conditionExpression).getValue(evaluationContext, String.class);
    }
}
