<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
<%
    pageContext.setAttribute("name1", "value1", PageContext.PAGE_SCOPE);
    pageContext.setAttribute("name2", "value2", PageContext.REQUEST_SCOPE);
    pageContext.setAttribute("name3", "value3", PageContext.SESSION_SCOPE);
    pageContext.setAttribute("name4", "value4", PageContext.APPLICATION_SCOPE);
//    pageContext.forward("pageContext_valid.jsp");// request + session + application
//    request.getRequestDispatcher("pageContext_valid.jsp").forward(request, response);// request + session + application
    response.sendRedirect("pageContext_valid.jsp");// session + application
%>
</body>
</html>
