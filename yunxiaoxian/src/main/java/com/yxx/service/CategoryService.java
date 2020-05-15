package com.yxx.service;

import com.yxx.pojo.Category;

import java.util.List;

public interface CategoryService {

    /**
     * 获取物品的所有类别
     * @return
     */
    public List<Category> getAllCategory();
}