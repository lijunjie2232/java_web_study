package com.li.schedule.dao;

import com.li.schedule.pojo.SysSchedule;
import com.li.schedule.pojo.SysUser;

import java.util.List;

/**
 * sys_schedule operations
 * author: @lijunjie2232
 */
public interface SysScheduleDao {

    /**
     * get all schedules
     *
     * @return all schedule in list
     */
    List<SysSchedule> getAllSysSchedule();

    /**
     * get specific scheudle
     *
     * @param sid: schedule id
     * @return SysSchedule object
     */
    SysSchedule getSysScheduleBySid(int sid);

    /**
     * get schedules of specified user
     *
     * @param user: SysUser object
     * @return schedule in list
     */
    List<SysSchedule> getSysScheduleByUser(SysUser user);

    /**
     * get schedule by uid
     *
     * @param uid: sys_user uid
     * @return schedule in list
     */
    List<SysSchedule> getSysScheduleByUid(int uid);

    /**
     * get completed schedule by uid
     *
     * @param uid: sys_user uid
     * @return schedule in list
     */
    List<SysSchedule> getCompletedSysScheduleByUid(int uid);

    /**
     * get uncompleted schedule by uid
     *
     * @param uid: sys_user uid
     * @return schedule in list
     */
    List<SysSchedule> getUncompletedSysScheduleByUid(int uid);

    /**
     * add schedule to table sys_schedule
     *
     * @param schedule: SysSchedule object
     * @return SysSchedule object with sid
     */
    SysSchedule addSchedule(SysSchedule schedule);

    /**
     * set a schedule to complete
     *
     * @param schedule: SysSchedule obejct
     * @return affect row
     */
    int completeSchedule(SysSchedule schedule);

    /**
     * update schedule info
     * @param schedule
     * @return
     */
    int updateSchedule(SysSchedule schedule);
    /**
     * set a schedule to uncomplete
     *
     * @param schedule: SysSchedule obejct
     * @return affect row
     */
    int removeSchedule(SysSchedule schedule);
}
