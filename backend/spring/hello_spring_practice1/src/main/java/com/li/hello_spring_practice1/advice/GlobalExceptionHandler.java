package com.li.hello_spring_practice1.advice;

import com.li.hello_spring_practice1.bean.Result;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@ResponseBody
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        Map<String, String> errorMap = new HashMap<>();
        for (var fieldError : e.getBindingResult().getFieldErrors())
            errorMap.put(fieldError.getField(), fieldError.getDefaultMessage());
        return new Result(500, "bad parameters", errorMap);
    }

    @ExceptionHandler(Exception.class)
    public Result handleException(Exception e) {
        System.out.println("[GlobalExceptionHandler]: " + e.getMessage());
        return Result.error(500, e.getMessage());
    }
}
