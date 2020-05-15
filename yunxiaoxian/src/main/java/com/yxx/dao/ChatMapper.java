package com.yxx.dao;

import com.yxx.pojo.Chat;
import com.yxx.pojo.ChatList;
import com.yxx.pojo.ChatRecord;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface ChatMapper {

    /**
     * 查找离线聊天信息
     * @param openID
     * @return
     */
    public List<Chat> selectChatMessage(@Param("openID")String openID);

    /**
     * 删除离线聊天信息
     * @param openID
     */
    public void deleteChatMessage(@Param("openID")String openID);

    /**
     * 获取聊天列表
     * @param openID
     * @return
     */
    public List<ChatList> getChatList(@Param("openID")String openID,
                                      @Param("currentPage")Integer currentPage);

    /**
     * 删除聊天列表
     * @param A_openID   //本人的openID
     * @param B_openID   //对方的openID
     */
    public void deleteChatList(@Param("A_openID")String A_openID,
                               @Param("B_openID")String B_openID,
                               @Param("goodsID")Integer goodsID);

    /**
     * 保存聊天记录
     * @param chat
     */
    public void insertChatMessage(Chat chat);

    /**
     * 增加聊天列表
     * @param AOpenID
     * @param BOpenID
     * @param goodsID
     * @param modifyTime
     */
    public void insertChatList(@Param("AOpenID")String AOpenID,
                               @Param("BOpenID")String BOpenID,
                               @Param("goodsID")Integer goodsID,
                               @Param("modifyTime")Date modifyTime);

    /**
     * 上传聊天图片
     * @param imageName
     */
    public void insertImage(@Param("imageName")String imageName);

    /**
     * 上传聊天记录
     * @param chatRecords
     * @param openID
     */
    public void uploadChatRecord(@Param("chatRecords")List<ChatRecord> chatRecords,
                                 @Param("openID")String openID);

    /**
     * 获取聊天记录
     * @param A_openID
     * @param goodsId
     * @param currentPage
     * @return
     */
    public List<ChatRecord> getChatRecordList(@Param("A_openID")String A_openID,
                                              @Param("goodsId")Integer goodsId,
                                              @Param("currentPage")Integer currentPage);

    /**
     * 删除聊天记录
     * @param A_openID
     * @param goodsId
     */
    public void deleteChatRecord(@Param("A_openID")String A_openID,
                                 @Param("goodsId")Integer goodsId);
}