package org.github.hoorf.logger.parse;

public interface IParseFunction {
    default boolean executeBefore(){
        return false;
    }

    String functionName();

    String apply(String value);
}
