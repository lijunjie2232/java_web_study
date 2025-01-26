<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
<%
String un = request.getParameter("userName");
String pawd = request.getParameter("password");
%>
<h1>UserName:
    <i>
        <%=un%>
    </i>
</h1>
<h1>Password:
    <i>
        <%=pawd%>
    </i>
</h1>
</body>
</html>
