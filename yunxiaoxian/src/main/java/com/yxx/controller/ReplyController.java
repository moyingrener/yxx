package com.yxx.controller;

import com.alibaba.fastjson.JSONObject;
import com.yxx.pojo.Reply;
import com.yxx.pojo.User;
import com.yxx.service.ReplyService;
import com.yxx.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ReplyController {
    private static Logger logger = LoggerFactory.getLogger(ReplyController.class);
    @Autowired
    private ReplyService replyService;
    @Autowired
    private UserService userService;

    //回复
    @PostMapping("/doReply")
    @ResponseBody
    //speaker,speaker_image,-listener，seller，buyer，-create_time，message，message_number，goods_id
    public JSONObject doReply(@ModelAttribute("reply")Reply reply){
        JSONObject json = new JSONObject();
        User user=null;
        try {
             user = userService.selectUserByGoodsId(reply.getGoodsId());
        }catch (Exception e){
            logger.error("error",e);
        }
        return json;
    }
}
