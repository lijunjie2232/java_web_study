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

<form action="" method="post" name="Login">
    <label for="userName">username:</label>
    <input type="text" name="userName" id="userName" value=""/>
    <br/>

    <label for="password">password:</label>
    <input type="password" name="password" id="password" value=""/>
    <br/>

    <input type="submit" value="login"/>
</form>
<%
    String un = request.getParameter("userName");
    String pawd = request.getParameter("password");
//    check is pass
    if (un != null && pawd != null && !un.isEmpty() && !pawd.isEmpty()) {
%>
<jsp:forward page="loginin.jsp">
    <jsp:param name="un" value="<%=un%>"/>
    <jsp:param name="pawd" value="<%=pawd%>"/>
</jsp:forward>
<%} %>
</body>
</html>
