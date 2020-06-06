package com.Controller;

import com.Domain.GoodsInfo;
import com.Domain.Order.OrdercreateRequest;
import com.Domain.Order.OrderInfo;
import com.Service.GoodsService;
import com.Service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "http://localhost:8080", maxAge = 3600)
@RestController
public class OrderController {
    private OrderService orderService;
    private GoodsService goodsService;

    @Autowired
    public OrderController(OrderService orderService, GoodsService goodsService)
    {
        this.orderService = orderService;
        this.goodsService = goodsService;
    }

    @PostMapping("data/new_order")
    public OrderInfo createNewOrder(@RequestBody OrdercreateRequest orderCreateRequest)
    {
        try
        {
            OrderInfo orderInfo = orderService.createNewOrder(orderCreateRequest);
            return orderInfo;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return OrderInfo.ERROR;
        }
    }

    private String getUsernameFromSession(HttpSession httpSession)
    {
        String username = (String) httpSession.getAttribute("id");
        return username;
    }

    @GetMapping("data/buyer_order")
    public OrderInfo[] getOrdersByBuyer(HttpSession httpSession)
    {
        String buyer = getUsernameFromSession(httpSession);
        if(buyer == null)
            return null;
        return orderService.getOrdersByBuyer(buyer);
    }

    @GetMapping("data/owner_order")
    public OrderInfo[] getOrdersByOwner(HttpSession httpSession)
    {
        String owner = getUsernameFromSession(httpSession);
        if(owner == null)
            return null;
        List<OrderInfo> list = new ArrayList<>();
        GoodsInfo[] goodsInfoArr = goodsService.getGoodsByOwner(owner);
        for(int i = 0; i < goodsInfoArr.length; i++)
        {
            OrderInfo[] orderInfoArr = orderService.getOrdersByGoods(goodsInfoArr[i].getGid());
            for(int j = 0; j < orderInfoArr.length; j++)
            {
                list.add(orderInfoArr[j]);
            }
        }
        OrderInfo[] res = list.toArray(new OrderInfo[1]);
        return res;
    }

    @GetMapping("data/order_display")
    public OrderInfo getOrderInfoByOid(@RequestParam Integer oid, HttpSession httpSession)
    {
        return orderService.getOrderByOid(oid, getUsernameFromSession(httpSession));
    }

    @PostMapping("data/pay_order")
    public OrderInfo payOrder(@RequestBody OrderInfo orderInfo, HttpSession httpSession)
    {
        String buyer = getUsernameFromSession(httpSession);
        if(buyer == null)
            return OrderInfo.ERROR;

        return orderService.buyerPay(orderInfo, buyer);
    }

    @PostMapping("data/send_goods")
    public OrderInfo sendGoods(@RequestBody OrderInfo orderInfo, HttpSession httpSession)
    {
        String owner = getUsernameFromSession(httpSession);
        if(owner == null)
            return OrderInfo.ERROR;

        return orderService.ownerSend(orderInfo, owner);
    }

    @PostMapping("data/receive_goods")
    public OrderInfo receiveGoods(@RequestBody OrderInfo orderInfo, HttpSession httpSession)
    {
        String buyer = getUsernameFromSession(httpSession);
        if(buyer == null)
            return OrderInfo.ERROR;

        return orderService.buyerReceive(orderInfo, buyer);
    }



}
