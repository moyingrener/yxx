package com.yxx.controller;

import com.alibaba.fastjson.JSONObject;
import com.yxx.pojo.*;
import com.yxx.service.CollectionService;
import com.yxx.service.GoodsService;

import com.yxx.service.MessageService;
import com.yxx.service.ReplyService;
import com.yxx.util.Base64Util;
import org.apache.ibatis.annotations.Param;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Controller
public class GoodsController {
    private static Logger logger = LoggerFactory.getLogger(GoodsController.class);
    @Autowired
    private GoodsService goodsService;
    @Autowired
    private CollectionService collectionService;
    @Autowired
    private ReplyService replyService;
    @Autowired
    private MessageService messageService;

    //搜索框   根据商品描述和页码模糊查询商品信息
    @GetMapping("selectGoodsByGoodsDescribe")
    @ResponseBody
    public JSONObject selectGoodsByGoodsDescribe(@ModelAttribute("goods") Goods goods, String goodsDescribe, Integer currentPage) throws UnsupportedEncodingException {
/*        if(goodsDescribe!=null){//解决搜索信息中文乱码
            goods.setGoodsDescribe(new String(goodsDescribe.getBytes("ISO-8859-1"),"UTF-8"));
        }*/
        JSONObject json = new JSONObject();
        List<GoodsCustom> goodslist = null;//商品信息集合
        Integer maxpage = null;//最大页数
        Integer count = null;//总记录数
        if (currentPage != null) {//假如发送了页码,返回后续页的数据
            goods.setCurrentPage((currentPage - 1) * 10);
            try {
                goodslist = goodsService.selectGoodsByGoodsDescribe(goods);//查询相应所有商品信息
            } catch (Exception e) {
                logger.error("selectGoodsByGoodsDescribe--> error:{}:", e);
            }
        } else {//假如没发送页码,返回第一页的数据
            goods.setCurrentPage(0);
            try {
                goodslist = goodsService.selectGoodsByGoodsDescribe(goods);//查询相应所有商品信息
            } catch (Exception e) {
                logger.error("selectGoodsByGoodsDescribe--> error:{}:", e);
            }
        }
        try {
            count = goodsService.selectCountByGoods(goods);//查询相应所有商品信息总记录数
        } catch (Exception e) {
            logger.error("selectCountByGoods--> error:{}:", e);
        }
        if (count != null) {
            if (count / 10 == 0 && count % 10 > 0) {//1-9条记录数
                maxpage = 1;
            } else if (count / 10 > 0 && count % 10 > 0) {//不能被10整除的记录数
                maxpage = (count / 10) + 1;
            } else if (count / 10 > 0 && count % 10 == 0) {//能被10整除的记录数
                maxpage = count / 10;
            }
        } else {//0条记录数
            maxpage = 0;
        }
        if (goodslist != null && goodslist.size() > 0 && maxpage != 0) {//假如查询到信息,返回
            json.put("goodslist", goodslist);//商品信息
            json.put("maxpage", maxpage);//最大页数
            json.put("count", count);//总纪录数
        } else {//没查询到,返回null
            json.put("goodslist", null);
        }
        return json;
    }

