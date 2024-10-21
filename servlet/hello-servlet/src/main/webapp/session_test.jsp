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
<h1>Session</h1>

<%
    if (session.getAttribute("username") == null || session.getAttribute("password") == null)
        response.sendRedirect("session_login.jsp");
%>
<%!
    SimpleDateFormat sp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss E曜日");
%>
<p>user: <%=session.getAttribute("username")%></p>
<p>password: <%=session.getAttribute("password")%></p>
<p>Session MaxInactiveInterval: <%=session.getMaxInactiveInterval()%></p>
<p>Session LastAccessedTime: <%=sp.format(session.getLastAccessedTime())%></p>
<p>Time: <%=sp.format(new Date().getTime())%></p>
<p>Session restTime: <%=new Date().getTime() - session.getLastAccessedTime()%></p>


<span><a href="session_logout.jsp">logout</a></span>
</body>
</html>
