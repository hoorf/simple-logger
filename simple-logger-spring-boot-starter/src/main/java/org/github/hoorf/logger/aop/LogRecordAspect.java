package org.github.hoorf.logger.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.github.hoorf.logger.annotation.LogRecord;
import org.github.hoorf.logger.model.LogRecordOperationSource;
import org.github.hoorf.logger.model.MethodExecuteResult;
import org.github.hoorf.logger.parse.LogRecordExpressionEvaluator;
import org.github.hoorf.logger.parse.ParseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.expression.AnnotatedElementKey;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Component
@Slf4j
public class LogRecordAspect {

    private ParameterNameDiscoverer parameterNameDiscoverer = new LocalVariableTableParameterNameDiscoverer();

    @Autowired
    private ParseService parseService;

    @Pointcut("@annotation(org.github.hoorf.logger.annotation.LogRecord)")
    public void logPointCut() {

    }

    @Around("logPointCut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {

        Object[] args = joinPoint.getArgs();
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        Class<?> clazz = joinPoint.getTarget().getClass();

        EvaluationContext evaluationContext = new StandardEvaluationContext();
        String[] parameterNames = parameterNameDiscoverer.getParameterNames(method);
        for (int i = 0; i < parameterNames.length; i++) {
            String paramName = parameterNames[i];
            evaluationContext.setVariable(paramName, args[i]);
        }

        LogRecord annotation = method.getAnnotation(LogRecord.class);
        LogRecordOperationSource logRecordOperationSource = new LogRecordOperationSource(annotation);

        MethodExecuteResult methodExecuteResult = new MethodExecuteResult(true, null, null);
        if (logRecordOperationSource.hasBefore()) {
            log(logRecordOperationSource.getBefore(), evaluationContext, joinPoint);
        }
        try {
            Object result = joinPoint.proceed();
            if (logRecordOperationSource.hasSuccess()) {
                log(logRecordOperationSource.getAfterSuccess(), evaluationContext, joinPoint);
            }
            return result;
        } catch (Exception e) {
            methodExecuteResult = new MethodExecuteResult(false, e, e.getMessage());
            if (logRecordOperationSource.hasError()) {
                log(logRecordOperationSource.getAfterError(), evaluationContext, joinPoint);
            }
        }

        if (null != methodExecuteResult.getThrowable()) {
            throw methodExecuteResult.getThrowable();
        }

        return null;
    }

    private void log(String template, EvaluationContext evaluationContext, ProceedingJoinPoint joinPoint) {
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        Class<?> clazz = joinPoint.getTarget().getClass();
        String result = parseService.getExpression(template,evaluationContext);
        log.info("aspect : {}", result);
    }
}
