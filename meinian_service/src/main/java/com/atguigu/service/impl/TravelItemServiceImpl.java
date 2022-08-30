package com.atguigu.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.dao.TravelItemDao;
import com.atguigu.entity.PageResult;
import com.atguigu.entity.QueryPageBean;
import com.atguigu.pojo.TravelItem;
import com.atguigu.service.TravelItemService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author light
 * @create 2022-08-20 16:14
 */
@Service(interfaceClass = TravelItemService.class)    //发布服务，注册到zookeeper
@Transactional
public class TravelItemServiceImpl implements TravelItemService {
        @Autowired
        private TravelItemDao travelItemDao;

        @Override
        public void add(TravelItem travelItem) {
                travelItemDao.add(travelItem);
        }

        @Override
        public PageResult findPage(QueryPageBean queryPageBean) {
                //开启分页插件
                PageHelper.startPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize());
                //dao查询返回Page对象，mybatis底层会转换返回Page对象
                Page<TravelItem> page =  travelItemDao.findPage(queryPageBean.getQueryString());
                //返回总记录数，当前页数据
                return new PageResult(page.getTotal(),page.getResult());
        }

        @Override
        public void delete(Integer id) {
                //判断关联表中，自由行是否被跟团游关联
                int count  = travelItemDao.checkTravelGroupAndItemByItemId(id);
                if(count > 0){
                        throw new RuntimeException("此自由行在中间表中有关联数据，请修改相关跟团游！！！");
                }
                travelItemDao.delete(id);
        }

        @Override
        public TravelItem getById(Integer id) {
                TravelItem travelItem = travelItemDao.getById(id);
                return travelItem;
        }

        @Override
        public void edit(TravelItem travelItem) {
                travelItemDao.edit(travelItem);
        }

        @Override
        public List<TravelItem> findAll() {
                return travelItemDao.findAll();
        }
}
