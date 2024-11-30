<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%--<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>--%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
<h1>loginin</h1>
<%--<jsp:useBean class="bean.User" scope="session" id="user"/>--%>
<%
    if (session.getAttribute("user") != null) {
%>
<jsp:useBean class="bean.User" scope="session" id="user"/>
<p>username:
    <jsp:getProperty name="user" property="username"/>
</p>
<p>password:
    <jsp:getProperty name="user" property="password"/>
</p>
<%
} else {
%>
<p>username: n/a</p>
<p>password: n/a</p>
<%
    }
%>
<%--<p>username:--%>
<%--    <jsp:getProperty name="user" property="username"/>--%>
<%--</p>--%>
<%--<p>password:--%>
<%--    <jsp:getProperty name="user" property="password"/>--%>
<%--</p>--%>
</body>
</html>
