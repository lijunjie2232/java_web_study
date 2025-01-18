package com.li.hellospringmybatis;

import com.li.hellospringmybatis.mapper.EmpMapper;
import com.li.hellospringmybatis.mapper.OrderItemMapper;
import com.li.hellospringmybatis.mapper.OrderMapper;
import com.li.hellospringmybatis.mapper.UserMapper;
import com.li.hellospringmybatis.pojo.Emp;
import com.li.hellospringmybatis.pojo.Order;
import com.li.hellospringmybatis.pojo.OrderItem;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;

@SpringBootTest
class HelloSpringMybatisApplicationTests {

	@Autowired
	EmpMapper empMapper;

	@Autowired
	UserMapper userMapper;

	@Autowired
	OrderMapper orderMapper;

	@Autowired
	OrderItemMapper orderItemMapper;

	@Test
	void EmpMapperTest() {
//		get by id test
		System.out.println(empMapper.getEmpById(1));
//		get all test
		List<Emp> emps = empMapper.getEmps();
		System.out.println(emps);
////		add test
//		Emp emp = new Emp("test", 20, new BigDecimal(1000));
//		empMapper.addEmp(emp);
//		System.out.println(emp);
//		System.out.println(emp.getId());
//		emps = empMapper.getEmps();
//		System.out.println(emps);
////		delete test
//		empMapper.deleteEmpById(emp.getId());
//		emps = empMapper.getEmps();
//		System.out.println(emps);
////		update test
//		emp = empMapper.getEmpById(emps.size());
//		System.out.println(emp);
//		emp.setSalary(emp.getSalary().add(new BigDecimal(1000)));
//		empMapper.updateEmp(emp);
//		System.out.println(empMapper.getEmpById(emps.size()));
	}

	@Test
	void UserMapperTest() {
		System.out.println(userMapper.findAllUsers());
	}

	@Test
	void OrderMapperTest() {
//		System.out.println(orderMapper.findAllOrders());
		Order order = orderMapper.findOrderById(1);
		System.out.println(order);
		System.out.println(orderMapper.findOrderByUser(userMapper.findUserById(1)));
	}

	@Test
	void OrderItemMapperTest() {
		System.out.println(orderItemMapper.findAllOrderItems());
	}

	@Test
	void contextLoads() {
	}

}
