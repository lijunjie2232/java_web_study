package com.li.hello_spring_practice1.controller;

import com.li.hello_spring_practice1.bean.Employee;
import com.li.hello_spring_practice1.bean.Result;
import com.li.hello_spring_practice1.service.EmployeeService;
import com.li.hello_spring_practice1.vo.req.EmployeeAddVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/restapi/v1")
// cllow cross origin
@CrossOrigin(origins = "*")
@Tag(name = "Employee", description = "Employee API")
public class EmployeeRestController {

    @Autowired
    EmployeeService employeeService;

    @Operation(summary = "get user by id")
    @Parameters(
            {
                    @Parameter(name = "id", description = "employee id", in = ParameterIn.PATH, required = true)
            }
    )
    @RequestMapping(value = "/employee/{id}", method = RequestMethod.GET)
    public Result getEmployee(@PathVariable int id) {
        return new Result(employeeService.getEmployee(id));
    }

    //    @RequestMapping(value = "/employee", method = RequestMethod.POST)
    @Operation(summary = "add user")
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

    // status exceotion handle test
    @RequestMapping(value = "/employee/status", method = RequestMethod.GET)
    public Result getEmployeeStatus(@RequestParam(value = "id", defaultValue = "0") int id) {
        int i = 100 / id;
        return Result.ok(i);
    }

    @ExceptionHandler(ArithmeticException.class)
    public Result handleArithmeticException(ArithmeticException e) {
        System.out.println("[EmployeeRestController]: " + e.getMessage());
        return new Result(500, e.getMessage());
    }

    @RequestMapping(value = "/employee/valtest", method = RequestMethod.GET)
    public Result getEmployeeValTest(
            @RequestBody @Validated EmployeeAddVo employee
    ) {
        return Result.ok(employee);
    }

}
