package com.li.schedule.filter;

import com.li.schedule.pojo.SysUser;
import jakarta.servlet.DispatcherType;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static com.li.schedule.controler.BaseController.setForwardMessage;

@WebFilter(filterName = "adminfilter",
        urlPatterns = {"/user/users", "/user.jsp"},
        dispatcherTypes = {
                DispatcherType.REQUEST,
                DispatcherType.FORWARD// filte forward by servlet
        }
)
public class adminFilter extends HttpFilter {

    protected void doFilter(HttpServletRequest req, HttpServletResponse resp, FilterChain chain) throws IOException, ServletException {
        HttpSession session = req.getSession();
        SysUser user = (SysUser) session.getAttribute("user");
        if (user != null) {
            if (user.getUsername().startsWith("admin") || user.getUsername().equals("root")) {
                chain.doFilter(req, resp);
                return;
            }
            req = setForwardMessage(req, "Retry", "no permission for admin", "/usermanager.jsp");
            req.getRequestDispatcher("/message.jsp").forward(req, resp);
        }
        req = setForwardMessage(req, "Retry", "please login first", "/usermanager.jsp");
        req.getRequestDispatcher("/message.jsp").forward(req, resp);

    }
}
