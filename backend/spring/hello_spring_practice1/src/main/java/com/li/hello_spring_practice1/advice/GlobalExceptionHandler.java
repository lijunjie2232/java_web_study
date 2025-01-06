package com.li.hello_spring_practice1.advice;

import com.li.hello_spring_practice1.bean.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ResponseBody
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ArithmeticException.class)
    public Result handleException(Exception e) {
        System.out.println("[GlobalExceptionHandler]: " + e.getMessage());
        return Result.error(500, e.getMessage());
    }
}
