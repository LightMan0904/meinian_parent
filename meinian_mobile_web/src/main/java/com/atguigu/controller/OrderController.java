package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.constant.MessageConstant;
import com.atguigu.constant.RedisMessageConstant;
import com.atguigu.entity.Result;
import com.atguigu.pojo.Order;
import com.atguigu.service.OrderService;
import com.atguigu.util.ValidateCodeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

import java.util.Map;

/**
 * @author light
 * @create 2022-08-24 20:07
 */
@RestController
@RequestMapping("/order")
public class OrderController {
    @Reference
    private OrderService orderService;

    @Autowired
    private JedisPool jedisPool;

    @RequestMapping("/saveOrder")
    private Result saveOrder(@RequestBody Map map){
        try {
            //判断前端输入的验证码是否合法，与redis中存入的验证码比较
            String validateCode = (String) map.get("validateCode");
            String redisValidateCode = jedisPool.getResource().get((String) map.get("telephone") + RedisMessageConstant.SENDTYPE_ORDER);
            if(validateCode == null ||  !validateCode.equals(redisValidateCode)){
                return new Result(false,MessageConstant.VALIDATECODE_ERROR);
            }
            Result result = orderService.saveOrder(map);
            return result;
        } catch (Exception exception) {
            exception.printStackTrace();
            return new Result(false, MessageConstant.ORDER_FAIL);
        }

    }

    @RequestMapping("/findById")
    private Result findById(Integer id){
        try {
            Map<String,Object> map = orderService.findById4Detail(id);
            return new Result(true,MessageConstant.QUERY_ORDER_SUCCESS,map);
        } catch (Exception exception) {
            exception.printStackTrace();
            return new Result(false,MessageConstant.QUERY_ORDER_FAIL);
        }
    }

}
