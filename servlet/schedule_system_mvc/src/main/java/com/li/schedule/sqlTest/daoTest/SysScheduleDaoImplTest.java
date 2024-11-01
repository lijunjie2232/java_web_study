package com.li.schedule.sqlTest.daoTest;

import com.li.schedule.dao.impl.SysScheduleDaoImpl;
import com.li.schedule.pojo.SysSchedule;
import com.li.schedule.pojo.SysUser;
import org.junit.jupiter.api.Test;

public class SysScheduleDaoImplTest {
    @Test
    public void addScheduleTest() {
        SysScheduleDaoImpl sse = new SysScheduleDaoImpl();
        SysSchedule ss1 = new SysSchedule(null, 1, "do something", 0);
        SysSchedule ss2 = new SysSchedule(null, 1, "do something else", 0);
        System.out.println(sse.addSchedule(ss1));
        System.out.println(sse.addSchedule(ss2));
        System.out.println(sse.getAllSysSchedule());
    }

    @Test
    public void completeScheduleTest() {
        SysScheduleDaoImpl sse = new SysScheduleDaoImpl();
        SysSchedule ss1 = new SysSchedule(1, null, null, null);
        SysSchedule ss2 = new SysSchedule(2, null, null, null);
        System.out.println(sse.completeSchedule(ss1));
        System.out.println(sse.uncompleteSchedule(ss2));
        System.out.println(sse.getAllSysSchedule());
        System.out.println(sse.uncompleteSchedule(ss1));
        System.out.println(sse.getAllSysSchedule());
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
    @Test
    public void updateScheduleTest() {
        SysScheduleDaoImpl sse = new SysScheduleDaoImpl();
        SysSchedule ss = new SysSchedule(1, 1, "do something else else", null);
        System.out.println(sse.updateSchedule(ss));
        System.out.println(sse.getAllSysSchedule());
    }

    @Test
    public void deleteScheduleTest() {
        SysScheduleDaoImpl sse = new SysScheduleDaoImpl();
        SysSchedule ss = new SysSchedule(1, 1, "do something else", 1);
        System.out.println(sse.removeSchedule(ss));
        System.out.println(sse.getAllSysSchedule());
    }

}
