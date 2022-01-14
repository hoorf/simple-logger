package org.github.hoorf.logger.parse;

public class DefaultParseFunction implements IParseFunction {
    @Override
    public String functionName() {
        return "";
    }

    @Override
    public String apply(String value) {
        return value;
    }
}
