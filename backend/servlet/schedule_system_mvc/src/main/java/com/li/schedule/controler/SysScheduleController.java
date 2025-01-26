package com.li.schedule.controler;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;


/**
 * add          /add
 * query        /query
 * update       /update
 * remove       /remove
 */


@WebServlet(name = "sysschedule", value = "/schedule/*")
public class SysScheduleController extends BaseController {

    protected void add(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("add");
    }

    protected void query(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("query");
    }

    protected void update(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("update");
    }

    protected void delete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("delete");
    }

    protected void invalid(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("invalid");
    }
}
