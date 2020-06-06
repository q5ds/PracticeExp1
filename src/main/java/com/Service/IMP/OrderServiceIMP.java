package com.Service.IMP;

import com.Dao.Entity.Goods;
import com.Dao.Entity.Order;
import com.Dao.Entity.OrderStatus;
import com.Dao.Entity.Users;
import com.Dao.Repository.GoodsRepository;
import com.Dao.Repository.OrderRepository;
import com.Dao.Repository.UsersRepository;
import com.Domain.GoodsInfo;
import com.Domain.Order.OrdercreateRequest;
import com.Domain.Order.OrderInfo;
import com.Service.OrderService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;


@Service("orderService")
public class OrderServiceIMP implements OrderService
{
    private OrderRepository orderRepository;
    private GoodsRepository goodsRepository;
    private UsersRepository userRepository;


    @Autowired
    public OrderServiceIMP(OrderRepository orderRepository, GoodsRepository goodsRepository,
                            UsersRepository userRepository)
    {
        this.orderRepository = orderRepository;
        this.goodsRepository = goodsRepository;
        this.userRepository = userRepository;
    }


    @Override
    public OrderInfo createNewOrder(OrdercreateRequest orderCreateRequest) throws Exception
    {
        Goods goods;
        Users buyer;
        try
        {
            goods = goodsRepository.findByGid(orderCreateRequest.getGid());
            buyer = userRepository.findUserByUsername(orderCreateRequest.getBuyer());
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return OrderInfo.ERROR;
        }

        Order order = new Order();

        System.out.println("订单需求数量： " + orderCreateRequest.getAmount() + "商品剩余数量：" + goods.getAmount());

        if(orderCreateRequest.getAmount() > goods.getAmount())
            throw new Exception("商品数量不足");

        BeanUtils.copyProperties(orderCreateRequest, order); //把两个类相同属性的值复制，左边给右边

        order.setBuyer(buyer);
        buyer.getOrders().add(order);

        order.setGoods(goods);
        goods.getOrders().add(order);

        goods.setAmount(goods.getAmount() - order.getAmount());

        order.setTotalPrice(order.getAmount() * goods.getPrice());
        order.setOrderStatus(OrderStatus.PAYING);

        orderRepository.save(order);
        userRepository.save(buyer);
        goodsRepository.save(goods);


        return OrderInfo.SUCCEED;
    }


    @Override
    public OrderInfo buyerPay(OrderInfo orderInfo, String buyer)
    {
        if(!orderInfo.getBuyer().equals(buyer))
            return OrderInfo.ERROR;
        try
        {
            Order order = orderRepository.findByOid(orderInfo.getOid());
            order.setOrderStatus(OrderStatus.SENDING);
            orderRepository.save(order);
            return OrderInfo.SUCCEED;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return OrderInfo.ERROR;
        }
    }

    @Override
    public OrderInfo ownerSend(OrderInfo orderInfo, String owner)
    {
        if(!orderInfo.getOwner().equals(owner))
            return OrderInfo.ERROR;
        try
        {
            Order order = orderRepository.findByOid(orderInfo.getOid());
            order.setOrderStatus(OrderStatus.RECEIVING);
            orderRepository.save(order);
            return OrderInfo.SUCCEED;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return OrderInfo.ERROR;
        }
    }

    @Override
    public OrderInfo buyerReceive(OrderInfo orderInfo, String buyer)
    {
        if(!orderInfo.getBuyer().equals(buyer))
            return OrderInfo.ERROR;
        try
        {
            Order order = orderRepository.findByOid(orderInfo.getOid());
            order.setOrderStatus(OrderStatus.COMPLETED);
            orderRepository.save(order);
            return OrderInfo.SUCCEED;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return OrderInfo.ERROR;
        }
    }


    private OrderInfo[] getOrderInfoArr(List<Order> orders)
    {
        OrderInfo[] orderInfoArr = new OrderInfo[orders.size()];
        for(int i = 0; i < orderInfoArr.length; i++)
        {
            orderInfoArr[i] = getOrderInfoFromOrder(orders.get(i));
        }
        return orderInfoArr;
    }


    @Override
    public OrderInfo[] getOrdersByBuyer(String username)
    {
        try
        {
            Users buyer = userRepository.findUserByUsername(username);
            List<Order> orders = orderRepository.findAllByBuyer(buyer);
            return getOrderInfoArr(orders);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public OrderInfo[] getOrdersByGoods(Integer gid)
    {
        try
        {
            Goods goods = goodsRepository.findByGid(gid);
            List<Order> orders = orderRepository.findAllByGoods(goods);
            return getOrderInfoArr(orders);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    private OrderInfo getOrderInfoFromOrder(Order order)
    {
        OrderInfo orderInfo = new OrderInfo();
        BeanUtils.copyProperties(order, orderInfo);
        GoodsInfo goodsInfo = new GoodsInfo();
        goodsInfo.setTitle(order.getGoods().getTitle());
        goodsInfo.setGid(order.getGoods().getGid());
        orderInfo.setGoods(goodsInfo);
        return orderInfo;
    }

    private OrderInfo getOrderInfoFromOrderInDetail(Order order)
    {
        OrderInfo orderInfo = getOrderInfoFromOrder(order);
        orderInfo.setBuyer(order.getBuyer().getUsername());
        orderInfo.setOwner(order.getGoods().getOwner().getUsername());
        return orderInfo;
    }

    @Override
    public OrderInfo getOrderByOid(Integer oid, String username)
    {
        try
        {
            Users user = userRepository.findUserByUsername(username);
            if(user == null)
                return null;
            Order order = orderRepository.findByOid(oid);
            if(order.getBuyer().getUid() != user.getUid() && order.getGoods().getOwner().getUid() != user.getUid())
                return null;

            return getOrderInfoFromOrderInDetail(order);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }


}