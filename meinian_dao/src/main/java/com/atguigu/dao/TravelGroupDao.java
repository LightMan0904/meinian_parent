package com.atguigu.dao;

import com.atguigu.entity.QueryPageBean;
import com.atguigu.pojo.TravelGroup;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author light
 * @create 2022-08-21 17:58
 */
public interface TravelGroupDao {


    void add(TravelGroup travelGroup);

    void setTravelGroupAndItems(Map<String, Integer> map);

    Page<TravelGroup> findPage(@Param("queryString") String queryString);

    TravelGroup getById(Integer id);

    List<Integer> getTravelItemIdsByGroupId(Integer id);

    void edit(TravelGroup travelGroup);

    void deleteTravelItemByGroupId(Integer id);

    void delete(Integer id);

    List<TravelGroup> findAll();

    List<TravelGroup> findTravelGroupBySetmealId(Integer setmealId);
}
