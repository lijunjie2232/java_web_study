package com.li.hellospringmybatis;

import com.li.hellospringmybatis.mapper.*;
import com.li.hellospringmybatis.pojo.*;
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

    @Autowired
    OrderUserMapper orderUserMapper;

    @Autowired
    EmpDynamicMapper empDynamicMapper;

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
    void OrderUserMapperTest() {
        User user = orderUserMapper.getUserById(1);
//		List<Order> orders = orderUserMapper.getOrderByUser(user.getId());
//		List<OrderItem> orderItems = orderUserMapper.getOrderItemByOrder(orders.get(0).getId());
//		Goods goods = orderUserMapper.getGoodsById(1);
//		System.out.println(user);
//		System.out.println(orders);
//		System.out.println(orderItems);
//		System.out.println(goods);
//		System.out.println(orderUserMapper.getUserAndOrderByStep(user.getId()));
        List<Order> orders = orderUserMapper.getUserAndOrderByStep(user.getId());
        System.out.println(orders.get(0).getId());
        System.out.println(orders.get(0).getOrderItems());
    }

    @Test
    void OrderItemMapperTest() {
        System.out.println(orderItemMapper.findAllOrderItems());
    }

    @Test
    void EmpDynamicMapperTest() {
//        System.out.println(empDynamicMapper.getEmpByNameAndSalary(null, BigDecimal.valueOf(100), null));
//        empDynamicMapper.updateEmpByNameAndSalary(new Emp(1, null, null, BigDecimal.valueOf(10000)));
//        System.out.println(empDynamicMapper.getEmpByNameAndSalaryByTrim(null, BigDecimal.valueOf(100), null));
//        System.out.println(empDynamicMapper.getEmpByNameAndSalaryChoose("test", BigDecimal.valueOf(100), null));
//        System.out.println(empDynamicMapper.getEmpByNameAndSalaryChoose(null, BigDecimal.valueOf(100), null));

    }

    @Test
    void contextLoads() {
    }

}
