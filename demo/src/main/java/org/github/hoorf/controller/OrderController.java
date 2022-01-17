package org.github.hoorf.controller;

import lombok.extern.slf4j.Slf4j;
import org.github.hoorf.logger.model.LogRecordContext;
import org.github.hoorf.model.OrderInfo;
import org.github.hoorf.logger.annotation.LogRecord;
import org.github.hoorf.service.OrderShipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class OrderController {

    @Autowired
    private OrderShipmentService orderShipmentService;

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


    /**
     * 重叠
     * @param orderInfo
     * @return
     */
    @PostMapping("/fireMultiOne")
    @LogRecord(success = "1.订单id->{#orderInfo.id},订单备注->{#orderInfo.desc},操作人->{#operator}")
    public OrderInfo fireMultiOne(@RequestBody OrderInfo orderInfo){
        LogRecordContext.put("operator","张三");
        orderInfo = orderShipmentService.shipment(orderInfo);
        return orderInfo;
    }

}
