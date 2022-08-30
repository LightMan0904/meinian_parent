package com.atguigu.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.dao.TravelGroupDao;
import com.atguigu.entity.PageResult;
import com.atguigu.entity.QueryPageBean;
import com.atguigu.pojo.TravelGroup;
import com.atguigu.service.TravelGroupService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author light
 * @create 2022-08-21 17:57
 */
@Service(interfaceClass = TravelGroupService.class)
@Transactional
public class TravelGroupServiceImpl implements TravelGroupService {
    @Autowired
    private TravelGroupDao travelGroupDao;


    @Override
    public void add(TravelGroup travelGroup, Integer[] travelItemIds) {
        travelGroupDao.add(travelGroup);
        //使用主键返回，得到刚刚添加的跟团游的id
        Integer travelGroupId = travelGroup.getId();
        setTravelGroupAndItems(travelGroupId,travelItemIds);
    }


    @Override
    public PageResult findPage(QueryPageBean queryPageBean) {
        //开启分页查询插件
        PageHelper.startPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize());
        //通过编码、名称、助记码来条件查询，查询得到的结果封装为Page对象
        Page<TravelGroup> page = travelGroupDao.findPage(queryPageBean.getQueryString());
        return new PageResult(page.getTotal(),page.getResult());
    }

    @Override
    public TravelGroup getById(Integer id) {
        return travelGroupDao.getById(id);
    }

    @Override
    public List<Integer> getTravelItemIdsByGroupId(Integer travelGroupId) {
        return travelGroupDao.getTravelItemIdsByGroupId(travelGroupId);
    }

    @Override
    public void edit(TravelGroup travelGroup, Integer[] travelItemIds) {
        //修改跟团游数据
        travelGroupDao.edit(travelGroup);
        //根据跟团游id删除中间表的数据
        Integer id = travelGroup.getId();
        travelGroupDao.deleteTravelItemByGroupId(id);
        //重新把跟团游id和自由行id添加到中间表
        setTravelGroupAndItems(id,travelItemIds);
    }

    @Override
    public void delete(Integer id) {
        //检查跟团游id是否在中间表中有关联数据
        List<Integer> travelItemIds =  travelGroupDao.getTravelItemIdsByGroupId(id);
        boolean flag = travelItemIds != null && travelItemIds.size() > 0 ? true : false;
        //根据是否有关联数据来进行删除操作
        if(flag){
            throw new RuntimeException("!!! The intermediate table has associated data !!!");
        }else {
            travelGroupDao.delete(id);
        }
    }

    @Override
    public List<TravelGroup> findAll() {
        return travelGroupDao.findAll();
    }

    /**
     * 添加中间表，map中存储跟团游的id和自由行的id，循环添加到表中
     * @param travelGroupId
     * @param travelItemIds
     */
    private void setTravelGroupAndItems(Integer travelGroupId, Integer[] travelItemIds) {
        if(travelItemIds != null && travelItemIds.length>0){
            Map<String,Integer> map = new HashMap<>();
            map.put("travelGroupId",travelGroupId);
            for (Integer travelItemId : travelItemIds) {
                map.put("travelItemId",travelItemId);
                travelGroupDao.setTravelGroupAndItems(map);
            }
        }
    }

}
