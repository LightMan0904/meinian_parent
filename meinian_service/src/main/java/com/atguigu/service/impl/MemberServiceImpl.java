package com.atguigu.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.dao.MemberDao;
import com.atguigu.pojo.Member;
import com.atguigu.service.MemberService;
import com.atguigu.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author light
 * @create 2022-08-25 21:25
 */
@Service(interfaceClass = MemberService.class)
@Transactional
public class MemberServiceImpl implements MemberService{

    @Autowired
    private MemberDao memberDao;

    @Override
    public Member findByTelephone(String telephone) {
        return memberDao.findByTelephone(telephone);
    }

    @Override
    public void add(Member member) {
        memberDao.add(member);
    }

    @Override
    public Map<String, List> getMemberReport() {
        Map<String,List> map = new HashMap<>();
        //获取当天年月，偏移量-12，从1年前开始循环，添加到月集合中
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH,-12);
        List<String> months = new ArrayList();
        for (int i = 0; i < 12; i++) {
            calendar.add(Calendar.MONTH,1);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM");
            String month = simpleDateFormat.format(calendar.getTime());
            months.add(month);
        }
        map.put("months",months);

        List<Integer> memberCount = new ArrayList<>();
        for (String month : months) {
            String lastDayOfMonth = DateUtils.getLastDayOfMonth(month);
            Integer memberCount4Month = memberDao.findMemberCount(lastDayOfMonth);
            memberCount.add(memberCount4Month);
        }
        map.put("memberCount",memberCount);
        return map;
    }
}
