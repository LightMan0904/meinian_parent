package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.constant.MessageConstant;
import com.atguigu.entity.PageResult;
import com.atguigu.entity.QueryPageBean;
import com.atguigu.entity.Result;
import com.atguigu.pojo.TravelGroup;
import com.atguigu.service.TravelGroupService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author light
 * @create 2022-08-21 17:56
 */
@RestController
@RequestMapping("/travelGroup")
public class TravelGroupController {
        @Reference
        private TravelGroupService travelGroupService;

        @RequestMapping("/add")
        public Result add(Integer[] travelItemIds, @RequestBody TravelGroup travelGroup){
                try {
                        travelGroupService.add(travelGroup,travelItemIds);
                        return new Result(true, MessageConstant.ADD_TRAVELGROUP_SUCCESS);
                } catch (Exception exception) {
                        exception.printStackTrace();
                        return new Result(false,MessageConstant.ADD_TRAVELGROUP_FAIL);
                }
        }

        /**
         * get请求没有请求体，从请求路径传，用@requestParam接受，
         * post请求有请求体，json格式使用请求体传的，用@requestBody接收
         * @param queryPageBean
         * @return
         */
        @RequestMapping("/findPage")
        public PageResult findPage(@RequestBody QueryPageBean queryPageBean){
                PageResult pageResult = travelGroupService.findPage(queryPageBean);
                return pageResult;
        }

        @RequestMapping("/getById")
        public Result getById(Integer id){
                try {
                        TravelGroup travelGroup =  travelGroupService.getById(id);
                        return new Result(true,MessageConstant.QUERY_TRAVELGROUP_SUCCESS,travelGroup);
                } catch (Exception exception) {
                        exception.printStackTrace();
                        return new Result(false,MessageConstant.QUERY_TRAVELGROUP_FAIL);
                }
        }

        @RequestMapping("/getTravelItemIdsByGroupId")
        public Result getTravelItemIdsByGroupId(Integer travelGroupId){
                try {
                        List<Integer> travelItemIds = travelGroupService.getTravelItemIdsByGroupId(travelGroupId);
                        return new Result(true,"通过跟团游查询自由行Id成功",travelItemIds);
                } catch (Exception exception) {
                        exception.printStackTrace();
                        return new Result(true,"通过跟团游查询自由行Id失败");
                }

        }

        @RequestMapping("/edit")
        public Result edit(@RequestBody TravelGroup travelGroup,Integer[] travelItemIds){
                try {
                        travelGroupService.edit(travelGroup,travelItemIds);
                        return new Result(true,MessageConstant.EDIT_TRAVELGROUP_SUCCESS);
                } catch (Exception exception) {
                        exception.printStackTrace();
                        return new Result(false,MessageConstant.EDIT_TRAVELGROUP_FAIL);
                }
        }

        @RequestMapping("/delete")
        public Result delete(Integer id){
                try {
                        travelGroupService.delete(id);
                        return new Result(true,MessageConstant.DELETE_TRAVELGROUP_SUCCESS);
                }catch (RuntimeException e){
                        return new Result(false,e.getMessage());
                }
                catch (Exception exception) {
                        exception.printStackTrace();
                        return new Result(false,MessageConstant.DELETE_TRAVELGROUP_FAIL);
                }
        }

        @RequestMapping("/findAll")
        public Result findAll(){
                try {
                        List<TravelGroup> travelGroupList = travelGroupService.findAll();
                        return new Result(true,MessageConstant.QUERY_TRAVELGROUP_SUCCESS,travelGroupList);
                } catch (Exception exception) {
                        exception.printStackTrace();
                        return new Result(false,MessageConstant.QUERY_TRAVELGROUP_FAIL);
                }

        }
}
