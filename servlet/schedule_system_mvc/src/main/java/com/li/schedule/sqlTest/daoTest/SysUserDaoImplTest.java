//package com.li.schedule.sqlTest.daoTest;
//
//import com.li.schedule.dao.impl.BaseDao;
//import com.li.schedule.dao.impl.SysUserDaoImpl;
//import com.li.schedule.pojo.SysUser;
//import org.junit.jupiter.api.Test;
//
//public class SysUserDaoImplTest {
//    @Test
//    public void testSelectAll(){
//        SysUserDaoImpl sysUserDao = new SysUserDaoImpl();
//        System.out.println(sysUserDao.selectAll());
//    }
//    @Test
//    public void testSelectByUid(){
//        SysUserDaoImpl sysUserDao = new SysUserDaoImpl();
//        System.out.println(sysUserDao.selectByUid(1));
//    }
//    @Test
//    public void testAddUser(){
//        SysUserDaoImpl sysUserDao = new SysUserDaoImpl();
//        SysUser user = new SysUser(null, "user5", BaseDao.MD5("user5"), null);
//        System.out.println(sysUserDao.addUser(user));
//    }
//    @Test
//    public void testUpdateUser(){
//        SysUserDaoImpl sysUserDao = new SysUserDaoImpl();
//        SysUser user = new SysUser(3, "user0", BaseDao.MD5("user0"), null);
//        System.out.println(sysUserDao.updateUser(user));
//    }
//    @Test
//    public void testDeleteById(){
//        SysUserDaoImpl sysUserDao = new SysUserDaoImpl();
//        System.out.println(sysUserDao.deleteByUid(7));
//    }
//    @Test
//    public void testMD5(){
//        SysUserDaoImpl sysUserDao = new SysUserDaoImpl();
//        System.out.println(BaseDao.MD5("root"));
//    }
//}
