package com.yxx.dao;

import com.yxx.pojo.Reply;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ReplyMapper {
    //查询单个留言详细信息(属于该留言的所有对话)
    public List<Reply> selectDetailForOneReply(@Param("messageNumber")String messageNumber,@Param("currentPage")Integer currentPage,@Param("counts")Integer counts);
    //根据messageNumber查询reply表中时间最早的一条记录
    public Reply selectSpeakerAndListenerByMessageNumber(@Param("messageNumber")String messageNumber);
    //插入reply表
    public int insertReplyToReply(Reply reply);

}
