package com.atguigu.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.constant.RedisConstant;
import com.atguigu.dao.SetmealDao;
import com.atguigu.entity.PageResult;
import com.atguigu.entity.QueryPageBean;
import com.atguigu.pojo.Setmeal;
import com.atguigu.service.SetmealService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.JedisPool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author light
 * @create 2022-08-22 15:02
 */
@Service(interfaceClass = SetmealService.class)
@Transactional
public class SetmealServiceImpl implements SetmealService {

    @Autowired
    private SetmealDao setmealDao;
    @Autowired
    JedisPool jedisPool;

    @Override
    public void add(Setmeal setmeal, Integer[] travelGroupIds) {
        setmealDao.add(setmeal);
        //添加套餐，主键回填，获取刚刚添加套餐的id
        Integer setmealId = setmeal.getId();
        setSetmealIdAndTravelGroupIds(setmealId,travelGroupIds);

        //将图片名称保存到Redis
        jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_DB_RESOURCES,setmeal.getImg());
    }

    @Override
    public PageResult findPage(QueryPageBean queryPageBean) {
        //开启分页插件
        PageHelper.startPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize());
        //条件查询返回Page对象
        Page<Setmeal> page = setmealDao.findPage(queryPageBean.getQueryString());
        return new PageResult(page.getTotal(),page.getResult());
    }

    @Override
    public List<Setmeal> findAll() {
        return setmealDao.findAll();
    }

    @Override
    public Setmeal findById(Integer id) {
        return setmealDao.findById(id);
    }

    @Override
    public Map<String, Object> getSetmealReport() {
        //按套餐分组，查找套餐名称和预约数量
        List<Map> setmealList = setmealDao.findSetmealList();
        List<String> setmealNames = new ArrayList<>();
        for (Map map : setmealList) {
            String setmealName = (String) map.get("name");
            setmealNames.add(setmealName);
        }
        Map<String,Object> map = new HashMap<>();
        map.put("setmealNames",setmealNames);
        map.put("setmealCount",setmealList);
        return map;
    }

    private void setSetmealIdAndTravelGroupIds(Integer setmealId, Integer[] travelGroupIds) {
        Map<String,Integer> map = new HashMap<>();
        map.put("setmealId",setmealId);
        for (Integer travelGroupId : travelGroupIds) {
            map.put("travelGroupId",travelGroupId);
            setmealDao.setSetmealIdAndTravelGroupIds(map);
        }
    }
}
