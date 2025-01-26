package com.li.schedule.dao.impl;

import com.li.schedule.dao.SysScheduleDao;
import com.li.schedule.pojo.SysSchedule;
import com.li.schedule.pojo.SysUser;

import java.util.List;

public class SysScheduleDaoImpl extends BaseDao implements SysScheduleDao {
    @Override
    public List<SysSchedule> getAllSysSchedule() {
        String sql = "select * from sys_schedule";
        try {
            return this.executeQuery(SysSchedule.class, sql);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public SysSchedule getSysScheduleBySid(int sid) {
        String sql = "select * from sys_schedule WHERE `sid`=?";
        try {
            return this.executeQuery4OneRow(SysSchedule.class, sql, sid);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<SysSchedule> getSysScheduleByUser(SysUser user) {
        String sql = "select * from sys_schedule WHERE `uid`=?";
        try {
            return this.executeQuery(SysSchedule.class, sql, user.getUid());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<SysSchedule> getSysScheduleByUid(int uid) {
        String sql = "select * from sys_schedule WHERE `uid`=?";
        try {
            return this.executeQuery(SysSchedule.class, sql, uid);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<SysSchedule> getCompletedSysScheduleByUid(int uid) {
        String sql = "select * from sys_schedule WHERE `uid`=? and `completed`=1";
        try {
            return this.executeQuery(SysSchedule.class, sql, uid);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<SysSchedule> getUncompletedSysScheduleByUid(int uid) {
        String sql = "select * from sys_schedule WHERE `uid`=? and `completed`=0";
        try {
            return this.executeQuery(SysSchedule.class, sql, uid);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public SysSchedule addSchedule(SysSchedule schedule) {
        String sql = "INSERT INTO `sys_schedule`(`uid`, `title`) VALUES (?,?)";
        try {
            int sid = this.executeInsertWithGenKey(sql, schedule.getUid(), schedule.getTitle());
            schedule.setSid(sid);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return schedule;
    }

    public int completeSchedule(SysSchedule schedule) {
        String sql = "UPDATE `sys_schedule` SET `completed`=1 WHERE `sid`=?";
        try {
            return this.executeUpdate(sql, schedule.getSid());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public int uncompleteSchedule(SysSchedule schedule) {
        String sql = "UPDATE `sys_schedule` SET `completed`=0 WHERE `sid`=?";
        try {
            return this.executeUpdate(sql, schedule.getSid());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public int updateSchedule(SysSchedule schedule) {
        String sql = "UPDATE `sys_schedule` SET `title`=?, `completed`=? WHERE `sid`=?";
        try {
            return this.executeUpdate(sql, schedule.getTitle(), schedule.getCompleted(), schedule.getSid());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public int removeSchedule(SysSchedule schedule) {
        String sql = "DELETE FROM `sys_schedule` WHERE `sid`=?";
        try {
            return this.executeUpdate(sql, schedule.getSid());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
