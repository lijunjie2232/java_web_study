package servlet;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.http.*;

@WebListener()
public class ListenerTest implements ServletContextListener, ServletContextAttributeListener {
    // method in ServletContextListener
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext application = sce.getServletContext();
        System.out.println(String.format("[ListenerTest][application-%s]: application initialized", application.hashCode()));
    }

    // method in ServletContextListener
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        ServletContext application = sce.getServletContext();
        System.out.println(String.format("ListenerTest][application-%s]: application destroyed", application.hashCode()));
    }

    // method in ServletContextAttributeListener
    @Override
    public void attributeAdded(ServletContextAttributeEvent sce) {
        ServletContext application = sce.getServletContext();
        System.out.println(String.format("ListenerTest][application-%s]: attribute \"%s\" added", application.hashCode(), sce.getName()));
    }

    // method in ServletContextAttributeListener
    @Override
    public void attributeRemoved(ServletContextAttributeEvent sce) {
        ServletContext application = sce.getServletContext();
        System.out.println(String.format("ListenerTest][application-%s]: attribute \"%s\" removed", application.hashCode(), sce.getName()));
    }

    // method in ServletContextAttributeListener
    @Override
    public void attributeReplaced(ServletContextAttributeEvent sce) {
        ServletContext application = sce.getServletContext();
        String key = sce.getName();
        // old value
        String oldValue = (String) sce.getValue();
        // new value
        String newValue = (String) application.getAttribute(key);
        System.out.println(String.format("ListenerTest][application-%s]: attribute \"%s\" changed from \"%s\" to \"%s\"", application.hashCode(), key, oldValue, newValue));
    }
}

