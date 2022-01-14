package org.github.hoorf.controller;

import org.github.hoorf.logger.annotation.LogRecord;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {

    @GetMapping("/query")
    @LogRecord(before = "开始生成订单",success = "订单号: {searchOrder({#orderNo})} ,订单id {#orderNo} ")
    public String query(String orderNo){
        return orderNo;
    }
}
