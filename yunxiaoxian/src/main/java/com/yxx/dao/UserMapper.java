package com.yxx.dao;

import com.alibaba.fastjson.JSONObject;
import com.yxx.pojo.Goods;
import com.yxx.pojo.GoodsCustom;
import com.yxx.pojo.User;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface UserMapper {
    //查询user表所有用户信息
    public List<User> selectAllFormUser();

    /**
     * 根据openID查询用户信息
     * @param openID
     * @return
     */
    public User selectByOpenID(@Param("openID") String openID);

    /**
     * 根据openID更新用户信息
     * @param user
     * @return
     */
    public int updateUserByOpenID(User user);

    /**
     * 注册用户
     * @param user
     * @return
     */


    public int  registerUser(User user);

    /**
     * 根据goods_id查询用户信息
     * @param goodsId
     * @return
     */
    public User selectUserByGoodsId(@Param("goodsId")Integer goodsId);




    /**
     * 更新用户在售物品状态
     * @param openID
     * @param goodsId
     * @return
     */
    public int updateMySoldGoodsStatus(@Param("openID")String openID,
                                       @Param("goodsId")Integer goodsId);

    /**
     * 添加订单
     * @param buyer
     * @param seller
     * @param goodsId
     * @param create_time
     */
    public void insertOrders(@Param("buyer")String buyer,
                             @Param("seller")String seller,
                             @Param("goodsId")Integer goodsId,
                             @Param("create_time")Date create_time);

    /**
     * 擦亮物品
     * @param openID
     * @param goodsID
     * @return
     */
    public int updateGoodsCreateTime(@Param("openID")String openID,
                                     @Param("goodsID")Integer goodsID,
                                     @Param("newTime")Date newTime);

    /**
     * 下架--上架
     * @param openID
     * @param goodsID
     * @param flag  //true为下架，false为上架
     * @return
     */
    public int updateGoodsStatus(@Param("openID")String openID,
                                 @Param("goodsID")Integer goodsID,
                                 @Param("flag")boolean flag);

    /**
     * 删除物品
     * @param openID
     * @param goodsID
     * @return
     */
    public int deleteGoods(@Param("openID")String openID,
                           @Param("goodsID")Integer goodsID);

    public int updateBlacklist(@Param("openID2")String openID2);

    /**
     * 我的下架物品
     * @param openID
     * @return
     */
    public List<Goods> OffShelvesGoods(@Param("openID")String openID,
                                             @Param("currentPage")Integer currentPage);

}