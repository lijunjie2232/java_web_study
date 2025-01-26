package com.li.schedule.controler;

import com.li.schedule.dao.impl.SysUserDaoImpl;
import com.li.schedule.pojo.SysUser;
import com.li.schedule.service.SysUserService;
import com.li.schedule.service.impl.SysUserServiceImpl;
import com.li.schedule.util.MD5Util;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "sysuser", value = "/user/*")
public class SysUserController extends BaseController {
    private SysUserService sysUserService = new SysUserServiceImpl();

    protected void login(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        SysUser user = sysUserService.userLogin(username, password);
        if (user != null) {
            req = this.setForwardMessage(req, "success", "Login success", "/schedule.jsp");
            req.getSession().setAttribute("user", user);
            req.getRequestDispatcher("/message.jsp").forward(req, resp);
            return;
        }
        req = this.setForwardMessage(req, "retry", "username or password error", "/login.jsp");
        req.getRequestDispatcher("/message.jsp").forward(req, resp);
    }

    protected void sign(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        SysUser user = sysUserService.userSign(username, password);
        if (user != null) {
            System.out.println(user);
            req = this.setForwardMessage(req, "success", "Login success", "/schedule.jsp");
            req.getSession().setAttribute("user", user);
            req.getRequestDispatcher("/message.jsp").forward(req, resp);
            return;
        }
        req = this.setForwardMessage(req, "retry", "please set a stronger password", "/sign.jsp");
        req.getRequestDispatcher("/message.jsp").forward(req, resp);
    }

    protected void manager(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        SysUser user = sysUserService.userLogin(username, password);
        if (user != null) {
            if (user.getUsername().startsWith("admin") || user.getUsername().equals("root")) {
//                req = this.setForwardMessage(req, "success", "Login success", "/user.jsp");
                req.getSession().setAttribute("user", user);
                req.setAttribute("userList", this.sysUserService.getAllUser());
                System.out.println(req.getAttribute("userList"));
                req.getRequestDispatcher("/user.jsp").forward(req, resp);
                return;
            } else {
                req = this.setForwardMessage(req, "Retry", "no permission for admin", "/usermanager.jsp");
                req.getRequestDispatcher("/message.jsp").forward(req, resp);
                return;
            }
        }
        req = this.setForwardMessage(req, "retry", "username or password error", "/usermanager.jsp");
        req.getRequestDispatcher("/message.jsp").forward(req, resp);
    }

    protected void users(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        SysUser user = (SysUser) req.getSession().getAttribute("user");
        if (user.getUsername().startsWith("admin") || user.getUsername().equals("root")) {
            req.setAttribute("userList", this.sysUserService.getAllUser());
            req.getRequestDispatcher("/user.jsp").forward(req, resp);
            return;
        } else {
            req = this.setForwardMessage(req, "Retry", "no permission for admin", "/usermanager.jsp");
            req.getRequestDispatcher("/message.jsp").forward(req, resp);
            return;
        }
    }
}
