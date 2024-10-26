package servlet;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.annotation.WebInitParam;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@WebFilter(
        filterName = "filtertest",
        urlPatterns = {"/loginin.jsp"},
        dispatcherTypes = {
                DispatcherType.REQUEST,
                DispatcherType.FORWARD
        }
)
public class FilterTest implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("pic requested");
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession();
        if (session.getAttribute("user") == null) {
            System.out.println("user not logged in");
            response.sendRedirect("login.jsp");
        } else {
            System.out.println("user logged in");
            System.out.println(session.getAttribute("user"));
            Date start = new Date();
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            System.out.println(String.format("[%s]: %s %s %s", dateFormat.format(start), session.getAttribute("user"), request.getMethod(), request.getRequestURI()));
            filterChain.doFilter(servletRequest, servletResponse);
            Date end = new Date();
            System.out.println(String.format("[%s]: handle %s %s use %d ms", dateFormat.format(end), request.getMethod(), request.getRequestURI(), end.getTime() - start.getTime()));
            System.out.println("filter passed hook");
        }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("filter init");
        Filter.super.init(filterConfig);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
        System.out.println("filter destroy");
    }
}
