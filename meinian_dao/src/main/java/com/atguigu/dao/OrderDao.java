package com.atguigu.dao;

import com.atguigu.pojo.Order;

import java.util.List;
import java.util.Map;

/**
 * @author light
 * @create 2022-08-24 20:08
 */
public interface OrderDao {

    List<Order> findByOrderParam(Order order);

    void add(Order order);

    Map findById4Detail(Integer id);

    int getTodayOrderNumber(String date);
    int getTodayVisitsNumber(String date);
    int getThisWeekAndMonthOrderNumber(Map<String, Object> map);
    int getThisWeekAndMonthVisitsNumber(Map<String, Object> map);
    List<Map<String,Object>> findHotSetmeal();
}
