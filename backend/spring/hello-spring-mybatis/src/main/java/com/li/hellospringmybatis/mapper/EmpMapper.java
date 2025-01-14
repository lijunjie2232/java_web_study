package com.li.hellospringmybatis.mapper;

import org.springframework.web.bind.annotation.Mapping;

@Mapping
public interface EmpMapper {
    Emp getEmpById(Integer id);
}
