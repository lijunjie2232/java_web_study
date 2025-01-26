package com.li.hello_spring_practice1.bean;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "统一返回结果")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result {
    private int code = 200;
    private String msg = "ok";
    private Object data = null;

    public Result(Object data) {
        this.data = data;
    }

    public Result(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static Result ok() {
        return new Result();
    }

    public static Result ok(Object data) {
        return new Result(data);
    }

    public static Result error(int code, String msg) {
        return new Result(code, msg);
    }
}
