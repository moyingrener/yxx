package com.yxx.service;

import com.alibaba.fastjson.JSONObject;
import com.yxx.dao.UserMapper;
import com.yxx.pojo.Goods;
import com.yxx.pojo.GoodsCustom;
import com.yxx.pojo.User;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    Logger logger = Logger.getLogger(UserServiceImpl.class);

    @Autowired
    private UserMapper userMapper;

    @Override
    public List<User> selectAllFormUser() {
        return userMapper.selectAllFormUser();
    }

    @Override
    public User selectUserByOpenID(String openID) {

        return userMapper.selectByOpenID(openID);
    }

    @Override
    public int updateUser(User user) {

        return userMapper.updateUserByOpenID(user);
    }

    @Override
    public int registerUser(User user) {

        return userMapper.registerUser(user);
    }

    @Override
    public User selectUserByGoodsId(Integer goodsId) {

        return userMapper.selectUserByGoodsId(goodsId);
    }

    @Override
    public JSONObject soldMyGoods(String buyer, String seller, Integer goodsId) {

        JSONObject json = new JSONObject();

        try {
            Date create_time = new Date();
            userMapper.updateMySoldGoodsStatus(seller, goodsId);
            userMapper.insertOrders(buyer, seller, goodsId, create_time);
            json.put("status", true);
            return json;
        } catch (Exception e) {
            logger.error("soldMyGoods > error:{}", e);
        }

        json.put("status", false);
        return json;
    }

    @Override
    public JSONObject updateGoodsCreateTime(String openID, Integer goodsID) {
        JSONObject json = new JSONObject();

        Date newTime = new Date();

        try {
            if(1 == userMapper.updateGoodsCreateTime(openID, goodsID, newTime)){
                json.put("status", true);
                return json;
            }
        } catch (RuntimeException r){
            logger.debug("error{}", r);
        }

        json.put("status", false);
        return json;
    }

    @Override
    public JSONObject updateGoodsStatus(String openID, Integer goodsID, boolean flag) {
        JSONObject json = new JSONObject();
        try {
            if(1 == userMapper.updateGoodsStatus(openID, goodsID, flag)){
                json.put("status", true);
                return json;
            }
        } catch (RuntimeException r){
            logger.debug("error{}", r);
        }

        json.put("status", false);
        return json;
    }

    @Override
    public JSONObject deleteGoods(String openID, Integer goodsID) {
        JSONObject json = new JSONObject();
        try {
            if(1 == userMapper.deleteGoods(openID, goodsID)){
                json.put("status", true);
                return json;
            }
        } catch (RuntimeException r){
            logger.debug("error{}", r);
        }

        json.put("status", false);
        return json;
    }

    @Override
    public JSONObject OffShelvesGoods(String openID, Integer currentPage) {
        JSONObject json = new JSONObject();
        try {
            List<Goods> offGoodsList = userMapper.OffShelvesGoods(openID, currentPage);
            json.put("offGoodsList", offGoodsList);
        } catch (RuntimeException r){
            logger.error("error:{}", r);
        }
        return json;
    }
}
