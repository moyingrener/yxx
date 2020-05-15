package com.yxx.service;

import com.alibaba.fastjson.JSONObject;

public interface OrdersService {

    /**
     * “删除订单”--实则为对用户隐藏订单
     * @return
     */
    public JSONObject deleteOrders(String openID, Integer identity, Integer goodsID);

    //拉黑
    public boolean blacklist(String openID1,String openID2);

}
