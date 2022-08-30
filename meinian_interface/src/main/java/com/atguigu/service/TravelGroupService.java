package com.atguigu.service;

import com.atguigu.entity.PageResult;
import com.atguigu.entity.QueryPageBean;
import com.atguigu.pojo.TravelGroup;

import java.util.List;

/**
 * @author light
 * @create 2022-08-21 17:57
 */
public interface TravelGroupService {
    void add(TravelGroup travelGroup, Integer[] travelItemIds);

    PageResult findPage(QueryPageBean queryPageBean);

    TravelGroup getById(Integer id);

    List<Integer> getTravelItemIdsByGroupId(Integer travelGroupId);

    void edit(TravelGroup travelGroup, Integer[] travelItemIds);

    void delete(Integer id);

    List<TravelGroup> findAll();
}
