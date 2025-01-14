package com.li.hellospringmybatis.mapper;

import com.li.hellospringmybatis.pojo.Emp;
import org.springframework.web.bind.annotation.Mapping;

@Mapping
public interface EmpMapper {
    public Emp getEmpById(Integer id);
}
