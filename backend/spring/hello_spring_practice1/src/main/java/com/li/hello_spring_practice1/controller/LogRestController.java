package com.li.hello_spring_practice1.controller;

import com.li.hello_spring_practice1.bean.Employee;
import com.li.hello_spring_practice1.bean.Log;
import com.li.hello_spring_practice1.bean.Result;
import com.li.hello_spring_practice1.dao.LogDao;
import com.li.hello_spring_practice1.service.EmployeeService;
import com.li.hello_spring_practice1.vo.req.EmployeeAddVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/restapi/v1")
// cllow cross origin
@CrossOrigin(origins = "*")
@Tag(name = "Log", description = "Log API")
public class LogRestController {

    @Autowired
    private LogDao logDao;

    @RequestMapping(value = "/log/{id}", method = RequestMethod.GET)
    @Operation(summary = "get Log", description = "get a log by id")
    @Parameters(
            {
                    @Parameter(name = "id", description = "log id", in = ParameterIn.PATH, required = true)
            }
    )
    public Result getLog(@PathVariable int id) {
        return Result.ok(logDao.getLogById(id));
    }

    @RequestMapping(value = "/log", method = RequestMethod.POST)
    @Operation(summary = "Add Log", description = "Add a log")
    public Result addLog(@RequestBody Log log) {
        System.out.println(log);
        return Result.ok();
    }

    @RequestMapping(value = "/logs", method = RequestMethod.GET)
    @Operation(summary = "get Logs", description = "get all logs")
    @Parameters(
            {
                    @Parameter(name = "page", description = "page", in = ParameterIn.QUERY, required = true),
                    @Parameter(name = "size", description = "size", in = ParameterIn.QUERY, required = true)
            }
    )
    public Result getLogs(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "5") int limit
    ) {
        return Result.ok(logDao.getAllLogs((page - 1) * limit, limit));
    }
}
