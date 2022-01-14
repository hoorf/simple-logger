package org.github.hoorf.logger.parse;

public class SearchNoFunction implements IParseFunction {
    @Override
    public String functionName() {
        return "searchNo";
    }

    @Override
    public String apply(String value) {
        return "查询号为: "+value;
    }
}
