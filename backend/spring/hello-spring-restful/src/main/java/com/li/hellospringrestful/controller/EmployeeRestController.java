package com.li.hellospringrestful.controller;

import com.li.hellospringrestful.bean.Employee;
import com.li.hellospringrestful.bean.Result;
import com.li.hellospringrestful.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/restapi/v1")
// cllow cross origin
@CrossOrigin(origins = "*")
@RestController
public class EmployeeRestController {

    @Autowired
    EmployeeService employeeService;

    @RequestMapping(value = "/employee/{id}", method = RequestMethod.GET)
    public Result getEmployee(@PathVariable int id) {
        return new Result(employeeService.getEmployee(id));
    }

//    @RequestMapping(value = "/employee", method = RequestMethod.POST)
    @PostMapping(value = "/employee")
    public Result addEmployee(@RequestBody Employee employee) {

        employeeService.addEmployee(employee);
        return new Result();
    }

    @RequestMapping(value = "/employee", method = RequestMethod.PUT)
    public Result updateEmployee(@RequestBody Employee employee) {
        employeeService.updateEmployee(employee);
        return new Result();
    }

    @RequestMapping(value = "/employee/{id}", method = RequestMethod.DELETE)
    public Result deleteEmployee(@PathVariable int id) {
        employeeService.deleteEmployee(id);
        return new Result();
    }

    @RequestMapping(value = "/employees", method = RequestMethod.GET)
    public Result getEmployees(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "5") int limit
    ) {
        return new Result(employeeService.getEmployees(page, limit));
    }
}
