package com.Domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoodsInfo {
    private Integer gid;
    private String title;
    private String detail;
    private Float price;
    private Integer amount;
    private String owner;
    public final static GoodsInfo Success_info = new GoodsInfo(0,null,null,null,null,null);
    public final static GoodsInfo Fail_info = new GoodsInfo(null, null, null, null, null, null);


}
