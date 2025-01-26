package servlet;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebInitParam;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.*;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Map;
import java.util.Set;

@WebServlet(
        name = "servletreqtest",
        value = "/sqt",
        loadOnStartup = 10
)
@MultipartConfig
public class ServletReqTest extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // get all params
        Enumeration<String> allParams = req.getParameterNames();
        while (allParams.hasMoreElements()) {
            String param = allParams.nextElement();
            System.out.println(String.format("[%s]: %s", param, req.getParameter(param).toString()));
        }
        resp.getWriter().println("ok");
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        // get all params from params in url by param name
        Enumeration<String> allParams = req.getParameterNames();
        while (allParams.hasMoreElements()) {
            String param = allParams.nextElement();
            if (param.equals("lang")) {
                String[] lang = req.getParameterValues("lang");
                for (String l : lang) {
                    System.out.println(String.format("[lang]: %s", l));
                }
            } else
                System.out.println(String.format("[%s]: %s", param, req.getParameter(param).toString()));
        }
        System.out.println("-----------------getParameterMap-----------------");
        // get all params from params in url by map
        Map<String, String[]> paramsMap = req.getParameterMap();
        Set<Map.Entry<String, String[]>> entries = paramsMap.entrySet();
        for (Map.Entry<String, String[]> entry : entries) {
            System.out.println(String.format("[%s]: %s", entry.getKey(), Arrays.toString(entry.getValue())));
        }
        // get body
        // method 1 for form-data
        System.out.println(req.getParameter("name"));
        // method 1 form raw
        StringBuffer sb = new StringBuffer();
        try (BufferedReader reader = req.getReader()) {
            String line;
            while ((line = reader.readLine()) != null)
                sb.append(line);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(new String(sb));
        // method 2
        ServletInputStream inputStream = req.getInputStream();
        String body2 = new String(inputStream.readAllBytes());
        System.out.println(body2);
        // method 3
//        StringBuffer sb = new StringBuffer();
        sb = new StringBuffer();
        try (ServletInputStream input = req.getInputStream()) {
            byte[] cache = new byte[128];
            for (int len = input.read(cache); len != -1; len = input.read(cache)) {
                sb.append(new String(cache, 0, len));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(new String(sb));
        resp.setContentType("text/json");
        resp.getWriter().println("{\"msg\":\"ok\"}");
    }
}
