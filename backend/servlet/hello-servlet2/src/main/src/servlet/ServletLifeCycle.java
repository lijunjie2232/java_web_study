package servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "slc", urlPatterns = "/slc", loadOnStartup = 100)
public class ServletLifeCycle extends HttpServlet {
    public ServletLifeCycle() {
        System.out.println("construct");
    }

    public void init() {
        System.out.println("init");
    }

    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("service");
    }

    public void destroy() {
        System.out.println("destroy");
    }
}
