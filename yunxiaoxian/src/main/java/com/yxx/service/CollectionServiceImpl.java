package com.yxx.service;

import com.alibaba.fastjson.JSONObject;
import com.yxx.dao.CollectionMapper;
import com.yxx.pojo.Collection;
import com.yxx.pojo.GoodsCustom;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CollectionServiceImpl implements CollectionService {
    private Logger logger = Logger.getLogger(CollectionServiceImpl.class);

    @Autowired
    private CollectionMapper collectionMapper;

    @Override
    public List<GoodsCustom> selectUserCollerction(String openID, Integer currentPage) {

        return collectionMapper.selectUserCollectionByOpenID(openID, currentPage);
    }

    @Override
    public JSONObject insertUserCollection(String openID, Integer goodsID) {
        JSONObject json = new JSONObject();

        List<Collection> temp = collectionMapper.selectCollectionByGoodsIDAndOpenID(openID, goodsID);

        //如果用户没有收藏过该物品，
        if(temp == null || temp.size() == 0){
            //则将openID和goodsID存入数据库，并返回true
            try {
                if(1 == collectionMapper.insertUserCollection(openID, goodsID)){
                    json.put("status", true);
                    return json;
                }
            } catch (Exception e){
                logger.error("insertUserCollection-->error:{}", e);
            }
        }

        //如果用户收藏过该物品，并返回false
        json.put("status", false);
        return json;
    }

    @Override
    public List<Collection> selectCollectionByGoodsIDAndOpenID(String openID, Integer goodsID) {
        return collectionMapper.selectCollectionByGoodsIDAndOpenID(openID,goodsID);
    }

    @Transactional
    @Override
    public JSONObject cancelCollection(String openID, Integer goodsID) {
        JSONObject json = new JSONObject();
        try {
            if(1 == collectionMapper.cancelCollection(openID, goodsID)){
                json.put("status", true);
                return json;
            }
        } catch (RuntimeException r){
            logger.debug("error{}", r);
            throw r;
        }

        json.put("status", false);
        return json;
    }
}
