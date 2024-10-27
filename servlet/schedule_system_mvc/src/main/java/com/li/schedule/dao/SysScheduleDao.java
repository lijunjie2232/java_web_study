package com.li.schedule.dao;

import com.li.schedule.pojo.SysSchedule;

/**
 * sys_schedule operations
 * author: @lijunjie2232
 */
public interface SysScheduleDao {

    /**
     * add schedule to table sys_schedule
     * @param schedule SysSchedule object
     * @return affect row == 0 means not succeed yet
     */
    int addSchedule(SysSchedule schedule);
}
