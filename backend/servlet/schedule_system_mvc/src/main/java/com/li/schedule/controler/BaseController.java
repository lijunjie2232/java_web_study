package com.li.schedule.controler;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;


public class BaseController extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String[] uriSp = req.getRequestURI().split("/");
        String methodName = uriSp[uriSp.length - 1];

        try {
            if (methodName.equals("service"))
                throw new Exception("invalid api");
            Method method = this.getClass().getDeclaredMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
            method.setAccessible(true);
            method.invoke(this, req, resp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static HttpServletRequest setForwardMessage(HttpServletRequest req, String title, String message, String forward) {
        req.setAttribute("title", title);
        req.setAttribute("message", message);
        req.setAttribute("forward", forward);
        return req;
    }
}
