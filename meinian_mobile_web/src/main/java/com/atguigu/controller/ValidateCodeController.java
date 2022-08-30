package com.atguigu.controller;

import com.atguigu.constant.MessageConstant;
import com.atguigu.constant.RedisMessageConstant;
import com.atguigu.entity.Result;
import com.atguigu.util.SMSUtils;
import com.atguigu.util.ValidateCodeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

/**
 * @author light
 * @create 2022-08-24 19:35
 */
@RestController
@RequestMapping("/validateCode")
public class ValidateCodeController {

    @Autowired
    private JedisPool jedisPool;

    @RequestMapping("/send4Order")
    private Result send4Order(String telephone){
        try {
            //1.生成4位验证码
            String validateCode = ValidateCodeUtils.generateValidateCode4String(4);
            //2.发送短信
            SMSUtils.sendShortMessage(telephone,validateCode);
            //3.把验证码存入redis，生命周期为5分钟
            jedisPool.getResource().setex(telephone+ RedisMessageConstant.SENDTYPE_ORDER,5*60,validateCode);

            System.out.println(telephone + " ---  " +validateCode);
            return new Result(true, MessageConstant.SEND_VALIDATECODE_SUCCESS);
        } catch (Exception exception) {
            exception.printStackTrace();
            return new Result(false,MessageConstant.SEND_VALIDATECODE_FAIL);
        }
    }
    @RequestMapping("/send4Login")
    private Result send4Login(String telephone){
        try {
            //1.生成4位验证码
            String validateCode = ValidateCodeUtils.generateValidateCode4String(4);
            //2.发送短信
            SMSUtils.sendShortMessage(telephone,validateCode);
            //3.把验证码存入redis，生命周期为5分钟
            jedisPool.getResource().setex(telephone+ RedisMessageConstant.SENDTYPE_LOGIN,5*60,validateCode);

            System.out.println(telephone + " ---  " +validateCode);
            return new Result(true, MessageConstant.SEND_VALIDATECODE_SUCCESS);
        } catch (Exception exception) {
            exception.printStackTrace();
            return new Result(false,MessageConstant.SEND_VALIDATECODE_FAIL);
        }
    }
}
