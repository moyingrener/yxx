package com.yxx.service;

import com.yxx.dao.MessageMapper;
import com.yxx.pojo.Message;
import com.yxx.pojo.MessageCustom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {
    @Autowired
    private MessageMapper messageMapper;

    @Override
    public List<MessageCustom> selectAllMyMessage(String openID,String userName,Integer currentPage) {
        return messageMapper.selectAllMyMessage(openID,userName,currentPage);
    }

    @Override
    public Integer selectOneMessageNumberForReplyCount(String messageNumber) {
        return messageMapper.selectOneMessageNumberForReplyCount(messageNumber);
    }

    @Override
    public int insertMessageByMessage(Message messages) {
        return messageMapper.insertMessageByMessage(messages);
    }

    @Override
    public List<String> selectMessageNumberByGoodsIDAndOpenID(Integer goodsId, String openID,Integer currentPage) {
        return messageMapper.selectMessageNumberByGoodsIDAndOpenID(goodsId,openID,currentPage);
    }

}
