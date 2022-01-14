package org.github.hoorf.controller;

import org.github.hoorf.controller.model.OrderInfo;
import org.github.hoorf.logger.annotation.LogRecord;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {

    /**
     * 基础属性
     * @param orderNo
     * @return
     */
    @GetMapping("/create")
    @LogRecord(before = "开始生成订单", success = "订单号: {#orderNo} 已生成", error = "生成订单失败")
    public String create(String orderNo) {
        return orderNo;
    }

    /**
     * 对象属性
     * @param orderInfo
     * @return
     */
    @PostMapping("/discount")
    @LogRecord(before = "开始计算订单->{#orderInfo.orderNo}的优惠总额",success = "订单id->{#orderInfo.id},优惠总额->{#orderInfo.discount.discountAmount} 元")
    public OrderInfo discount(@RequestBody OrderInfo orderInfo) {
        return orderInfo;
    }

    /**
     * 自定义处理函数
     * @param orderInfo
     * @return
     */
    @PostMapping("/fireSend")
    @LogRecord(success = "订单id->{#orderInfo.id},开始发货,发货信息->{searchSendInfo({#orderInfo.id})}")
    public OrderInfo fireSend(@RequestBody OrderInfo orderInfo){
        return orderInfo;
    }
}
