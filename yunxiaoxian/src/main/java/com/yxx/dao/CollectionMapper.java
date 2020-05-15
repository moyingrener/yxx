package com.yxx.dao;

import com.yxx.pojo.Collection;
import com.yxx.pojo.GoodsCustom;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.PostMapping;

import java.net.Inet4Address;
import java.util.List;

public interface CollectionMapper {

    /**
     * 根据用户的openID查询其所收藏的物品信息
     * @param openID
     * @param currentPage
     * @return
     */
    public List<GoodsCustom> selectUserCollectionByOpenID(@Param("openID")String openID,
                                                          @Param("currentPage")Integer currentPage);

    /**
     * 将用户收藏物品信息存入collection表
     * @param openID
     * @param goodsID
     */
    public int insertUserCollection(@Param("openID")String openID,
                                     @Param("goodsID")Integer goodsID);

    /**
     * 根据openID和goodsID查询collection中的记录
     * @param openID
     * @param goodsID
     * @return
     */
    public List<Collection> selectCollectionByGoodsIDAndOpenID(@Param("openID")String openID,
                                                          @Param("goodsID")Integer goodsID);

    /**
     * 取消收藏
     * @param openID
     * @param goodsID
     * @return
     */
    public int cancelCollection(@Param("openID")String openID,
                                @Param("goodsID")Integer goodsID);
}
