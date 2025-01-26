package com.li.hello_spring_practice1.bean;

import com.li.hello_spring_practice1.annotation.Gender;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "employee")
public class Employee {
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
    @Schema(description = "id")
    private Integer id;

    @NotBlank
    @Schema(description = "name")
    private String name;

    @Min(value = 0, message = "age could smaller than 0")
    @Schema(description = "age")
    private Integer age;

    @Email(message = "email format error")
    @Schema(description = "email")
    private String email;

//    @Max(value = 1, message = "gender could only be 0 or 1")
//    @Min(value = 0, message = "gender could only be 0 or 1")
    @Gender(message = "{gender.invalid.message}")
    @Schema(description = "gender")
    private Integer gender;

    @NotBlank
    @Schema(description = "address")
    private String address;

    @DecimalMin(value = "0.00", message = "salary could not smaller than 0")
    @Schema(description = "salary")
    private BigDecimal salary;
}
