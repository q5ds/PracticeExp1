package com.Dao.Repository;

import com.Dao.Entity.Goods;
import com.Dao.Entity.Order;
import com.Dao.Entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

    List<Order> findAllByBuyer(Users buyer);  //查询用户所有的订单
    Order findByOid(Integer oid);  //根据所给订单号查询订单
    List<Order> findAllByGoods(Goods goods);    //根据商品区查询
}
