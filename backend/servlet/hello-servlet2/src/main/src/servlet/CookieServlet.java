package servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

@WebServlet(name = "cookietest", value = "/cookietest")
public class CookieServlet extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        Cookie c1 = new Cookie("id", "2cceb827-8102-480e-aceb-6ce5868ba47c");
//        Cookie c2 = new Cookie("auth", "6876434864341864");
//        c2.setMaxAge(10);
//        resp.addCookie(c1);
//        resp.addCookie(c2);
//        resp.getWriter().write("ok");
        try (PrintWriter out = resp.getWriter()) {
            Cookie[] cookies = req.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    out.write(String.format("[%s]: %s\n", cookie.getName(), cookie.getValue()));
                }
            }
        }
    }
}
