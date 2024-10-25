package servlet;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.annotation.WebInitParam;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

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
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            System.out.println("user logged in");
            System.out.println(session.getAttribute("user"));
            filterChain.doFilter(servletRequest, servletResponse);
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