    //返回 单个商品详细信息和留言回复
    @RequestMapping("/selectOneGoodsDetailMessage")
    @ResponseBody
    public JSONObject selectOneGoodsDetailMessage(@ModelAttribute("goods") Goods goods,
                                                  @RequestParam("goodsId") Integer goodsId,
                                                  @RequestParam("openID") String openID,
                                                  @RequestParam("currentPage") Integer currentPage) {

        GoodsCustom goodsmessage = null;
        List<Collection> collections = null;
        JSONObject json = new JSONObject();
        try {
            goodsmessage = goodsService.selectOneGoodsByGoodsId(goods);//查询商品信息
        } catch (Exception e) {
            logger.error("selectOneGoodsByGoodsId--> error:{}:", e);
        }
        if (goodsmessage != null) {
            json.put("goodsmessage", goodsmessage);
            try {
                collections = collectionService.selectCollectionByGoodsIDAndOpenID(openID, goodsId);//查询是否收藏
            } catch (Exception e) {
                logger.error("selectCollectionByGoodsIDAndOpenID--> error:{}:", e);
            }
            if (collections.size() > 0) {
                json.put("collected", true);
            } else {
                json.put("collected", false);
            }
        } else {//假如没查到或者商品id为null,返回null
            json.put("goodsmessage", null);
            return json;
        }
        //查询留言回复信息
        List<String> numberList=null;
        try {//查询留言框编号集合
            if(currentPage==null){
                numberList = messageService.selectMessageNumberByGoodsIDAndOpenID(goodsId, null,0);
            }else {
                numberList = messageService.selectMessageNumberByGoodsIDAndOpenID(goodsId, null,(currentPage-1)*6);
            }
        }catch (Exception e){
            logger.error("selectMessageNumberByGoodsIDAndOpenID--> error:{}:", e);
        }
        if(numberList!=null&&numberList.size()>0){
            List<List<Reply>> replylist=new ArrayList<List<Reply>>();//存查询出来的多个List<Reply>
            for (String number:numberList){
                List<Reply> replies=null;
                try {//根据留言框编号查询留言信息
                        replies = replyService.selectDetailForOneReply(number,null,0);
                }catch (Exception e){
                    logger.error("selectReplyDetailByMessageNumber--> error:{}:", e);
                }
                if(replies!=null&&replies.size()>0){
                    replylist.add(replies);
                }
            }
            json.put("replylist",replylist);
        }else {//假如没有留言
            json.put("replylist",new ArrayList<List<Reply>>());
        }

        return json;
    }

    //查询我卖的商品
    @PostMapping("selectAllMySaleGoods")
    @ResponseBody
    public JSONObject selectAllMySaleGoods(String openID, Integer currentPage) {

        JSONObject json = new JSONObject();
        List<OrderCustom> mysalelist = null;
        try {
            if (currentPage != null) {//查询我卖出的商品信息
                mysalelist = goodsService.selectAllMySaleGoods(openID, (currentPage - 1) * 10);
            } else {
                mysalelist = goodsService.selectAllMySaleGoods(openID, 0);
            }
        } catch (Exception e) {
            logger.error("selectAllMySaleGoods--> error:{}:", e);
        }
        if (mysalelist != null && mysalelist.size() != 0) {//假如查询到我卖出的商品返回
            json.put("mysalelist", mysalelist);
            return json;
        } else {//没查询到我卖出的商品返回空
            json.put("mysalelist", null);
            return json;
        }
    }

    //查询我买的商品
    @PostMapping("selectAllMyBuyGoods")
    @ResponseBody
    public JSONObject selectAllMyBuyGoods(String openID, Integer currentPage) {

        JSONObject json = new JSONObject();
        List<OrderCustom> mybuylist = null;
        try {
            if (currentPage != null) {//查询我买的商品信息
                mybuylist = goodsService.selectAllMyBuyGoods(openID, (currentPage - 1) * 10);
            } else {
                mybuylist = goodsService.selectAllMyBuyGoods(openID, 0);
            }
        } catch (Exception e) {
            logger.error("selectAllMyBuyGoods--> error:{}:", e);
        }
        if (mybuylist != null && mybuylist.size() != 0) {//假如查询到我买的商品返回
            json.put("mybuylist", mybuylist);
            return json;
        } else {//没查询到我买的商品返回空
            json.put("mybuylist", null);
            return json;
        }
    }

