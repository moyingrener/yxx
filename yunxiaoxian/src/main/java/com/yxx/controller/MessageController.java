package com.yxx.controller;

import com.alibaba.fastjson.JSONObject;
import com.yxx.pojo.Message;
import com.yxx.pojo.MessageCustom;
import com.yxx.pojo.Reply;
import com.yxx.pojo.User;
import com.yxx.service.MessageService;
import com.yxx.service.ReplyService;
import com.yxx.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Controller
public class MessageController {
    private static Logger logger = LoggerFactory.getLogger(MessageController.class);
    @Autowired
    private MessageService messageService;
    @Autowired
    private ReplyService replyService;
    @Autowired
    private UserService userService;

    //查询买家留言
    @PostMapping("/selectAllMyMessage")
    @ResponseBody
    public JSONObject selectAllMyMessage(String openID, String userName, Integer currentPage) throws UnsupportedEncodingException {
        JSONObject json = new JSONObject();
        //List<String> messageNumberList = new ArrayList<String>();//存留言框编号集合
        List<MessageCustom> messagelist = null;//存所有信息集合
        //List<Integer> messages = null;//存每个编号框对应的回复数
        if (currentPage != null && openID != null && userName != null) {//openID和页码不为空
            try {
                messagelist = messageService.selectAllMyMessage(openID, userName, (currentPage - 1) * 10);//查询所有留言
            } catch (Exception e) {
                logger.error("selectAllMyMessage--> error:{}", e);
            }
/*            if (messagelist.size() > 0) {//假如能查到
                int size = messagelist.size();//存集合长度
                for (MessageCustom messageCustom : messagelist) {
                    messageNumberList.add(messageCustom.getMessageNumber());//存留言框编号
                }
                for (int len = 0; len < size; len++) {
                    //查询每个编号框对应的回复数
                    Integer messageCount = messageService.selectOneMessageNumberForReplyCount(messageNumberList.get(len));
                    messagelist.get(len).setCount(messageCount);//将回复数分别存进每个MessageCustom
                }
            }*/
        } else if (currentPage == null && openID != null && userName != null) {//openID不为空和页码为空
            try {
                messagelist = messageService.selectAllMyMessage(openID, userName, 0);//查询所有留言
            } catch (Exception e) {
                logger.error("selectAllMyMessage--> error:{}", e);
            }
/*            if (messagelist.size() > 0) {//假如能查到
                int size = messagelist.size();//存集合长度
                for (MessageCustom messageCustom : messagelist) {
                    messageNumberList.add(messageCustom.getMessageNumber());//存留言框编号
                }
                for (int len = 0; len < size; len++) {
                    //查询每个编号框对应的回复数
                    Integer messageCount = messageService.selectOneMessageNumberForReplyCount(messageNumberList.get(len));
                    messagelist.get(len).setCount(messageCount);//将回复数分别存进每个MessageCustom
                }
            }*/
        } else {//openID为空
            json.put("messagelist", null);
            return json;
        }
        if(messagelist!=null&&messagelist.size()>0){
            json.put("messagelist", messagelist);
        }else {
            json.put("messagelist", null);
        }
        return json;
    }

    //查看留言详细记录
    @PostMapping("/selectDetailForReply")
    @ResponseBody
    public JSONObject selectDetailForReply(@RequestParam("messageNumber") String messageNumber,
                                           @RequestParam(value = "currentPage", required = false) Integer currentPage) {

        JSONObject json = new JSONObject();
        List<Reply> replylist = null;

        if (currentPage != null) {//openID
            try {
                replylist = replyService.selectDetailForOneReply(messageNumber, (currentPage - 1) * 15, null);//查询回复信息
            } catch (Exception e) {
                logger.error("selectDetailForOneReply--> error:{}", e);
            }
        } else if (currentPage == null) {//openID
            try {
                replylist = replyService.selectDetailForOneReply(messageNumber, 0, null);//查询回复信息
            } catch (Exception e) {
                logger.error("selectDetailForOneReply--> error:{}", e);
            }
        }
        if (replylist != null && replylist.size() > 0) {
            json.put("replylist", replylist);
        } else {
            json.put("replylist", null);
        }
        return json;
    }

