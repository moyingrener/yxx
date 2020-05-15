package com.yxx.websocket;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yxx.dao.ChatMapper;
import com.yxx.pojo.Chat;
import com.yxx.service.ChatService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;
import javax.annotation.Resource;


/**
 * 处理器
 */
@Component
public class WebSocketPushHandler implements WebSocketHandler {

    private static final Logger logger = Logger.getLogger(WebSocketPushHandler.class);

    @Resource
    private ChatService chatService;

    private static final List<WebSocketSession> users = new ArrayList<>();

    //存储WebsocketSession
    private static final ConcurrentHashMap<String, WebSocketSession> mapUserSession = new ConcurrentHashMap<String, WebSocketSession>(1024);


    // 建立websocket连接时调用该方法
    // 用户进入系统监听
    // 用户连接上websocket后发送离线消息
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {

        //获取当前用户的session
        //String openID = this.getUserSession(session);

        Map<String, Object> attributes = session.getAttributes();

        String openID = (String) attributes.get("openID");
        //System.out.println(openID);
        //System.out.println(session.getAttributes());
        //System.out.println(attributes.toString());
        //System.out.println(session.getAttributes());
        //session.sendMessage(new TextMessage(openID + "连接成功" + "-->" + session.getId()));
        if(openID != null) {
            //将用户session按照<openID, session>存起来
            mapUserSession.put(openID, session);
            //发送离线消息
            chatService.offLineMessage(openID, session);
        }

        //} else {
            //session.sendMessage(new TextMessage("{\"error\": \"the session of openID is null\"}"));
        //}
        //System.out.println("连接-->afterConnectionEstablished");
    }


    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        //获取消息
        String msg = message.getPayload().toString();

        //将消息转为将消息转为JSONObjec类型
        JSONObject json = JSON.parseObject(msg);

        //获取接收者的openID
        String openID = json.getString("toUser");

        //将JSONObjec对象转为Chat类
        Chat chat = JSON.parseObject(msg, Chat.class);

        //获取接收者的WebsocketSession
        WebSocketSession toUserSession = mapUserSession.get(chat.getToUser());

        //session.sendMessage(message);

        //2.检查该openID对应的session是否存在
        //2.1如果存在则转发信息
        //2.2如果不存在则把聊天记录存到数据库
        if(toUserSession != null && toUserSession.isOpen()) {
            try {
                toUserSession.sendMessage(message);
            } catch (IOException i) {
                logger.error("handleMessage error:{}", i);
            }
        } else {
            chatService.insertChatMessage(chat);
        }

    }

    // 后台错误信息处理方法
    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        if (session.isOpen()) {
            session.close();
        }
        //从mapUserSession中移除该openID的session
        mapUserSession.remove(this.getUserSession(session));
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        // 用户退出后的处理，退出之后，要将用户信息从websocket的session中remove掉，这样用户就处于离线状态了，也不会占用系统资源
        if (session.isOpen()) {
            session.close();
        }
        //从mapUserSession中移除该openID的session
        mapUserSession.remove(this.getUserSession(session));
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }


    /**
     * 获取当前用户session
     * @param session
     * @return
     */
    private String getUserSession(WebSocketSession session){
        try {
            String openID = (String) session.getAttributes().get("openID");
            //System.out.println(openID);
            return openID;
        } catch (Exception e){
            logger.error("error by getUserSession:{}", e);
        }
        return null;
    }


//    /**
//     * 发送消息给指定的用户
//     */
//    public void sendmsg(String AopenID, String BopenID, String message) {
//
//        System.out.println("==================================================");
//        System.out.println(mapUserSession.get(AopenID));
//        System.out.println(mapUserSession.get(BopenID));
//        System.out.println(message);
//        System.out.println("服务器接收到信息，并成功转发");
//        WebSocketSession session = mapUserSession.get(AopenID);
//        System.out.println(session.toString());
//        JSONObject json = new JSONObject();
//        json.put("status", true);
//        json.put("chatType", "off-line");
//
//        TextMessage msg = new TextMessage(json.toJSONString());
//        //ChatMapper cm = JSON.parseObject(msg, ChatMessage.class);
//
//        try {
//            System.out.println(json.toJSONString());
//            session.sendMessage(msg);
//        } catch (IOException e) {
//            logger.error("error:{}", e);
//        }
//
//    }

}