package com.li.hellospringmybatis;

import com.li.hellospringmybatis.mapper.EmpMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class HelloSpringMybatisApplicationTests {

	@Autowired
	EmpMapper empMapper;

	@Test
	void testEmpMapper() {
		System.out.println(empMapper.getEmpById(1));
	}
	@Test
	void contextLoads() {
	}

}
