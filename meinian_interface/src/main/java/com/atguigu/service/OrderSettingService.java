package com.atguigu.service;

import com.atguigu.pojo.OrderSetting;

import java.util.List;

/**
 * @author light
 * @create 2022-08-23 19:27
 */
public interface OrderSettingService {
    void addBatch(List<OrderSetting> dataList);
}
