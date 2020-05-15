package com.yxx.dao;

import com.yxx.pojo.Category;

import java.util.List;

public interface CategoryMapper {

    /**
     * 获取物品的所有类别
     * @return
     */
    public List<Category> getAllCategory();
}