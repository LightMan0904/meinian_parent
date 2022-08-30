package com.atguigu.dao;

import com.atguigu.pojo.Member;

import java.util.List;
import java.util.Map;

/**
 * @author light
 * @create 2022-08-24 20:09
 */
public interface MemberDao {
    Member findByTelephone(String telephone);

    void add(Member member);

    Integer findMemberCount(String lastDayOfMonth);

    int getTodayNewMember(String date);
    int getTotalMember();
    int getThisWeekAndMonthNewMember(Map map);
}
