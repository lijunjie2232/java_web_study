package servlet;

import bean.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Enumeration;

@WebServlet(name = "sessiontets", value = "/sessiontest")
public class SessionServlet extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        session.setMaxInactiveInterval(15*60);
        String SessionID = session.getId();
        System.out.println(SessionID);
        System.out.println(session.isNew());
        Enumeration<String> sessionParams = session.getAttributeNames();
        User user = (User)session.getAttribute("user");
        if (user != null) {
            System.out.println("user in session");
            System.out.println(user);
        }
        else if (!sessionParams.hasMoreElements())
            System.out.println("empty session");
        else {
            while (sessionParams.hasMoreElements()) {
                String key = sessionParams.nextElement();
                System.out.println(String.format("[%s]: %s", key, session.getAttribute(key)));
            }
        }

        resp.getWriter().write("ok");
    }
}
