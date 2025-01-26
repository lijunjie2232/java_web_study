package com.li.schedule.controler;

import com.li.schedule.dao.impl.SysUserDaoImpl;
import com.li.schedule.pojo.SysUser;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name="singintest", value="/signintest")
public class SigninServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        if (username!=null && password!=null && username.length()>0 && password.length()>0) {
            SysUserDaoImpl userDao = new SysUserDaoImpl();
            SysUser user = new SysUser(null, username, userDao.MD5(password), null);
            userDao.addUser(user);
            System.out.println(user);
            resp.getWriter().write(user.toString());
        } else{
            resp.getWriter().write("bad request");
        }

    }
}
