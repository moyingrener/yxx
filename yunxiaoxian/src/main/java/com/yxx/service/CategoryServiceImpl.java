package com.yxx.service;

import com.yxx.dao.CategoryMapper;
import com.yxx.pojo.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public List<Category> getAllCategory() {

        return categoryMapper.getAllCategory();
    }
}