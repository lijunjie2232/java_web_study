package servlet;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebInitParam;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Enumeration;

@WebServlet(
        name = "servletInterface",
        value = "/si",
        loadOnStartup = 10,
        initParams = {
                @WebInitParam(
                        name = "initParam1",
                        value = "abc",
                        description = "initParam1"
                ),
                @WebInitParam(
                        name = "initParam2",
                        value = "123",
                        description = "initParam2"
                )
        }
)
public class ServletInterface extends HttpServlet {
    @Override
    public void init(ServletConfig config) throws ServletException {
        System.out.println("--------- ServletInterface init start ---------");
        Enumeration<String> initParams = config.getInitParameterNames();
        while (initParams.hasMoreElements()) {
            String initParam = initParams.nextElement();
            System.out.println(String.format("[ServletConfigInitParam] name: %s, value: %s", initParam, config.getInitParameter(initParam)));
        }
        ServletContext applicaton = config.getServletContext();
        Enumeration<String> contextParams = applicaton.getInitParameterNames();
        while (contextParams.hasMoreElements()) {
            String contextParam = contextParams.nextElement();
            System.out.println(String.format("[ServletContextInitParam] name: %s, value: %s", contextParam, applicaton.getInitParameter(contextParam)));
        }
        super.init(config);

        System.out.println("--------- ServletInterface init end ---------");
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletConfig sc = this.getServletConfig();
        Enumeration<String> initParams = sc.getInitParameterNames();
        String p1 = sc.getInitParameter("initParam1");
        String p2 = sc.getInitParameter("initParam2");
        String servletName = sc.getServletName();
        ServletContext application = sc.getServletContext();
        System.out.println(application.toString());
        System.out.println(application.getClass());
        String si = this.getServletInfo();
        System.out.println("contextPath: " + application.getContextPath());
        resp.getWriter().println("servletInterface");
        application.setAttribute("contextParam1", p2);
        application.setAttribute("contextParam2", p1);
        System.out.println("cp1: " + application.getAttribute("contextParam1"));// 123
        System.out.println("cp2: " + application.getAttribute("contextParam2"));// abc
        application.removeAttribute("contextParam1");
        application.removeAttribute("contextParam2");
        System.out.println("after remove:");
        System.out.println("cp1: " + application.getAttribute("contextParam1"));// null
        System.out.println("cp2: " + application.getAttribute("contextParam2"));// null
        System.out.println("--------- ServletInterface request test ---------");
        System.out.println("url: " + req.getRequestURL());
        System.out.println("uri: " + req.getRequestURI());
        System.out.println("serverName: " + req.getServerName());
        System.out.println("serverPort: " + req.getServerPort());
        System.out.println("protocol: " + req.getProtocol());
        System.out.println("requestURL: " + req.getRequestURL());
        System.out.println("remoteAddr: " + req.getRemoteAddr());
        System.out.println("remoteHost: " + req.getRemoteHost());
        System.out.println("remotePort: " + req.getRemotePort());
    }
}
