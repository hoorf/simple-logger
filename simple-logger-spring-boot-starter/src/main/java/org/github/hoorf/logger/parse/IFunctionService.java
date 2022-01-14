package org.github.hoorf.logger.parse;

public interface IFunctionService {
    String apply(String functionName, String value);
    boolean beforeFunction(String functionName);
}
