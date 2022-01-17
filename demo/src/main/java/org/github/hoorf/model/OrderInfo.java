package org.github.hoorf.model;

import lombok.Data;

@Data
public class OrderInfo {

    private String id;

    private String orderNo;

    private String desc;

    private OrderDiscount discount;

    @Data
    class OrderDiscount {
        private Integer discountAmount;
        private Integer num;
    }
}
