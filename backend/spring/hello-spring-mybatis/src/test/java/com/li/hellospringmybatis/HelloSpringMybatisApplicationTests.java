package com.li.hellospringmybatis;

import com.li.hellospringmybatis.mapper.EmpMapper;
import com.li.hellospringmybatis.pojo.Emp;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;

@SpringBootTest
class HelloSpringMybatisApplicationTests {

	@Autowired
	EmpMapper empMapper;

	@Test
	void EmpMapperTest() {
//		get by id test
		System.out.println(empMapper.getEmpById(1));
////		get all test
//		List<Emp> emps = empMapper.getEmps();
//		System.out.println(emps);
////		add test
//		empMapper.addEmp(new Emp("小明", 18, new BigDecimal(10000)));
//		emps = empMapper.getEmps();
//		System.out.println(emps);
////		delete test
//		empMapper.deleteEmpById(emps.size());
//		emps = empMapper.getEmps();
//		System.out.println(emps);
////		update test
//		Emp emp = empMapper.getEmpById(emps.size());
//		System.out.println(emp);
//		emp.setSalary(emp.getSalary().add(new BigDecimal(1000)));
//		empMapper.updateEmp(emp);
//		System.out.println(empMapper.getEmpById(emps.size()));
	}

	@Test
	void contextLoads() {
	}

}
