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
public class SysSchedule implements Serializable, SysScheduleDao {

    private Integer sid;
    private Integer uid;
    private String title;
    private Integer completed;

    @Override
    public int addSchedule(SysSchedule schedule) {
        return 0;
    }
}
