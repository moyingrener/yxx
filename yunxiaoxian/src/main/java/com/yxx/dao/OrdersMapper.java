package com.yxx.dao;

import org.apache.ibatis.annotations.Param;

public interface OrdersMapper {

    /**
     * 修改订单表用户的状态---“删除订单”
     * @param buyerOpenID
     * @param goodsID
     * @return
     */
    public int updateOrderOfUserStatus(@Param("buyerOpenID")String buyerOpenID,
                                       @Param("sellerOpenID")String sellerOpenID,
                                       @Param("goodsID")Integer goodsID);

    //交易记录，并且没拉黑过
    public int blacklist(@Param("openID1")String openID1,
                         @Param("openID2")String openID2);

    //交易记录
    public int tradingRecord(@Param("openID1")String openID1,
                            @Param("openID2")String openID2);

}
