package com.li.hellospringmybatis.controller;

import com.github.pagehelper.PageInfo;
import com.li.hellospringmybatis.pojo.Emp;
import com.li.hellospringmybatis.service.EmpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class EmpRestController {

    @Autowired
    private EmpService empService;

    @GetMapping("/emps")
    public PageInfo<Emp> getEmps(@RequestParam(name = "page", defaultValue = "1") int pageNum, @RequestParam(name = "size", defaultValue = "5") int pageSize) {
        return empService.getEmps(pageNum, pageSize);
    }
}
