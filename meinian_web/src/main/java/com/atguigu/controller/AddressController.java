package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.constant.MessageConstant;
import com.atguigu.entity.PageResult;
import com.atguigu.entity.QueryPageBean;
import com.atguigu.entity.Result;
import com.atguigu.pojo.Address;
import com.atguigu.service.AddressService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author light
 * @create 2022-08-27 16:18
 */
@RestController
@RequestMapping("/address")
public class AddressController {

    @Reference
    private AddressService addressService;

    @RequestMapping("/findAllMaps")
    private Map findAllMaps(){
        Map map=new HashMap();
        List<Address> addressList = addressService.findAll();

        //1、定义分店坐标集合
        List<Map> gridnMaps=new ArrayList<>();
        //2、定义分店名称集合
        List<Map> nameMaps=new ArrayList();
        for (Address address : addressList) {
            Map gridnMap=new HashMap();
            // 获取经度
            gridnMap.put("lng",address.getLng());
            // 获取纬度
            gridnMap.put("lat",address.getLat());
            gridnMaps.add(gridnMap);

            Map nameMap=new HashMap();
            // 获取地址的名字
            nameMap.put("addressName",address.getAddressName());
            nameMaps.add(nameMap);
        }
        // 存放经纬度
        map.put("gridnMaps",gridnMaps);
        // 存放名字
        map.put("nameMaps",nameMaps);
        return map;
    }

    @RequestMapping("/findPage")
    private Result findPage(@RequestBody QueryPageBean queryPageBean){
        try {
            PageResult pageResult = addressService.findPage(queryPageBean);
            return new Result(true,MessageConstant.QUERY_ADDRESS_SUCCESS,pageResult);
        } catch (Exception exception) {
            exception.printStackTrace();
            return new Result(false,MessageConstant.QUERY_ADDRESS_FAIL);
        }
    }

    @RequestMapping("/deleteById")
    private Result deleteById(Integer id){
        try {
            addressService.deleteById(id);
            return new Result(true,MessageConstant.DELETE_ADDRESS_SUCCESS);
        } catch (Exception exception) {
            exception.printStackTrace();
            return new Result(false,MessageConstant.DELETE_ADDRESS_FAIL);
        }
    }

    @RequestMapping("/addAddress")
    private Result addAddress(@RequestBody Address address){
        try {
            addressService.addAddress(address);
            return new Result(true,MessageConstant.ADD_ADDRESS_SUCCESS);
        } catch (Exception exception) {
            exception.printStackTrace();
            return new Result(false,MessageConstant.ADD_ADDRESS_FAIL);
        }
    }
}
