package com.atguigu.dao;

import com.atguigu.pojo.OrderSetting;

import java.util.Date;

/**
 * @author light
 * @create 2022-08-23 19:28
 */
public interface OrderSettingDao {
    void add(OrderSetting orderSetting);

    int checkOrderSettingByDate(Date orderDate);

    void edit(OrderSetting orderSetting);

    OrderSetting findByOrderDate(Date orderDate);

    void editReservationsByOrderDate(OrderSetting orderSetting);
}
