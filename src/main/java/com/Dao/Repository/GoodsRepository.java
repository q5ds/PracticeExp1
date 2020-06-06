package com.Dao.Repository;

import com.Dao.Entity.Goods;
import com.Dao.Entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface  GoodsRepository extends JpaRepository<Goods,Integer>{

    List<Goods> findAllByTitleIsLike(String str); //根据用户输入结果查询产品
    List<Goods> findAllByOwner(Users user);  //查询某个用户发布的产品
    Goods findByGid(Integer gid);


}
