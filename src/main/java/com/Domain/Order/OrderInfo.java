package com.Domain.Order;

import com.Dao.Entity.OrderStatus;
import com.Domain.GoodsInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderInfo {
    private Integer oid;
    private Integer amount;
    private Float totalPrice;
    private OrderStatus orderStatus;
    private Date createTime;
    private GoodsInfo goods;
    private String owner;
    private String buyer;

    public final static OrderInfo ERROR = new OrderInfo(-1, null, null, null, null, null, null, null);
    public final static OrderInfo SUCCEED = new OrderInfo(0, null, null, null, null, null, null, null);

}
