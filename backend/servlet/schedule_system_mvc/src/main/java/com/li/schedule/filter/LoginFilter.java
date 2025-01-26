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

@WebFilter(filterName = "loginfilter",
        urlPatterns = {"/schedule/*", "/user/*",},
        dispatcherTypes = {
                DispatcherType.REQUEST,
                DispatcherType.FORWARD// filte forward by servlet
        }
)
public class LoginFilter extends HttpFilter {
    private List<String> allowUri = Arrays.asList("/user/login", "/user/sign", "/user/manager");

    protected void doFilter(HttpServletRequest req, HttpServletResponse resp, FilterChain chain) throws IOException, ServletException {
        String uri = req.getRequestURI();
        HttpSession session = req.getSession();
        SysUser user = (SysUser) session.getAttribute("user");
        if (!this.allowUri.contains(uri) && user == null) {
            resp.sendRedirect("/login.jsp");
            return;
        }
        chain.doFilter(req, resp);
    }
}
