package com.li.hello_spring_practice1.vo.req;

import com.li.hello_spring_practice1.annotation.Gender;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeAddVo {
    /*
    for sql table:
    CREATE TABLE IF NOT EXISTS `employee`
    (
        `id`      INT(11)        NOT NULL AUTO_INCREMENT,
        `name`    VARCHAR(255)   NOT NULL,
        `age`     INT(11)        NOT NULL,
        `email`   VARCHAR(255)   NOT NULL,
        `gender`  INT1           NOT NULL,
        `address` VARCHAR(255)   NOT NULL,
        `salary`  DECIMAL(10, 2) NOT NULL,
        PRIMARY KEY (`id`) USING BTREE
    );
     */
    @Null
    private Integer id;

    @NotBlank
    private String name;

    @Min(value = 0, message = "age could smaller than 0")
    private Integer age;

    @Email(message = "email format error")
    private String email;

    @Gender(message = "{gender.invalid.message}")
    private Integer gender;

    @NotBlank
    private String address;

    @DecimalMin(value = "0.00", message = "salary could not smaller than 0")
    private BigDecimal salary;
}


