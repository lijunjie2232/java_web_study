package com.li.hellospringmybatis.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.li.hellospringmybatis.mapper.EmpMapper;
import com.li.hellospringmybatis.pojo.Emp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmpService {
    @Autowired
    private EmpMapper empMapper;

    public PageInfo<Emp> getEmps(int pageNum, int pageSize){
        PageHelper.startPage(pageNum, pageSize);
        List<Emp> emps = empMapper.getEmps();
        PageInfo<Emp> pageInfo = new PageInfo<>(emps);
        return pageInfo;
    }
}
