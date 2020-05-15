package com.yxx.controller;

import com.alibaba.fastjson.JSONObject;
import com.yxx.util.AesCbcUtil;
import com.yxx.util.HttpRequest;
import com.yxx.pojo.GoodsCustom;
import com.yxx.pojo.MessageCustom;
import com.yxx.pojo.User;
import com.yxx.service.CollectionService;
import com.yxx.service.OrdersService;
import com.yxx.service.UserService;
import org.apache.ibatis.annotations.Param;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;


@Controller
public class UserController {
    private static Logger logger = Logger.getLogger(UserController.class);


    @Autowired
    private UserService userService;

    @Autowired
    private CollectionService collectionService;

    @Autowired
    public OrdersService ordersService;

    @RequestMapping(value = "/test")
    public String test(){

        return "index";
    }



    @RequestMapping(value = "/selectAllUser")
    @ResponseBody
    public List<User> selectAllUser(){
        List<User> userslist = userService.selectAllFormUser();
        return userslist;
    }

    //修改用户或注册用户
    @PostMapping("updateUser")
    @ResponseBody
    public JSONObject updateUser(@RequestParam("openID")String openID,
                                 @RequestParam("userName")String userName,
                                 @RequestParam("userImage")String userImage,
                                 HttpSession session){
        session.setAttribute("openID", openID);

        JSONObject json = new JSONObject();

        User user = new User();
        user.setOpenID(openID);
        user.setUserImage(userImage);
        user.setUserName(userName);

        User temp = userService.selectUserByOpenID(openID);

        //如果user用户存在,则更新用户信息
        if(temp != null){
            if(userService.updateUser(user) == 1){  //如果更新成功返回true，否则返回false
                System.out.println("更新");
                json.put("status", true);
                return json;
            }

            json.put("status", false);
            return json;
        }
        //如果user用户不存在,则注册用户
        if(userService.registerUser(user) == 1){    //如果注册成功返回true，否则返回false
            System.out.println("注册");
            json.put("status", true);
            return json;
        }

        json.put("status", true);
        return json;
    }

    //卖家售出功能
    @PostMapping("soldMyGoods")
    @ResponseBody
    public JSONObject soldMyGoods(String buyer, String seller,Integer goodsId){

        return userService.soldMyGoods(buyer, seller, goodsId);
    }

    //删除订单
    @PostMapping("deleteOrder")
    @ResponseBody
    public JSONObject deleteOrder(String openID, Integer goodsID, Integer identity){
        JSONObject json = new JSONObject();

        return ordersService.deleteOrders(openID, identity, goodsID);
    }

    //根据openID查询用户信息
    @PostMapping("/selectUserByopenID")
    @ResponseBody
    public JSONObject selectUserByopenID(@RequestParam(value="openID",required = true) String openID){
        JSONObject json = new JSONObject();
        User user=null;
        try {
            user = userService.selectUserByOpenID(openID);
        } catch (Exception e) {
            logger.debug("selectUserByopenID--> error:{}", e);
        }
        if(user!=null){
            json.put("user",user);
            return json;
        }else {
            json.put("user",null);
            return json;
        }
    }

    //刷新物品创建时间
    @PostMapping("polishGoods")
    @ResponseBody
    public JSONObject polishGoods(String openID, Integer goodsID){

        return userService.updateGoodsCreateTime(openID, goodsID);
    }

    //下架物品
    @PostMapping("pullOffShelves")
    @ResponseBody
    public JSONObject pullOffShelves(String openID, Integer goodsID){

        return userService.updateGoodsStatus(openID, goodsID, true);
    }

    //上架物品
    @PostMapping("pullOnShelves")
    @ResponseBody
    public JSONObject pullOnShelves(String openID, Integer goodsID){

        return userService.updateGoodsStatus(openID, goodsID, false);
    }

    //删除物品
    @PostMapping("deleteGoods")
    @ResponseBody
    public JSONObject deleteGoods(String openID, Integer goodsID){

        return userService.deleteGoods(openID, goodsID);
    }

    //我的下架物品
    @PostMapping("OffShelvesGoods")
    @ResponseBody
    public JSONObject OffShelvesGoods(String openID, Integer currentPage){

        return userService.OffShelvesGoods(openID, (currentPage - 1) * 10);
    }
}
