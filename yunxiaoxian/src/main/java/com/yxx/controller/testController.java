package com.yxx.controller;

import com.alibaba.fastjson.JSONObject;
import com.yxx.pojo.Chat;
import com.yxx.pojo.ChatList;
import com.yxx.websocket.WebSocketPushHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.socket.TextMessage;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class testController {

    @Resource
    private WebSocketPushHandler webSocketPushHandler;

    @RequestMapping("login")
    //@ResponseBody
    public String test(HttpSession session, @RequestParam("openID")String openID){

        //System.out.println(session.getAttribute("openID"));

        //session.setAttribute("openID", openID);
        //System.out.println(session.getId()+"=========这是控制器的");

        //System.out.println(session.getAttribute("openID"));


        return "websocket";
    }

    @RequestMapping("chat")
    public String chat(){

        return "uploadchats";
    }



}