package com.atguigu.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.constant.MessageConstant;
import com.atguigu.dao.MemberDao;
import com.atguigu.dao.OrderDao;
import com.atguigu.dao.OrderSettingDao;
import com.atguigu.dao.SetmealDao;
import com.atguigu.entity.Result;
import com.atguigu.pojo.Member;
import com.atguigu.pojo.Order;
import com.atguigu.pojo.OrderSetting;
import com.atguigu.service.OrderService;
import com.atguigu.util.DateUtils;
import com.atguigu.util.HttpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author light
 * @create 2022-08-24 20:08
 */
@Service(interfaceClass = OrderService.class)
@Transactional
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private MemberDao memberDao;

    @Autowired
    private OrderSettingDao orderSettingDao;

    @Override
    public Result saveOrder(Map map) throws Exception {
        //1. 判断前端选择的那一天是否开团
        String date = (String) map.get("orderDate");
        Date orderDate = DateUtils.parseString2Date(date);
        OrderSetting orderSetting =  orderSettingDao.findByOrderDate(orderDate);
        if(orderSetting == null){
            return new Result(false, MessageConstant.SELECTED_DATE_CANNOT_ORDER);
        }
        //2.判断那一天的团是否还有剩余
        else {
            int number = orderSetting.getNumber();
            int reservations = orderSetting.getReservations();
            if(reservations >= number){
                return new Result(false,MessageConstant.ORDER_FAIL);
            }

        }
        //3.判断用户是否是会员
        String telephone = (String) map.get("telephone");
        Member member =  memberDao.findByTelephone(telephone);
        if(member != null){
            //3.1判断会员在当天是否已经预约了该套餐
            Order order = new Order();
            Integer memberId = member.getId();
            Integer setmealId = Integer.parseInt((String) map.get("setmealId"));
            order.setMemberId(memberId);
            order.setOrderDate(orderDate);
            order.setSetmealId(setmealId);
            List<Order> orderList = orderDao.findByOrderParam(order);
            if (orderList != null && orderList.size() > 0){
                return new Result(false,MessageConstant.HAS_ORDERED);
            }
        }
            //3.2快速注册
        else{
           member = new Member();
           member.setName((String) map.get("name"));
           member.setSex((String) map.get("sex"));
           member.setPhoneNumber(telephone);
           member.setIdCard((String) map.get("idCard"));
           member.setRegTime(new Date());
           memberDao.add(member);//主键回填
        }
        //4.更新预约设置的预约数
        orderSetting.setReservations(orderSetting.getReservations()+1);
        orderSettingDao.editReservationsByOrderDate(orderSetting);

        //5.保存预约
        Order order = new Order();
        order.setMemberId(member.getId());
        order.setOrderDate(orderDate);
        order.setOrderType(Order.ORDERTYPE_WEIXIN);
        order.setOrderStatus(Order.ORDERSTATUS_NO);
        order.setSetmealId(Integer.parseInt((String) map.get("setmealId")));
        orderDao.add(order);    //主键回填
        return new Result(true,MessageConstant.ORDER_SUCCESS,order);
    }

    @Override
    public Map findById4Detail(Integer id) {
        Map<String,Object> resultMap = orderDao.findById4Detail(id);
        if(resultMap != null){
             Date orderDate = (Date) resultMap.get("orderDate");
             resultMap.put("orderDate",orderDate);
        }
        return resultMap;
    }
}
