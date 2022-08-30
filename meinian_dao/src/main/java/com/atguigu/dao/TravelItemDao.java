package com.atguigu.dao;

import com.atguigu.entity.QueryPageBean;
import com.atguigu.pojo.TravelItem;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author light
 * @create 2022-08-20 16:15
 */
public interface TravelItemDao {

    void add(TravelItem travelItem);

//    mybatis处理单个的普通参数可以用@Param("")注释，也可以用#{value}
    Page<TravelItem> findPage(@Param("queryString") String queryString);

    void delete(Integer id);

    TravelItem getById(Integer id);

    void edit(TravelItem travelItem);

    List<TravelItem> findAll();

    int checkTravelGroupAndItemByItemId(Integer id);

    List<TravelItem> findTravelItemByGroupId(Integer groupId);
}
