package com.Controller;

import com.Domain.GoodsInfo;
import com.Service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpSession;

@CrossOrigin(origins = "http://localhost:8080", maxAge = 3600)
@RestController
public class GoodsController {
    private GoodsService goodsService;

    //可以对类成员变量、方法及构造函数进行标注，完成自动装配的工作
    @Autowired
    public GoodsController(GoodsService goodsService)
    {
        this.goodsService = goodsService;
    }

    //处理请求方法的get类型
    @GetMapping("data/all_goods")
    public GoodsInfo[] getAllGoods()
    {
        return goodsService.getAllGoods();
    }

    @GetMapping("data/search_goods")
    public GoodsInfo[] searchGoods(String keywords)
    {
        return goodsService.searchGoods(keywords);
    }

    @GetMapping("data/goods")
    public GoodsInfo[] getGoods(HttpSession httpSession)
    {
        String ownerName = (String) httpSession.getAttribute("id");
        if(ownerName == null)
            return null;
        GoodsInfo[] res = goodsService.getGoodsByOwner(ownerName);
        return res;
    }

    @GetMapping("data/get_goods")
    public GoodsInfo getGoodsByGid(@RequestParam Integer gid)
    {
            return goodsService.getGoodsByGsid(gid);
    }

    @PostMapping("data/new_goods")
    public GoodsInfo addNewGoods(@RequestBody GoodsInfo goodsInfo, HttpSession httpSession)
    {
        String username = (String) httpSession.getAttribute("id");
        if(username == null)
        {
            return GoodsInfo.Fail_info;
        }
        else
        {
            GoodsInfo res = goodsService.addNewGoods(goodsInfo, username);
            return res;
        }
    }

    @PostMapping("data/edit_goods")
    public GoodsInfo editGoods(@RequestBody GoodsInfo goodsInfo, HttpSession httpSession)
    {
        String username = (String) httpSession.getAttribute("id");
        if(username == null)
        {
            return GoodsInfo.Fail_info;
        }
        else
        {
            GoodsInfo res = goodsService.editGoods(goodsInfo);
            return res;
        }
    }


}
