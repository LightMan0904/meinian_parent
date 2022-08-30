package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.constant.MessageConstant;
import com.atguigu.entity.PageResult;
import com.atguigu.entity.QueryPageBean;
import com.atguigu.entity.Result;
import com.atguigu.pojo.TravelItem;
import com.atguigu.service.TravelItemService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author light
 * @create 2022-08-20 16:12
 */
@RestController
@RequestMapping("/travelItem")
public class TravelItemController {
        @Reference
        private TravelItemService travelItemService;

        @RequestMapping("/add")         //请求参数为json格式，所以用@requestbody注释
        public Result add(@RequestBody TravelItem travelItem){
                try {
                        travelItemService.add(travelItem);
                        return new Result(true, MessageConstant.ADD_TRAVELITEM_SUCCESS);
                } catch (Exception exception) {
                        exception.printStackTrace();
                        return new Result(false,MessageConstant.ADD_TRAVELITEM_FAIL);
                }
        }

        @RequestMapping("/findPage")         //请求参数为json格式，所以用@requestbody注释
        public PageResult findPage(@RequestBody QueryPageBean pageParam){
                PageResult pageResult =  travelItemService.findPage(pageParam);
                return pageResult;
        }

        @RequestMapping("/delete")
        public Result delete(Integer id){
                try {
                        travelItemService.delete(id);
                        return new Result(true,MessageConstant.DELETE_TRAVELITEM_SUCCESS);
                }catch (RuntimeException e){
                        return new Result(false,e.getMessage());
                }
                catch (Exception exception) {
                        exception.printStackTrace();
                        return new Result(false,MessageConstant.DELETE_TRAVELITEM_FAIL);
                }
        }

        @RequestMapping("/getById")
        public Result getById(Integer id){
                try {
                        TravelItem travelItem = travelItemService.getById(id);
                        return new Result(true,MessageConstant.QUERY_TRAVELITEM_SUCCESS,travelItem);
                } catch (Exception exception) {
                        exception.printStackTrace();
                        return new Result(false,MessageConstant.QUERY_TRAVELITEM_FAIL);
                }
        }

        @RequestMapping("/edit")
        public Result edit(@RequestBody TravelItem travelItem){
                try {
                        travelItemService.edit(travelItem);
                        return new Result(true,MessageConstant.EDIT_TRAVELITEM_SUCCESS);
                } catch (Exception exception) {
                        exception.printStackTrace();
                        return new Result(false,MessageConstant.EDIT_TRAVELITEM_FAIL);
                }
        }

        @RequestMapping("/findAll")
        public Result findAll(){
                List<TravelItem> travelItemList = travelItemService.findAll();
                return new Result(true,MessageConstant.QUERY_TRAVELITEM_SUCCESS,travelItemList);
        }
}
