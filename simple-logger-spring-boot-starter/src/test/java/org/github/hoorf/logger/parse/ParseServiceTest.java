package org.github.hoorf.logger.parse;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.springframework.context.expression.AnnotatedElementKey;

public class ParseServiceTest {

    @Test
    public void testVisit() throws Exception {

        ParseFunctionFactory functionFactory = new ParseFunctionFactory(Lists.newArrayList(new SearchNoFunction()));

        AnnotatedElementKey annotatedElementKey = new AnnotatedElementKey(getClass().getMethod("testVisit"), getClass());
    }

    class Order{
        private String orderNo;
        private String price;
    }


} 
