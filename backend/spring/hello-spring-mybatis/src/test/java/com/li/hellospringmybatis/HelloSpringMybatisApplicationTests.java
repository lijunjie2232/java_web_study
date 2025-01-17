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
//		get all test
		List<Emp> emps = empMapper.getEmps();
		System.out.println(emps);
//		add test
		Emp emp = new Emp("test", 20, new BigDecimal(1000));
		empMapper.addEmp(emp);
		System.out.println(emp);
		System.out.println(emp.getId());
		emps = empMapper.getEmps();
		System.out.println(emps);
//		delete test
		empMapper.deleteEmpById(emp.getId());
		emps = empMapper.getEmps();
		System.out.println(emps);
//		update test
		emp = empMapper.getEmpById(emps.size());
		System.out.println(emp);
		emp.setSalary(emp.getSalary().add(new BigDecimal(1000)));
		empMapper.updateEmp(emp);
		System.out.println(empMapper.getEmpById(emps.size()));
	}

	@Test
	void contextLoads() {
	}

}
