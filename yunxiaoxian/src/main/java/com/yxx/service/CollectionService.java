package com.yxx.service;

import com.alibaba.fastjson.JSONObject;
import com.yxx.pojo.Collection;
import com.yxx.pojo.GoodsCustom;

import java.util.List;

public interface CollectionService {

    /**
     * 根据openID查询用户的收藏物品
     * @param openID
     * @return
     */
    public List<GoodsCustom> selectUserCollerction(String openID, Integer currentPage);

    /**
     * 将用户收藏的物品信息存入数据库
     * @param openID
     * @param goodsID
     * @return
     */
    public JSONObject insertUserCollection(String openID, Integer goodsID);
    /**
     * 根据openID和goodsID查询collection中的记录
     * @param openID
     * @param goodsID
     * @return
     */
    public List<Collection> selectCollectionByGoodsIDAndOpenID(String openID,
                                                               Integer goodsID);

    /**
     * 取消收藏
     * @param openID
     * @param goodsID
     * @return
     */
    public JSONObject cancelCollection(String openID, Integer goodsID);

}
