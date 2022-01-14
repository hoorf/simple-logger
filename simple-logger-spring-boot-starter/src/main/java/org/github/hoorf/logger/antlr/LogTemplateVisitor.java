// Generated from C:/develop/idea_workspace/simple-logger/simple-logger-spring-boot-starter/src/main/java/org/github/hoorf/logger/antlr\LogTemplate.g4 by ANTLR 4.9.2
package org.github.hoorf.logger.antlr;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link LogTemplateParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface LogTemplateVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link LogTemplateParser#content}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitContent(LogTemplateParser.ContentContext ctx);
	/**
	 * Visit a parse tree produced by {@link LogTemplateParser#template}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTemplate(LogTemplateParser.TemplateContext ctx);
	/**
	 * Visit a parse tree produced by {@link LogTemplateParser#method}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMethod(LogTemplateParser.MethodContext ctx);
	/**
	 * Visit a parse tree produced by {@link LogTemplateParser#methodName}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMethodName(LogTemplateParser.MethodNameContext ctx);
	/**
	 * Visit a parse tree produced by {@link LogTemplateParser#param}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParam(LogTemplateParser.ParamContext ctx);
	/**
	 * Visit a parse tree produced by {@link LogTemplateParser#paramName}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParamName(LogTemplateParser.ParamNameContext ctx);
}