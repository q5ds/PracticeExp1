package com.Service;

import com.Domain.GoodsInfo;


public interface GoodsService {
    GoodsInfo[] getAllGoods();
    GoodsInfo[] searchGoods(String description);

    GoodsInfo[] getGoodsByOwner(String ownerName);

    GoodsInfo getGoodsByGsid(int gid);

    GoodsInfo addNewGoods(GoodsInfo goodsInfo, String username);

    GoodsInfo editGoods(GoodsInfo goodsInfo);
}
