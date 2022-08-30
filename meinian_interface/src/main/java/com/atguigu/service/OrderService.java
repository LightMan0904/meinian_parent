package com.atguigu.service;

import com.atguigu.entity.Result;
import com.atguigu.pojo.Order;

import java.util.Map;

/**
 * @author light
 * @create 2022-08-24 20:07
 */
public interface OrderService {
    Result saveOrder(Map map) throws Exception;

    Map findById4Detail(Integer id);
}
