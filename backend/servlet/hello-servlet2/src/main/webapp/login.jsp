<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%--<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>--%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
<form action="userlogin" method="post">
    <label>
        <span>username: </span>
        <input type="text" name="username" id="username"/>
    </label>
    <br/>
    <label>
        <span>password:</span>
        <input type="password" name="password" id="password"/>
    </label>
    <br/>
    <input type="submit" value="Login"/>
</form>
</body>
</html>
