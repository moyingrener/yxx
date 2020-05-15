package com.yxx.dao;

import com.yxx.pojo.Message;
import com.yxx.pojo.MessageCustom;
import com.yxx.pojo.Reply;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MessageMapper {
    //查询买家留言
    public List<MessageCustom> selectAllMyMessage(@Param("openID") String openID,@Param("userName") String userName, @Param("currentPage") Integer currentPage);
    //查询用户留言框分别对应的回复数量
    public Integer selectOneMessageNumberForReplyCount(@Param("messageNumber") String messageNumber);
    //根据goods_id,openID,message插入message表(留言)
    public int insertMessageByMessage(Message messages);
    //根据goodsId,openID查询留言框编号
   public List<String> selectMessageNumberByGoodsIDAndOpenID(@Param("goodsId")Integer goodsId,@Param("openID")String openID,@Param("currentPage")Integer currentPage);
}
