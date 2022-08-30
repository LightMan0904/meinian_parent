package com.atguigu.dao;

import com.atguigu.pojo.Setmeal;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author light
 * @create 2022-08-22 15:03
 */
public interface SetmealDao {
    void add(Setmeal setmeal);

    void setSetmealIdAndTravelGroupIds(Map<String, Integer> map);

    Page<Setmeal> findPage(@Param("queryString") String queryString);

    List<Setmeal> findAll();

    Setmeal findById(Integer id);

    List<Map> findSetmealList();
}
