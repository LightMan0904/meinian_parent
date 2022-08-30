package com.atguigu.service;

import com.atguigu.entity.PageResult;
import com.atguigu.entity.QueryPageBean;
import com.atguigu.pojo.Setmeal;

import java.util.List;
import java.util.Map;

/**
 * @author light
 * @create 2022-08-22 15:02
 */
public interface SetmealService {
    void add(Setmeal setmeal, Integer[] travelGroupIds);

    PageResult findPage(QueryPageBean queryPageBean);

    List<Setmeal> findAll();

    Setmeal findById(Integer id);

    Map<String, Object> getSetmealReport();
}
