package org.github.hoorf.logger.parse;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import lombok.extern.slf4j.Slf4j;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.github.hoorf.logger.antlr.LogTemplateBaseVisitor;
import org.github.hoorf.logger.antlr.LogTemplateLexer;
import org.github.hoorf.logger.antlr.LogTemplateParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class ParseService {

    Cache<String, Object> cache = CacheBuilder.newBuilder().expireAfterWrite(30, TimeUnit.SECONDS).build();

    @Autowired
    private ParseFunctionFactory functionFactory;

    ExpressionParser spelExpressionParser = new SpelExpressionParser();

    private LogRecordExpressionEvaluator logRecordExpressionEvaluator = new LogRecordExpressionEvaluator();

    public String getExpression(String template, EvaluationContext evaluationContext) {
        LogTemplateLexer lexer = new LogTemplateLexer(CharStreams.fromString(template));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        LogTemplateParser parser = new LogTemplateParser(tokens);
        boolean buildParseTree = parser.getBuildParseTree();
        log.info("buildParseTree :{}",buildParseTree);
        log.info("isTrace :{}",parser.isTrace());
        log.info("ParseInfo :{}",parser.getParseInfo());
        LogTemplateBaseVisitor<String> visitor = new LogTemplateBaseVisitor<String>() {
            @Override
            public String visitParamName(LogTemplateParser.ParamNameContext ctx) {
                System.err.println(ctx.ID());
                return ctx.getText();
            }

            @Override
            public String visitContent(LogTemplateParser.ContentContext ctx) {
                StringBuilder stringBuilder = new StringBuilder();
                for (LogTemplateParser.TemplateContext templateContext : ctx.template()) {
                    for (ParseTree child : templateContext.children) {
                        if (child instanceof LogTemplateParser.MethodContext) {
                            stringBuilder.append(visitMethod((LogTemplateParser.MethodContext) child));
                        } else if (child instanceof LogTemplateParser.ParamContext) {
                            stringBuilder.append(visitParam((LogTemplateParser.ParamContext) child));
                        } else {
                            stringBuilder.append(child.getText());
                        }
                    }
                }
                return stringBuilder.toString();
            }

            @Override
            public String visitMethod(LogTemplateParser.MethodContext ctx) {
                String param = visitParam(ctx.param());
                String methodName = ctx.methodName().getText();
                IParseFunction function = functionFactory.getFunction(methodName);
                if (function != null) {
                    String result = function.apply(param);
                    return result;
                } else {
                    return param;
                }
            }

            @Override
            public String visitParam(LogTemplateParser.ParamContext ctx) {
                String text = ctx.paramName().getText();
                String result = spelExpressionParser.parseExpression(text).getValue(evaluationContext, String.class);
                return result;
            }
        };
        LogTemplateParser.ContentContext content = parser.content();
        return visitor.visit(content);
    }
}
