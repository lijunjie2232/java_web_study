package com.li.schedule.controler;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jdk.jfr.ContentType;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/ajax/api")
@MultipartConfig
public class AjaxServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        for (Map.Entry<String, String[]> entry : req.getParameterMap().entrySet()) {
            String key = entry.getKey();
            String[] value = entry.getValue();
            System.out.println(String.format("[%s]: %s", key, Arrays.asList(value)));
        }
        resp.setContentType("application/json;charset=utf-8");
        resp.getWriter().write("{\"msg\": \"ok\"}");
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        for (Map.Entry<String, String[]> entry : req.getParameterMap().entrySet()) {
            String key = entry.getKey();
            String[] value = entry.getValue();
            System.out.println(String.format("[%s]: %s", key, Arrays.asList(value)));
        }
        resp.setContentType("application/json;charset=utf-8");
        resp.getWriter().write("{\"msg\": \"ok\"}");
    }
}
