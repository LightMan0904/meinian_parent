package com.atguigu.dao;

import com.atguigu.pojo.Address;
import com.github.pagehelper.Page;

import java.util.List;

/**
 * @author light
 * @create 2022-08-27 16:19
 */
public interface AddressDao {
    List<Address> findAllMaps();

    Page<Address> findPage(String queryString);

    void deleteById(Integer id);

    void addAddress(Address address);
}
