package com.atguigu.job;

import com.atguigu.constant.RedisConstant;
import com.atguigu.util.QiniuUtils;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.JedisPool;

import java.util.Set;

/**
 * @author light
 * @create 2022-08-23 16:35
 */
public class ClearImgJob {
    @Autowired
    JedisPool jedisPool;

    private void clearImg(){
        //查找redis中无用的图片，
        Set<String> pics = jedisPool.getResource().sdiff(RedisConstant.SETMEAL_PIC_RESOURCES, RedisConstant.SETMEAL_PIC_DB_RESOURCES);
        for (String pic : pics) {
            //删除七牛云里的无用的图片
            QiniuUtils.deleteFileFromQiniu(pic);
            //删除redis中无用的图片
            jedisPool.getResource().srem(RedisConstant.SETMEAL_PIC_RESOURCES,pic);
        }
    }
}
