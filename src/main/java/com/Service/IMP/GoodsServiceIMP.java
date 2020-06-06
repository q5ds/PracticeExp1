package com.Service.IMP;

import com.Dao.Entity.Goods;
import com.Dao.Entity.Users;
import com.Dao.Repository.GoodsRepository;
import com.Dao.Repository.UsersRepository;
import com.Domain.GoodsInfo;
import com.Service.GoodsService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;


@Service("goodsService")
public class GoodsServiceIMP implements GoodsService{
    private GoodsRepository goodsRepository;
    private UsersRepository userRepository;

    @Autowired
    public GoodsServiceIMP(GoodsRepository goodsRepository, UsersRepository usersRepository)
    {
        this.goodsRepository = goodsRepository;
        this.userRepository = usersRepository;
    }

    @Override
    public GoodsInfo[] getAllGoods()
    {
        try
        {
            List<Goods> goodsList = goodsRepository.findAll();
            return getGoodsInfoArr(goodsList);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public GoodsInfo[] searchGoods(String description)
    {
        try
        {
            List<Goods> goodsList = goodsRepository.findAllByTitleIsLike(description);
            return getGoodsInfoArr(goodsList);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public GoodsInfo[] getGoodsByOwner(String ownerName)
    {
        try
        {
            Users owner = userRepository.findUserByUsername(ownerName);
            List<Goods> goodsList = goodsRepository.findAllByOwner(owner);
            return getGoodsInfoArr(goodsList);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public GoodsInfo getGoodsByGsid(int gid)
    {
        try
        {
            Goods goods = goodsRepository.findByGid(gid);
            GoodsInfo goodsInfo = new GoodsInfo();
            BeanUtils.copyProperties(goods, goodsInfo);
            goodsInfo.setOwner(goods.getOwner().getUsername());
            return goodsInfo;
        }
        catch (Exception e)
        {
            return null;
        }
    }


    @Override
    public GoodsInfo addNewGoods(GoodsInfo goodsInfo, String username)
    {
        try
        {
            Users user = userRepository.findUserByUsername(username);
            Goods goods = new Goods();
            BeanUtils.copyProperties(goodsInfo, goods);
            goods.setOwner(user);
            user.getGoodsSet().add(goods);
            goodsRepository.save(goods);
            return GoodsInfo.Success_info;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return GoodsInfo.Fail_info;
        }
    }

    @Override
    public GoodsInfo editGoods(GoodsInfo goodsInfo)
    {
        try
        {
            Goods goods = goodsRepository.findByGid(goodsInfo.getGid());
            BeanUtils.copyProperties(goodsInfo, goods);
            goodsRepository.save(goods);
            return GoodsInfo.Success_info;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return GoodsInfo.Fail_info;
        }
    }

    private GoodsInfo[] getGoodsInfoArr(List<Goods> goodsList)
    {
        GoodsInfo[] goodsInfoArr = new GoodsInfo[goodsList.size()];
        for(int i = 0; i < goodsList.size(); i++)
        {
            goodsInfoArr[i] = new GoodsInfo();
            BeanUtils.copyProperties(goodsList.get(i), goodsInfoArr[i]);
            goodsInfoArr[i].setOwner(goodsList.get(i).getOwner().getUsername());
            goodsInfoArr[i].setDetail(null);
        }
        return goodsInfoArr;
    }


}
