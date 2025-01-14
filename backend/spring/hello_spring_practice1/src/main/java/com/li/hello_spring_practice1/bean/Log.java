package com.li.hello_spring_practice1.bean;

import com.li.hello_spring_practice1.annotation.Gender;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "log")
public class Log {
    /*
    for sql table:
    CREATE TABLE `login`
    (
        `id`       int(11)      NOT NULL AUTO_INCREMENT,
        `username` varchar(255) NOT NULL,
        `time`     datetime DEFAULT CURRENT_TIMESTAMP,
        PRIMARY KEY (`id`)
    );
     */
    @Schema(description = "id", example = "1")
    private int id;
    @Schema(description = "username", example = "li")
    private String username;
    @Schema(description = "time", example = "2023-05-05 12:12:12")
    private Date time;
}
