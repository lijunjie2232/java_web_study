<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>

<%
    if (session.getAttribute("username") != null || session.getAttribute("password") != null){
        session.invalidate();
    }
    response.sendRedirect("session_test.jsp");
%>
</body>
</html>
