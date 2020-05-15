package com.yxx.controller;

import com.alibaba.fastjson.JSONObject;
import com.yxx.pojo.Goods;
import com.yxx.pojo.GoodsCustom;
import com.yxx.service.GoodsService;
import com.yxx.util.Base64Util;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Random;

@Controller
public class AlterController {

    private static Logger logger = LoggerFactory.getLogger(AlterController.class);
    @Autowired
    private GoodsService goodsService;

    //降价,编辑 按钮(根据商品id和openID等编辑商品信息)
    @PostMapping("/updateGoodsByGoods")
    @ResponseBody
    public JSONObject updateGoodsByGoods(@ModelAttribute("goods") Goods goods,
                                         @RequestParam(value = "myfile", required = false) String[] myfile,
                                         @RequestParam(value = "openID") String openID,
                                         @RequestParam(value = "theKey") Integer theKey,
                                         @RequestParam(value = "imageStrings", required = false) String[] imageStrings)
            throws IllegalStateException, IOException {

        JSONObject json = new JSONObject();
        boolean result = false;
        if (theKey == 1) {
            // 存储图片的物理路径
            String file_path = "//opt//pic";
            boolean b = myfile != null && myfile.length > 0 && theKey == 1 && imageStrings != null && imageStrings.length > 0;
            //判断图片名字是否改变过
            //查询数据库中图片名
            GoodsCustom goodsmessage = null;
            try {
                goodsmessage = goodsService.selectOneGoodsByGoodsId(goods);
            } catch (Exception e) {
                logger.error("selectOneGoodsByGoodsId--> error:{}:", e);
            }
            String[] oldGoodsImage = goodsmessage.getGoodsImage();//数据库中图片名
            //对比本次图片名改变是否只是顺序改变
            if (imageStrings.length == oldGoodsImage.length) {//假如图片名个数一样
                int size = imageStrings.length;//记录图片个数
                int count = 0;//记录匹配成功次数
                for (int i = 0; i < size; i++) {
                    for (int j = 0; j < size; j++) {
                        if (imageStrings[i].equals(oldGoodsImage[j])) {
                            count += 1;
                        }
                    }
                }
                if (count == size) {//假如匹配成功次数等于图片个数则图片没换
                    //获取系统时间
                    Date createTime = new java.sql.Date(new java.util.Date().getTime());
                    goods.setCreateTime(createTime);
                    goods.setGoodsImage(imageStrings);
                    try {
                        result = goodsService.updateGoodsByGoods(goods);
                    } catch (Exception e) {
                        logger.error("updateGoodsByGoods--> error:{}:", e);
                    }
                } else {//假如匹配成功次数不等于图片个数则图片换了 上传图片
                    result = upLoadImageAndDeteleImage(goods, myfile, openID, logger, json, result, b, oldGoodsImage, file_path);
                }
            } else {//假如图片名个数不一样 上传图片
                result = upLoadImageAndDeteleImage(goods, myfile, openID, logger, json, result, b, oldGoodsImage, file_path);
            }
        } else {
            //图片没换
            //获取系统时间
            Date createTime = new java.sql.Date(new java.util.Date().getTime());
            goods.setCreateTime(createTime);
            try {
                result = goodsService.updateGoodsByGoods(goods);
            } catch (Exception e) {
                logger.error("updateGoodsByGoods--> error:{}:", e);
            }
        }
        if (result) {
            json.put("status", true);
        } else {
            json.put("status", false);
        }
        return json;
    }

    private boolean upLoadImageAndDeteleImage(@ModelAttribute("goods") Goods goods, String[] myfile, @RequestParam("openID") String openID, Logger logger, JSONObject json, boolean result, boolean b, String[] oldGoodsImage, String file_path) {
        if (upGoodsImages(goods, myfile, openID, logger, json, b, file_path)) {
            //新图片上传成功 删除旧图片
            for (String image : oldGoodsImage) {
                File file = new File(file_path + "/" + image);
                System.gc();
                file.delete();
            }
            try {//更新商品
                result = goodsService.updateGoodsByGoods(goods);
            } catch (Exception e) {
                logger.error("updateGoodsByGoods--> error:{}:", e);
            }
        }
        return result;
    }

    private boolean upGoodsImages(@ModelAttribute("goods") Goods goods, String[] myfile, @Param("openID") String openID, Logger logger, JSONObject json, boolean b, String file_path) {
        if (b) {
            //获取随机数
            Random rand = new Random();
            //拼接url
            StringBuffer newNames = new StringBuffer();
            // 解析Base64
            for (String file : myfile) {//校验
                String dataPrefix;
                String suffix;
                if (file == null || "".equals(file)) {
                    json.put("error", "上传失败，上传图片数据为空!");
                    json.put("status", false);
                    return false;
                } else {
                    String[] d = file.split("base64+");
                    if (d != null && d.length == 2) {
                        dataPrefix = d[0];////data:img/jpg;base64
                    } else {
                        json.put("error", "上传失败，数据不合法!");
                        json.put("status", false);
                        return false;
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
                    return false;
                }
                // 解析Base64 重新命名
                MultipartFile multipartFile = Base64Util.base64ToMultipart(file);
                //将内存中的数据写入磁盘
                String transName;
                transName = (rand.nextInt(9999999) + 100000) + openID + multipartFile.getOriginalFilename().replaceAll(".+\\.", System.currentTimeMillis() + ".");
                newNames.append(transName + ",");
                // 将内存中的数据写入磁盘
                File newName = new File(file_path + "/" + transName);
                try {
                    multipartFile.transferTo(newName);
                } catch (IOException e) {
                    logger.error("IOException", e.getMessage());
                    json.put("error", "图片存入服务器失败!");
                    json.put("status", false);
                    return false;
                }
            }
            goods.setImage(newNames.toString().substring(0, newNames.toString().length() - 1));
        }
        return true;
    }
}
