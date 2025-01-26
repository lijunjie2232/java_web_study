package com.li.schedule.sqlTest;

import com.beust.ah.A;
import com.li.schedule.dao.impl.AccountDaoImpl;
import com.li.schedule.sqlTest.advanced.pojo.Account;
import com.li.schedule.sqlTest.advanced.utils.JDBCUtilWithThreadLocal;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class JDBCTransaction {

    public void connTest() {

        try {
            Connection conn = JDBCUtilWithThreadLocal.getConnection();
            System.out.println(conn);
            JDBCUtilWithThreadLocal.release();
            return;
        }  catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Test
    public void JDBCTransactionTestC() throws SQLException {
        Connection conn = null;
        try {
            AccountDaoImpl accountDaoImpl = new AccountDaoImpl();
            Account ac_a = accountDaoImpl.selectById(1);
            Account ac_b = accountDaoImpl.selectById(2);
            System.out.println(ac_a);
            System.out.println(ac_b);
            conn = JDBCUtilWithThreadLocal.getConnection();
            System.out.println(conn);
            conn.setAutoCommit(false);
            accountDaoImpl.balanceChange(ac_b, 10000.0);
            System.out.println(accountDaoImpl.selectById(ac_b.getId()));
            accountDaoImpl.balanceChange(ac_a, -10000.0);
            System.out.println(accountDaoImpl.selectById(ac_a.getId()));
            conn.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (conn != null) {
                System.out.println("rollback ...");
                conn.rollback();
            }
        } finally {
            if (conn != null) {
                System.out.println("do finally routine ...");
                conn.setAutoCommit(true);
                JDBCUtilWithThreadLocal.release();
            }
        }
    }

    @Test
    public void JDBCTransactionTestR() throws SQLException {
        Connection conn = null;
        try {
            conn = JDBCUtilWithThreadLocal.getConnection();
            conn.setAutoCommit(false);
            String sql = "UPDATE `account` SET `balance`=`balance`+-1000000 WHERE `id`=1";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.executeUpdate();
            conn.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (conn != null) {
                conn.rollback();
            }
        } finally {
            JDBCUtilWithThreadLocal.release();
        }
    }
}
