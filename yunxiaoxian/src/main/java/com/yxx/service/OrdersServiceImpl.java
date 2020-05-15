package com.yxx.service;

import com.alibaba.fastjson.JSONObject;
import com.yxx.dao.OrdersMapper;
import com.yxx.dao.UserMapper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class OrdersServiceImpl implements OrdersService {
    private Logger logger = Logger.getLogger(OrdersServiceImpl.class);

    @Autowired
    private OrdersMapper ordersMapper;

    @Autowired
    private UserMapper userMapper;

    @Override
    public JSONObject deleteOrders(String openID, Integer identity, Integer goodsID) {
        JSONObject json = new JSONObject();

        if(identity == 0){  //如果是买家
            try {
                if(1 ==ordersMapper.updateOrderOfUserStatus(openID,null, goodsID)){
                    json.put("status", true);
                    return json;
                }
            } catch (Exception e){
                logger.debug("error:{}-->", e);
            }
        } else if(identity == 1){
            try {
                if(1 ==ordersMapper.updateOrderOfUserStatus(null, openID, goodsID)){
                    json.put("status", true);
                    return json;
                }
            } catch (Exception e){
                logger.debug("error:{}-->", e);
            }
        }

        json.put("status", false);
        return json;
    }

    //拉黑
    @Override
    public boolean blacklist(String openID1,String openID2){
        System.out.println("================================================ser"+openID1);
        System.out.println("================================================ser"+openID2);
        //交易过，并且没拉黑过
        int countb = ordersMapper.blacklist(openID1,openID2);
        //交易记录
        int countt = ordersMapper.tradingRecord(openID1,openID2);
        if (countt>0 && countb == countt){
            int ub = userMapper.updateBlacklist(openID2);
            if(ub == 1){
                return true;
            }else {
                return false;
            }

        }else {
            return false;
        }
    }
}
