package com.yxx.service;

import com.yxx.pojo.Message;
import com.yxx.pojo.MessageCustom;

import java.util.List;

public interface MessageService {
    //查询买家留言
    public List<MessageCustom> selectAllMyMessage(String openID,String userName,Integer currentPage);
    //查询用户留言框分别对应的回复数量
    public Integer selectOneMessageNumberForReplyCount(String messageNumber);
    //根据goods_id,openID,message插入message表(留言)
    public int insertMessageByMessage(Message messages);
    //根据goodsId,openID查询留言框编号
    public List<String> selectMessageNumberByGoodsIDAndOpenID(Integer goodsId, String openID,Integer currentPage);
}
