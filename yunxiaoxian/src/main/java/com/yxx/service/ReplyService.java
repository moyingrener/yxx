package com.yxx.service;


import com.yxx.pojo.Reply;

import java.util.List;

public interface ReplyService {
    //查询单个留言详细信息(属于该留言的所有对话)
    public List<Reply> selectDetailForOneReply(String messageNumber,Integer currentPage,Integer counts);
    //根据messageNumber查询reply表中时间最早的一条记录
    public Reply selectSpeakerAndListenerByMessageNumber(String messageNumber);
    //插入reply表
    public int insertReplyToReply(Reply reply);
}
