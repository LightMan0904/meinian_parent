package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.constant.MessageConstant;
import com.atguigu.constant.RedisConstant;
import com.atguigu.entity.PageResult;
import com.atguigu.entity.QueryPageBean;
import com.atguigu.entity.Result;
import com.atguigu.pojo.Setmeal;
import com.atguigu.service.SetmealService;
import com.atguigu.util.QiniuUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.JedisPool;

import java.io.IOException;
import java.util.UUID;

/**
 * @author light
 * @create 2022-08-22 15:01
 */
@RestController
@RequestMapping("/setmeal")
public class SetmealController {

    @Reference
    private SetmealService setmealService;
    @Autowired
    private JedisPool jedisPool;

    @RequestMapping("/upload")
    private Result upload(MultipartFile imgFile){
        try {
            String originalFilename = imgFile.getOriginalFilename();//abc.jpg
            int index = originalFilename.lastIndexOf(".");//.的位置
            String suffix = originalFilename.substring(index);//.jpg
            String imageName =  UUID.randomUUID().toString() + suffix;
            //把图片上传到七牛云
            QiniuUtils.upload2Qiniu(imgFile.getBytes(),imageName);
            //把上传图片名称存入redis，基于redis的set集合存储
            jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_RESOURCES,imageName);
            //把图片名字回传，用于显示预览
            return new Result(true, MessageConstant.PIC_UPLOAD_SUCCESS,imageName);
        } catch (IOException e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.PIC_UPLOAD_FAIL);
        }
    }

    @RequestMapping("/add")
    private Result add(Integer[] travelGroupIds, @RequestBody Setmeal setmeal){
        try {
            setmealService.add(setmeal,travelGroupIds);
            return new Result(true,MessageConstant.ADD_SETMEAL_SUCCESS);
        } catch (Exception exception) {
            exception.printStackTrace();
            return new Result(false,MessageConstant.ADD_SETMEAL_FAIL);
        }
    }

    @RequestMapping("/findPage")
    private PageResult findPage(@RequestBody QueryPageBean queryPageBean){
        return setmealService.findPage(queryPageBean);
    }
}
