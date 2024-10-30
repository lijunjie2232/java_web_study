package com.li.schedule.dao.impl;

import com.li.schedule.sqlTest.advanced.utils.JDBCUtilWithThreadLocal;

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
    public int excuetUpdate(String sql, Object... params) throws Exception {
        Connection conn = JDBCUtilWithThreadLocal.getConnection();
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
                    for (int j = 0; j < columnCount; j++) {
                        String fieldName = rsmd.getColumnName(j + 1);
                        Field field = clazz.getField(fieldName);
                        field.set(t, rsmd.getColumnLabel(j + 1));
                    }
                    clazzList.add(t);
                }
            }
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
    public <T> T executeFirstQuery(Class<T> clazz, String sql, Object... params) throws Exception {
        List<T> result = this.executeQuery(clazz, sql, params);
        if (result.size() > 0) {
            return result.get(0);
        } else return null;
    }
}
