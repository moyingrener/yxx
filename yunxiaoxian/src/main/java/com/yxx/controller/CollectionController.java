package com.yxx.controller;

import com.alibaba.fastjson.JSONObject;
import com.yxx.pojo.GoodsCustom;
import com.yxx.service.CollectionService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class CollectionController {

    private static Logger logger = Logger.getLogger(CollectionController.class);

    @Autowired
    private CollectionService collectionService;

    //我的收藏
    @PostMapping("selectUserCollection")
    @ResponseBody
    public JSONObject selectUserCollection(String openID, Integer currentPage){
        JSONObject json = new JSONObject();
        List<GoodsCustom> collectionList = null;

        try {
            collectionList = collectionService.selectUserCollerction(openID, (currentPage - 1) * 10);
        } catch (Exception e) {
            logger.error("error:{}" + " from" + getClass(), e);
        }

        if(collectionList != null && collectionList.size() != 0){
            json.put("collectionList", collectionList);
            return json;
        }

        json.put("collectionList", null);
        return json;
    }

    //收藏物品
    @PostMapping("collectGoods")
    @ResponseBody
    public JSONObject collectGoods(String openID, Integer goodsID){

        //调用收藏物品的Service方法
        return collectionService.insertUserCollection(openID, goodsID);
    }

    //取消收藏
    @PostMapping("cancelCollection")
    @ResponseBody
    public JSONObject cancelCollection(String openID, Integer goodsID){

        return collectionService.cancelCollection(openID, goodsID);
    }
}