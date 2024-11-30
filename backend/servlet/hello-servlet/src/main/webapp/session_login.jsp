<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.function.Supplier" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
<h1>login</h1>
<hr/>

<form action="session_login.jsp" method="post" name="Login">
    <label for="userName">username:</label>
    <input type="text" name="userName" id="userName" value=""/>
    <br/>

    <label for="password">password:</label>
    <input type="password" name="password" id="password" value=""/>
    <br/>

    <%
        String un = request.getParameter("userName");
        String pawd = request.getParameter("password");
//    check is pass
        if (un != null && pawd != null && un.equals("admin") && pawd.equals("admin")) {
            session.setAttribute("username", un);
            session.setAttribute("password", pawd);
            response.sendRedirect("session_test.jsp");
//            request.getRequestDispatcher("session_test.jsp").forward(request, response);
        } else {
            if (un == null) out.print("<p>input username and password</p>");
            else if (pawd == null) out.print("<p>input password</p>");
            else out.print("<p>username or password not correct</p>");
        }
    %>

    <input type="submit" value="login"/>
</form>


</body>
</html>
