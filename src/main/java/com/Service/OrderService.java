package com.Service;

import com.Domain.Order.OrderInfo;
import com.Domain.Order.OrdercreateRequest;


public interface OrderService {
    OrderInfo createNewOrder(OrdercreateRequest ordercreateRequest)throws Exception;
    OrderInfo buyerPay(OrderInfo orderInfo, String buyer);

    OrderInfo ownerSend(OrderInfo orderInfo, String owner);

    OrderInfo buyerReceive(OrderInfo orderInfo, String buyer);

    OrderInfo[] getOrdersByBuyer(String username);

    OrderInfo[] getOrdersByGoods(Integer gid);

    OrderInfo getOrderByOid(Integer oid, String username);


}
