package com.atguigu.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.dao.OrderSettingDao;
import com.atguigu.pojo.OrderSetting;
import com.atguigu.service.OrderSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author light
 * @create 2022-08-23 19:27
 */
@Service(interfaceClass = OrderSettingService.class)
@Transactional
public class OrderSettingServiceImpl implements OrderSettingService {

    @Autowired
    private OrderSettingDao orderSettingDao;

    @Override
    public void addBatch(List<OrderSetting> dataList) {
        for (OrderSetting orderSetting : dataList) {
            //添加时，有可能 2022/08/15 这一天已经有预约数了，数据库已经有值了，再添加其实是在修改数据库中  2022/08/15 的值
            int count = orderSettingDao.checkOrderSettingByDate(orderSetting.getOrderDate());
            //如果有值，则是修改，添加和修改只能做一个
            if(count > 0){
                orderSettingDao.edit(orderSetting);
            }else {
                orderSettingDao.add(orderSetting);
            }
        }
    }
}
