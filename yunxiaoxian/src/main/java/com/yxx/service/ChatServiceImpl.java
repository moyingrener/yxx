package com.yxx.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yxx.dao.ChatMapper;
import com.yxx.pojo.Chat;
import com.yxx.pojo.ChatList;
import com.yxx.pojo.ChatRecord;
import com.yxx.service.ChatService;
import com.yxx.util.Base64Util;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.File;
import java.io.IOException;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Random;


@Service
public class ChatServiceImpl implements ChatService {
    Logger logger = Logger.getLogger(ChatServiceImpl.class);

    @Autowired
    private ChatMapper chatMapper;

    @Override
    public void offLineMessage(String openID, WebSocketSession session) {
        JSONObject json = new JSONObject();

        //查询离线消息
        List<Chat> chatList = chatMapper.selectChatMessage(openID);

        json.put("chatType", "offline");
        json.put("chatList", chatList);

        TextMessage message = new TextMessage(json.toJSONString());

        //发送信息
        if (chatList != null && chatList.size() != 0 && session.isOpen()){
            try {
                session.sendMessage(message);
            } catch (IOException e){
                logger.error("off-LineMessage error:{}", e);
                return;
            }
            //删除信息
            try {
                chatMapper.deleteChatMessage(openID);
            } catch (Exception e) {
                logger.error("off-LineMessage error:{}", e);
            }
        }
    }

    @Override
    public JSONObject getChatList(String openID, Integer currentPage) {
        JSONObject json = new JSONObject();
        List<ChatList> chatList = null;
        try {
            chatList = chatMapper.getChatList(openID, currentPage);
        } catch (Exception e){
            logger.debug("getChatList error:{}", e);
        }
        json.put("chatList", chatList);
        return json;
    }

    @Override
    public JSONObject deleteChatList(String A_openID, String B_openID, Integer goodsID) {
        JSONObject json = new JSONObject();
        try {
            chatMapper.deleteChatList(A_openID, B_openID, goodsID);
            chatMapper.deleteChatRecord(A_openID, goodsID);
            json.put("status", true);
            return json;
        } catch (Exception e){
            logger.error("deleteChatList error:{}", e);
        }

        json.put("status", false);
        return json;
    }

    @Override
    public void insertChatMessage(Chat chat) {
        try {
            chatMapper.insertChatMessage(chat);
        } catch (Exception e) {
            logger.error("insertChatMessage error:{}", e);
        }
    }

    @Override
    public JSONObject iWant(String AOpenID, String BOpenID, Integer goodsID) {
        JSONObject json = new JSONObject();

        Date modifyTime = new Date();
        try {
            chatMapper.insertChatList(AOpenID, BOpenID, goodsID, modifyTime);
            chatMapper.insertChatList(BOpenID, AOpenID, goodsID, modifyTime);
            json.put("status", true);
            return json;
        } catch (Exception e) {
            logger.error("iWant -> error:{}", e);
        }

        json.put("status", false);
        return json;
    }

    @Override
    public JSONObject uploadPicture(String myFile) {

        JSONObject json = new JSONObject();

        Random rand = new Random();                     //获取随机数

        StringBuffer newName = new StringBuffer();      //拼接url

        String file_path = "//opt//chatpic";            // 存储图片的物理路径

        String dataPrefix;
        String suffix;



        if(myFile == null || myFile.equals("")) {
            json.put("error", "上传失败，上传图片数据为空!");
            json.put("status", false);
            return json;
        } else {
            String[] d = myFile.split("base64+");
            if(d.length == 2) {
                dataPrefix = d[0];
            } else {
                json.put("error", "上传失败，数据不合法!");
                json.put("status", false);
                return json;
            }
        }

        //校验图片后缀
        if(!dataPrefix.equalsIgnoreCase("data:image/jpeg;") &&
            !dataPrefix.equalsIgnoreCase("data:image/jpg;") &&
            !dataPrefix.equalsIgnoreCase("data:image/x-icon;") &&
            !dataPrefix.equalsIgnoreCase("data:image/gif;") &&
            !dataPrefix.equalsIgnoreCase("data:image/png;")) {

            json.put("error", "上传图片格式不合法!");
            json.put("status", false);
            System.out.println("haha");
            return json;
        }

        //解析Base64
        MultipartFile multipartFile = Base64Util.base64ToMultipart(myFile);

        //将内存中的数据写入磁盘
        String imageName = (rand.nextInt(9999999) + 100000) +
                multipartFile.getName().replaceAll(".+\\.", System.currentTimeMillis() + ".");

        //存到服务器
        File file = new File(file_path + "/" + imageName);
        try {
            multipartFile.transferTo(file);
        } catch (IOException i) {
            logger.error("uploadPicture -> error:{}", i);
            json.put("error", "图片存入服务器失败!");
            json.put("status", false);
            return json;
        }

        try {
            chatMapper.insertImage(imageName);
            json.put("status", true);
            json.put("imageName", imageName);
        } catch (Exception e) {
            logger.error("uploadPicture -> error:{}", e);
            json.put("error", "图片存入数据库失败!");
            json.put("status", false);
        }

        return json;
    }

    @Override
    public JSONObject uploadChatRecord(List<ChatRecord> chatRecords) {
        JSONObject json = new JSONObject();

        String openID = null;

        for(int i = 0; i < chatRecords.size(); i++){
            if(chatRecords.get(i).getIsSelf()){
                openID = chatRecords.get(i).getFromUser();
                System.out.println(chatRecords.get(i));
                break;
            }
        }

        if(openID == null || openID == ""){
            json.put("status", false);
            return json;
        }

        try {
            chatMapper.uploadChatRecord(chatRecords, openID);
            json.put("status", true);
            return json;
        } catch (Exception e) {
            logger.error("uploadChatRecord error:{}", e);
        }

        json.put("status", false);
        return json;
    }

    @Override
    public JSONObject getChatRecord(String A_openID, Integer goodsId, Integer currentPage) {

        JSONObject json = new JSONObject();

        try {
            List<ChatRecord> chatRecordList = chatMapper.getChatRecordList(A_openID, goodsId, currentPage);
            json.put("chatRecordList", chatRecordList);
            return json;
        } catch (Exception e){
            logger.error("getChatRecord > error:{}", e);
        }

        json.put("chatRecordList", null);
        return json;
    }
}