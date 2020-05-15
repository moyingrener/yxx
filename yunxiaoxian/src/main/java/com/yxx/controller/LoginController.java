package com.yxx.controller;

import com.alibaba.fastjson.JSONObject;
import com.yxx.util.AesCbcUtil;
import com.yxx.util.HttpRequest;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class LoginController {
    private static Logger logger = Logger.getLogger(LoginController.class);

    //获取openID,并返回
    @PostMapping("getOpenID")
    @ResponseBody
    public JSONObject getOpenID(String code){
        JSONObject jsonResult = new JSONObject();

        if(code == null || code.length() == 0) {
            jsonResult.put("status", false);
            jsonResult.put("msg", "code不能为空");
            return jsonResult;
        }

        String app_id = "wxe07ebf182de62b43";
        String app_secret = "be3568c6e5af041a29db4a1595889cf3";

        //授权码
        String grant_type= "authorization_code";

        String params = "appid=" + app_id + "&secret=" + app_secret + "&js_code=" + code + "&grant_type=" + grant_type;

        //发送请求
        String sr = HttpRequest.sendGet("https://api.weixin.qq.com/sns/jscode2session", params);

        //解析相应内容（转成json对象）
        JSONObject json = JSONObject.parseObject(sr);

        //获取会话密钥session_key和用户的唯一标识openID
        //String session_key = json.get("session_key").toString();
        //String openID = (String) json.get("openid");

        jsonResult.put("openID", json.get("openid"));

        return jsonResult;
    }
}