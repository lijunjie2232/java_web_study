package com.li.schedule.pojo;

import com.li.schedule.dao.SysScheduleDao;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
// @Getter
// @Setter
// @EqualsAndHashCode
// @ToString
@Data//@Data = @Getter + @Setter + @EqualsAndHashCode + @ToString
public class SysSchedule implements Serializable {

    private Integer sid;
    private Integer uid;
    private String title;
    private Integer completed=0;

}
