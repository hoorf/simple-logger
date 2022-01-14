package org.github.hoorf.logger.antlr;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.AbstractParseTreeVisitor;
import org.antlr.v4.runtime.tree.ParseTree;
import org.junit.jupiter.api.Test;

public class LogTemplateLexerTest {


    /**
     * Method: getTokenNames()
     */
    @Test
    public void testGetTokenNames() {
        LogTemplateLexer lexer = new LogTemplateLexer(CharStreams.fromString("{searchOrderNo({#orderId})} 手机号 {#user} {uxx({#US})} 张三"));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        LogTemplateParser parser = new LogTemplateParser(tokens);
        AbstractParseTreeVisitor<String> visitor = new LogTemplateBaseVisitor() {
            @Override
            public Object visitParamName(LogTemplateParser.ParamNameContext ctx) {
                System.err.println(ctx.ID());
                return ctx.getText();
            }

            @Override
            public Object visitContent(LogTemplateParser.ContentContext ctx) {
                StringBuilder stringBuilder = new StringBuilder();
                for (LogTemplateParser.TemplateContext templateContext : ctx.template()) {
                    for (ParseTree child : templateContext.children) {
                        if (child instanceof LogTemplateParser.MethodContext) {
                            stringBuilder.append(visitMethod((LogTemplateParser.MethodContext) child).toString());
                        } else if (child instanceof LogTemplateParser.ParamContext) {
                            stringBuilder.append(visitParam((LogTemplateParser.ParamContext) child).toString());
                        } else {
                            stringBuilder.append(child.getText());
                        }
                    }
                }
                return stringBuilder.toString() ;
            }


            @Override
            public Object visitMethod(LogTemplateParser.MethodContext ctx) {
                String methodName = ctx.methodName().getText();
                String param = visitParam(ctx.param()).toString();
                return methodName+"("+param+")";
            }

            @Override
            public Object visitParam(LogTemplateParser.ParamContext ctx) {
                String text = ctx.paramName().getText();
                return ctx.getText()+"123p";
            }

        };
        LogTemplateParser.ContentContext content = parser.content();
        System.out.println(visitor.visit(content));
    }

    private void visit(LogTemplateParser parser) {
        LogTemplateParser.ContentContext content = parser.content();
        for (LogTemplateParser.TemplateContext templateContext : content.template()) {
            if (null != templateContext.method()) {
                System.out.println("---------------");
                LogTemplateParser.ParamContext param = templateContext.method().param();
                if (param != null) {
                    System.out.println(param.getText());
                }
                LogTemplateParser.MethodNameContext methodNameContext = templateContext.method().methodName();
                if (methodNameContext != null) {
                    System.out.println(methodNameContext.getText());
                }
                System.out.println("+++++++++++++++++");
            }
            if (null != templateContext.param()) {
                String text = templateContext.param().getText();
                System.out.println(text);
            }
        }
    }


} 
