package com.yxx.controller;


import com.alibaba.fastjson.JSONObject;
import com.yxx.pojo.ChatRecord;
import com.yxx.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
public class ChatController {

    @Autowired
    private ChatService chatService;

    //获取聊天列表
    @PostMapping("getChatList")
    @ResponseBody
    public JSONObject getChatList(String openID, Integer currentPage){

        return chatService.getChatList(openID, (currentPage - 1) * 15);
    }

    //删除聊天列表
    @PostMapping("deleteChatList")
    @ResponseBody
    public JSONObject deleteChatList(String A_openID, String B_openID, Integer goodsID){

        return chatService.deleteChatList(A_openID, B_openID, goodsID);
    }

    //“我想要”按钮--添加到聊天列表
    @PostMapping("iWant")
    @ResponseBody
    public JSONObject iWant(String A_openID, String B_openID, Integer goodsID){

        return chatService.iWant(A_openID, B_openID, goodsID);
    }

    //上传聊天图片
    @PostMapping("uploadPicture")
    @ResponseBody
    public JSONObject uploadPicture(String myFile){

        return chatService.uploadPicture(myFile);
    }

    //上传聊天记录
    @RequestMapping(value = "uploadChatRecord", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject uploadChatRecord(@RequestBody List<ChatRecord> chatRecords){

        return chatService.uploadChatRecord(chatRecords);
    }

    //获取聊天记录
    @PostMapping("getChatRecord")
    @ResponseBody
    public JSONObject getChatRecord(String A_openID, Integer goodsId, Integer currentPage){

        return chatService.getChatRecord(A_openID, goodsId, (currentPage - 1) * 20);
    }
}