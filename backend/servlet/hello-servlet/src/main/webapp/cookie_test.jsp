<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
<%
    Cookie[] cookies = request.getCookies();
    if (cookies != null){
        out.println("Cookies:");
        for(Cookie cookie : cookies){
            out.println(String.format("%s: %s", cookie.getName(), cookie.getValue()));
        }
    }
%>
</body>
</html>
