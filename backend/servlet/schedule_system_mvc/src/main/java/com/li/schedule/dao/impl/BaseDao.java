package com.li.schedule.dao.impl;

import com.li.schedule.sqlTest.advanced.utils.JDBCUtilWithThreadLocal;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class BaseDao {
    /**
     * general update one row
     *
     * @param sql:    sql setence
     * @param params: variable in sql
     * @return affect row
     * @throws Exception
     */
    public int executeUpdate(String sql, Object... params) throws Exception {
        Connection conn = JDBCUtilWithThreadLocal.getConnection();
        System.out.println(String.format("[Connection]: %s, [autoCommit]: %s" , conn, conn.getAutoCommit()));
        int row = 0;
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            for (int i = 0; i < params.length; i++) {
                ps.setObject(i + 1, params[i]);
            }
            row = ps.executeUpdate();
        }
        JDBCUtilWithThreadLocal.release();
        return row;
    }

    /**
     * general insert one row with generated keys return
     *
     * @param sql:    sql setence
     * @param params: variable in sql
     * @return affect row
     * @throws Exception
     */
    public int executeInsertWithGenKey(String sql, Object... params) throws Exception {
        Connection conn = JDBCUtilWithThreadLocal.getConnection();
        int GENERATED_KEY = -1;
        Timestamp TIMESTAMP = null;
        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            for (int i = 0; i < params.length; i++) {
                ps.setObject(i + 1, params[i]);
            }
            int row = ps.executeUpdate();
            if (row > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        GENERATED_KEY = rs.getInt(1);
                    }
                }
            }
        }
        JDBCUtilWithThreadLocal.release();
        return GENERATED_KEY;
    }

    /**
     * general query
     *
     * @param clazz:  DAO object class
     * @param sql:    sql setence
     * @param params: variable in sql
     * @return query result
     * @throws
     */
    public <T> List<T> executeQuery(Class<T> clazz, String sql, Object... params) throws Exception {
        Connection conn = JDBCUtilWithThreadLocal.getConnection();
        List<T> clazzList = new LinkedList<>();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            for (int i = 0; i < params.length; i++) {
                ps.setObject(i + 1, params[i]);
            }
            try (ResultSet rs = ps.executeQuery()) {
                ResultSetMetaData rsmd = rs.getMetaData();
                int columnCount = rsmd.getColumnCount();
                while (rs.next()) {
                    T t = clazz.getDeclaredConstructor().newInstance();
                    for (int i = 1; i <= columnCount; i++) {
                        String fieldName = rsmd.getColumnLabel(i);
                        Field field = clazz.getDeclaredField(fieldName);
                        field.setAccessible(true);
                        field.set(t, rs.getObject(i));
                        field.setAccessible(false);
                    }
                    clazzList.add(t);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        JDBCUtilWithThreadLocal.release();
        return clazzList;
    }

    /**
     * general only first row of query
     *
     * @param clazz:  DAO object class
     * @param sql:    sql setence
     * @param params: variable in sql
     * @return query result
     * @throws
     */
    public <T> T executeQuery4OneRow(Class<T> clazz, String sql, Object... params) throws Exception {
        List<T> result = this.executeQuery(clazz, sql, params);
        if (result.size() > 0) {
            return result.get(0);
        } else return null;
    }

    public static String MD5(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(input.getBytes());
            // Convert byte array into signum representation
            StringBuilder sb = new StringBuilder();
            for (byte b : messageDigest) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
