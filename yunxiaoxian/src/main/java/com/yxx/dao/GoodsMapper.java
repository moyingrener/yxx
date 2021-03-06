package com.yxx.dao;

import com.yxx.pojo.Goods;
import com.yxx.pojo.GoodsCustom;
import com.yxx.pojo.OrderCustom;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface GoodsMapper {
    //搜索框 根据商品描述查询商品信息(分页)
    public List<GoodsCustom> selectGoodsByGoodsDescribe(Goods goods);
    //搜索框 根据商品描述或类型查询相应商品在商品表的记录数
    public int selectCountByGoods(Goods goods);
    //查询单个商品的详细信息
    public GoodsCustom selectOneGoodsByGoodsId(Goods goods);
    //查询我卖的商品信息
    public List<OrderCustom> selectAllMySaleGoods(@Param("openID") String openID,@Param("currentPage") Integer currentPage);
    //查询我买的商品信息
    public List<OrderCustom> selectAllMyBuyGoods(@Param("openID") String openID,@Param("currentPage") Integer currentPage);
    //查询我发布的商品信息
    public List<Goods> selectAllMyPublishGoods(@Param("openID") String openID,@Param("currentPage") Integer currentPage);
    //上传商品
    public int uploadGoods(Goods goods);
    //根据商品所有信息修改更新商品信息
    public int updateGoodsByGoods(Goods goods);

}
