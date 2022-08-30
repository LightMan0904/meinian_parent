package com.atguigu.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.dao.MemberDao;
import com.atguigu.dao.OrderDao;
import com.atguigu.service.ReportService;
import com.atguigu.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author light
 * @create 2022-08-26 22:02
 */
@Service(interfaceClass = ReportService.class)
@Transactional
public class ReportServiceImpl implements ReportService {

    @Autowired
    private MemberDao memberDao;

    @Autowired
    private OrderDao orderDao;


    @Override
    public Map getBusinessReportData() {
        Map map = null;
        try {
            map = new HashMap();
            String today = DateUtils.parseDate2String(DateUtils.getToday());
            //本周第一天
            String weekMonday = DateUtils.parseDate2String(DateUtils.getThisWeekMonday());
            //本周最后一天
            String weekSunday = DateUtils.parseDate2String(DateUtils.getSundayOfThisWeek());
            //本月第一天
            String monthFirst = DateUtils.parseDate2String(DateUtils.getFirstDay4ThisMonth());
            //本月最后一天
            String monthLast = DateUtils.parseDate2String(DateUtils.getLastDay4ThisMonth());

            System.out.println("weekMonday = " + weekMonday);
            System.out.println("weekSunday = " + weekSunday);
            System.out.println("monthFirst = " + monthFirst);
            System.out.println("monthLast = " + monthLast);

            // （1）今日新增会员数
            int todayNewMember = memberDao.getTodayNewMember(today);
            // （2）总会员数
            int totalMember = memberDao.getTotalMember();
            // （3）本周新增会员数
            Map<String,Object> paramWeekNewMember = new HashMap<String,Object>();
            paramWeekNewMember.put("begin",weekMonday);
            paramWeekNewMember.put("end",weekSunday);
            int thisWeekNewMember = memberDao.getThisWeekAndMonthNewMember(paramWeekNewMember);
            // （4）本月新增会员数
            Map<String,Object> paramMonthNewMember = new HashMap<String,Object>();
            paramMonthNewMember.put("begin",monthFirst);
            paramMonthNewMember.put("end",monthLast);
            int thisMonthNewMember = memberDao.getThisWeekAndMonthNewMember(paramMonthNewMember);

            // （5）今日预约数
            int todayOrderNumber = orderDao.getTodayOrderNumber(today);
            // （6）今日出游数
            int todayVisitsNumber = orderDao.getTodayVisitsNumber(today);
            // （7）本周预约数
            Map<String,Object> paramWeek = new HashMap<String,Object>();
            paramWeek.put("begin",weekMonday);
            paramWeek.put("end",weekSunday);
            int thisWeekOrderNumber = orderDao.getThisWeekAndMonthOrderNumber(paramWeek);
            // （9）本月预约数
            Map<String,Object> paramMonth = new HashMap<String,Object>();
            paramMonth.put("begin",monthFirst);
            paramMonth.put("end",monthLast);
            int thisMonthOrderNumber = orderDao.getThisWeekAndMonthOrderNumber(paramMonth);
            // （8）本周出游数
            Map<String,Object> paramWeekVisit = new HashMap<String,Object>();
            paramWeekVisit.put("begin",weekMonday);
            paramWeekVisit.put("end",weekSunday);
            int thisWeekVisitsNumber = orderDao.getThisWeekAndMonthVisitsNumber(paramWeekVisit);
            // （10）本月出游数
            Map<String,Object> paramMonthVisit = new HashMap<String,Object>();
            paramMonthVisit.put("begin",monthFirst);
            paramMonthVisit.put("end",monthLast);
            int thisMonthVisitsNumber = orderDao.getThisWeekAndMonthVisitsNumber(paramMonthVisit);

            // （11）热门套餐
            List<Map<String,Object>> hotSetmeal = orderDao.findHotSetmeal();

            map.put("reportDate",today);

            map.put("todayNewMember",todayNewMember);
            map.put("totalMember",totalMember);
            map.put("thisWeekNewMember",thisWeekNewMember);
            map.put("thisMonthNewMember",thisMonthNewMember);

            map.put("todayOrderNumber",todayOrderNumber);
            map.put("todayVisitsNumber",todayVisitsNumber);
            map.put("thisWeekOrderNumber",thisWeekOrderNumber);
            map.put("thisWeekVisitsNumber",thisWeekVisitsNumber);
            map.put("thisMonthOrderNumber",thisMonthOrderNumber);
            map.put("thisMonthVisitsNumber",thisMonthVisitsNumber);

            map.put("hotSetmeal",hotSetmeal);
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        return map;
    }
}