    //我发布的商品信息
    @PostMapping("selectAllMyPublishGoods")
    @ResponseBody
    public JSONObject selectAllMyPublishGoods(String openID, Integer currentPage) {

        JSONObject json = new JSONObject();
        List<Goods> mypublishlist = null;
        try {
            if (currentPage != null) {//查询我发布的商品信息
                mypublishlist = goodsService.selectAllMyPublishGoods(openID, (currentPage - 1) * 10);
            } else {
                mypublishlist = goodsService.selectAllMyPublishGoods(openID, 0);
            }
        } catch (Exception e) {
            logger.error("selectAllMyPublishGoods--> error:{}:", e);
        }
        if (mypublishlist != null && mypublishlist.size() != 0) {//假如查询到我发布的商品返回
            json.put("mypublishlist", mypublishlist);
            return json;
        } else {//没查询到我发布的商品返回空
            json.put("mypublishlist", null);
            return json;
        }
    }

    //上传商品
    @PostMapping("uploadGoods")
    @ResponseBody
    public JSONObject uploadGoods(@ModelAttribute("goods") Goods goods, String[] myfile,
                                  @RequestParam(value = "openID", required = true) String openID) {

        JSONObject json = new JSONObject();
        //获取随机数
        Random rand = new Random();
        //拼接url
        StringBuffer newNames = new StringBuffer();
        // 存储图片的物理路径
        String file_path = "//opt//pic";
        Integer stringSize = myfile.length;
        // 解析Base64
        for (String file : myfile) {//校验
            String dataPrefix;
            String suffix;
            if (file == null || "".equals(file)) {
                json.put("error", "上传失败，上传图片数据为空!");
                json.put("status", false);
                return json;
            } else {
                String[] d = file.split("base64+");
                if (d != null && d.length == 2) {
                    dataPrefix = d[0];////data:img/jpg;base64
                } else {
                    json.put("error", "上传失败，数据不合法!");
                    json.put("status", false);
                    return json;
                }
            }

            if ("data:image/jpeg;".equalsIgnoreCase(dataPrefix)) {//data:image/jpeg;base64,base64编码的jpeg图片数据
                suffix = ".jpg";
            } else if ("data:image/jpg;".equalsIgnoreCase(dataPrefix)) {//data:image/jpeg;base64,base64编码的jpg图片数据
                suffix = ".jpg";
            } else if ("data:image/x-icon;".equalsIgnoreCase(dataPrefix)) {//data:image/x-icon;base64,base64编码的icon图片数据
                suffix = ".ico";
            } else if ("data:image/gif;".equalsIgnoreCase(dataPrefix)) {//data:image/gif;base64,base64编码的gif图片数据
                suffix = ".gif";
            } else if ("data:image/png;".equalsIgnoreCase(dataPrefix)) {//data:image/png;base64,base64编码的png图片数据
                suffix = ".png";
            } else {
                json.put("error", "上传图片格式不合法!");
                json.put("status", false);
                return json;
            }

            // 解析Base64 重新命名
            MultipartFile multipartFile = Base64Util.base64ToMultipart(file);
            //将内存中的数据写入磁盘
            String transName;
            transName = (rand.nextInt(9999999) + 100000) + openID +
                    multipartFile.getName().replaceAll(".+\\.", System.currentTimeMillis() + ".");
            newNames.append(transName + ",");
            // 将内存中的数据写入磁盘
            File newName = new File(file_path + "/" + transName);
            try {
                multipartFile.transferTo(newName);
            } catch (IOException e) {
                logger.error("IOException", e.getMessage());
                json.put("error", "图片存入服务器失败!");
                json.put("status", false);
                return json;
            }
        }
        // 上传图片
        goods.setImage(newNames.toString().substring(0, newNames.toString().length() - 1));
        goods.setStatus(0);
        //获取系统时间
        Date createTime = new java.sql.Date(new java.util.Date().getTime());
        goods.setCreateTime(createTime);
        boolean flag = false;
        flag = goodsService.uploadGoods(goods);
        if (flag == false) {
            json.put("status", false);
            return json;
        }
        json.put("status", true);
        return json;
    }

}
