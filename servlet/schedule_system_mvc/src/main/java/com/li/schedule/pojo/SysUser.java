package com.li.schedule.pojo;

import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
// @Getter
// @Setter
// @EqualsAndHashCode
// @ToString
@Data//@Data = @Getter + @Setter + @EqualsAndHashCode + @ToString
public class SysUser implements Serializable {

    private Integer uid;
    private String username;
    private String password;

}
