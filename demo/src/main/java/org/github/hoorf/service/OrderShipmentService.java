package org.github.hoorf.service;

import org.github.hoorf.logger.annotation.LogRecord;
import org.github.hoorf.logger.model.LogRecordContext;
import org.github.hoorf.model.OrderInfo;
import org.springframework.stereotype.Service;

@Service
public class OrderShipmentService {

    @LogRecord(success = "2.订单id->{#orderInfo.id},订单备注->{#orderInfo.desc} 操作人->{#operator}")
    public OrderInfo shipment(OrderInfo orderInfo) {
        LogRecordContext.put("operator","李四");
        orderInfo.setDesc(orderInfo.getDesc() + "==>已处理");
        return orderInfo;
    }
}
