package com.li.schedule.sqlTest.daoTest;

import com.li.schedule.dao.impl.SysScheduleDaoImpl;
import com.li.schedule.pojo.SysSchedule;
import com.li.schedule.pojo.SysUser;
import org.junit.jupiter.api.Test;

public class SysScheduleDaoImplTest {
    @Test
    public void addScheduleTest() {
        SysScheduleDaoImpl sse = new SysScheduleDaoImpl();
        SysSchedule ss = new SysSchedule(null, 1, "do something else", 0);
        System.out.println(sse.addSchedule(ss));
        System.out.println(sse.getAllSysSchedule());
    }

    @Test
    public void completeScheduleTest() {
        SysScheduleDaoImpl sse = new SysScheduleDaoImpl();
        SysSchedule ss = new SysSchedule(1, 1, "do something", 1);
        System.out.println(sse.completeSchedule(ss));
        System.out.println(sse.uncompleteSchedule(ss));
    }

    @Test
    public void deleteScheduleTest() {
        SysScheduleDaoImpl sse = new SysScheduleDaoImpl();
        SysSchedule ss = new SysSchedule(1, 1, "do something else", 0);
        System.out.println(sse.removeSchedule(ss));
    }

    @Test
    public void queryScheduleTest() {
        SysScheduleDaoImpl sse = new SysScheduleDaoImpl();
        System.out.println(sse.getAllSysSchedule());
        System.out.println(sse.getSysScheduleBySid(1));
        System.out.println(sse.getSysScheduleByUser(new SysUser(1, null, null, null)));
        System.out.println(sse.getSysScheduleByUid(1));
        System.out.println(sse.getCompletedSysScheduleByUid(1));
        System.out.println(sse.getUncompletedSysScheduleByUid(1));
    }
}