    //留言
    @PostMapping("/doMessage")
    @ResponseBody
    public JSONObject doMessage(@ModelAttribute("reply") Reply reply, @ModelAttribute("messages") Message messages,
                                @RequestParam("goodsId") Integer goodsId, String listenerOpenID, String listener,
                                @RequestParam("speakerOpenID") String speakerOpenID, @RequestParam("speaker") String speaker,
                                @RequestParam("speakerImage") String speakerImage, @RequestParam("message") String message,
                                String messageNumber) throws ParseException {

        JSONObject json = new JSONObject();
        if (speakerOpenID != null && goodsId != null && message != null && speaker != null && speakerImage != null) {
            UUID randomUUID = UUID.randomUUID();
            //查询是否留言过(根据goods_id和留言者openID查询是否存在留言框编号)
            List<String> messageNumbers = null;//留言框编号
            int k=0;
                User user = null;
                try {//查询卖家信息
                    user = userService.selectUserByGoodsId(goodsId);
                } catch (Exception e) {
                    logger.error("selectUserByGoodsId--> error:{}", e);
                }
                if (messageNumber == null) {//假如留言框编号为null 说明是第一次留言
                    int i = 0;
                    int j = 0;
                    String firstMessageNumber = randomUUID.toString() + System.currentTimeMillis();//第一个留言框编号=随机数+时间戳
                    String dateString = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());//获取时间
                    messages.setMessageNumber(firstMessageNumber);
                    messages.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateString));//设置时间
                    if (speakerOpenID.equals(user.getOpenID())) {//假如是卖家自己留言
                        messages.setOpenID(user.getOpenID());
                        try {
                            i = messageService.insertMessageByMessage(messages);//插入message表 给留言者存一条记录索引
                        }catch (Exception e){
                            logger.error("insertReplyToReply--> error:{}", e);
                        }
                    } else {//假如是买家给卖家留言
                        try {
                            messages.setOpenID(speakerOpenID);
                            i = messageService.insertMessageByMessage(messages);//插入message表 给留言者存一条记录索引
                            if (i > 0) {
                                messages.setOpenID(user.getOpenID());//插入message表
                                i = messageService.insertMessageByMessage(messages);//给听者存一条记录索引
                            }
                            //插入message表 给买家存一条记录索引
                        } catch (Exception e) {
                            logger.error("insertMessageByMessage--> error:{}", e);
                        }
                    }
                    if (i > 0) {
                        reply.setCreateTime(messages.getCreateTime());
                        reply.setMessageNumber(firstMessageNumber);
                        if (speakerOpenID.equals(user.getOpenID())) {//假如是卖家给自己留言
                            reply.setSeller(user.getOpenID());
                            reply.setBuyer(user.getOpenID());
                            reply.setListener(speaker);
                        } else {//假如是买家给卖家留言
                            reply.setSeller(user.getOpenID());
                            reply.setBuyer(speakerOpenID);
                            reply.setListener(user.getUserName());
                        }
                        try {
                            j = replyService.insertReplyToReply(reply);
                        } catch (Exception e) {
                            logger.error("insertReplyToReply--> error:{}", e);
                        }
                    }
                    if (j > 0) {//留言成功
                        json.put("status", true);
                    } else {//留言失败
                        json.put("status", false);
                    }
                } else {//假如留言框编号不为null 说明是回复操作
                    int i = 0;
                    int j = 0;
                    String dateString = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());//获取时间
                    messages.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateString));//设置时间
                    if (speakerOpenID.equals(user.getOpenID())&&listenerOpenID.equals(user.getOpenID())) {
                        //假如是卖家回复自己
                        reply.setSeller(user.getOpenID());
                        reply.setBuyer(user.getOpenID());
                    } else if((speakerOpenID.equals(user.getOpenID())&&!listenerOpenID.equals(user.getOpenID()))||
                            (!speakerOpenID.equals(user.getOpenID())&&listenerOpenID.equals(user.getOpenID()))){
                        //假如是买家卖家之间相互回复
                        reply.setSeller(user.getOpenID());
                        if(speakerOpenID.equals(user.getOpenID())){//从说话者和听话者判断谁是买家
                            reply.setBuyer(listenerOpenID);
                        }else {
                            reply.setBuyer(speakerOpenID);
                        }
                    }else if(!speakerOpenID.equals(user.getOpenID())&&!listenerOpenID.equals(user.getOpenID())){
                        //假如是买家回复买家
                        reply.setSeller(user.getOpenID());
                        reply.setBuyer(speakerOpenID);
                    }
                        reply.setCreateTime(messages.getCreateTime());
                        try {
                            j = replyService.insertReplyToReply(reply);
                        } catch (Exception e) {
                            logger.error("selectUserByGoodsId--> error:{}", e);
                        }
                    if (j > 0) {//留言成功
                        json.put("status", true);
                    } else {//留言失败
                        json.put("status", false);
                    }
                }
        } else {
            json.put("status", false);
        }
        return json;
    }
}
