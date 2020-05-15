package com.yxx.service;

import com.alibaba.fastjson.JSONObject;
import com.yxx.pojo.Chat;
import com.yxx.pojo.ChatRecord;
import org.springframework.web.socket.WebSocketSession;

import java.util.List;


public interface ChatService {

    /**
     * 查看是否有离线消息---有则发送
     */
    public void offLineMessage(String openID, WebSocketSession session);

    /**
     * 获取聊天列表
     * @param openID
     * @param currentPage
     * @return
     */
    public JSONObject getChatList(String openID, Integer currentPage);

    /**
     * 删除聊天列表
     * @param A_openID
     * @param B_openID
     * @param goodsID
     * @return
     */
    public JSONObject deleteChatList(String A_openID, String B_openID, Integer goodsID);

    /**
     * 保存聊天记录
     * @param chat
     */
    public void insertChatMessage(Chat chat);

    /**
     * “我想要”按钮--插入聊天列表
     * @param AOpenID
     * @param BOpenID
     * @param goodsID
     * @return
     */
    public JSONObject iWant(String AOpenID, String BOpenID, Integer goodsID);

    /**
     * 上传聊天图片
     * @param myFile
     * @return
     */
    public JSONObject uploadPicture(String myFile);

    /**
     * 上传聊天记录
     * @param chatRecords
     * @return
     */
    public JSONObject uploadChatRecord(List<ChatRecord> chatRecords);

    /**
     * 获取聊天记录
     * @param A_openID
     * @param goodsId
     * @param currentPage
     * @return
     */
    public JSONObject getChatRecord(String A_openID, Integer goodsId, Integer currentPage);
}