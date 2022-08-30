package com.atguigu.service;

import com.atguigu.entity.PageResult;
import com.atguigu.entity.QueryPageBean;
import com.atguigu.pojo.Address;

import java.util.List;
import java.util.Map;

/**
 * @author light
 * @create 2022-08-27 16:19
 */
public interface AddressService {
    List<Address> findAll();

    PageResult findPage(QueryPageBean queryPageBean);

    void deleteById(Integer id);

    void addAddress(Address address);
}
