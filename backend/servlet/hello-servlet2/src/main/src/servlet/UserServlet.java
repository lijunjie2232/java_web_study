package servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import bean.User;

import java.io.IOException;

public class UserServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        resp.setContentType("text/json;charset=utf-8");
//        resp.setHeader("Content-type", "text/json;charset=utf-8");
        if (username != null && password != null && !username.isEmpty() && !password.isEmpty()) {
            req.getSession().setAttribute("user", new User(username, password));
            req.getRequestDispatcher("loginin.jsp").forward(req, resp);
//            resp.getWriter().write(new User(username, password).toString());
        } else resp.getWriter().write("{\"msg\":\"bad login\"}");
    }
}
