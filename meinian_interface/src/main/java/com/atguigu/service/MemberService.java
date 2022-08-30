package com.atguigu.service;

import com.atguigu.pojo.Member;

import java.util.List;
import java.util.Map;

/**
 * @author light
 * @create 2022-08-25 21:25
 */
public interface MemberService {
    Member findByTelephone(String telephone);

    void add(Member member);

    Map<String, List> getMemberReport();
}
