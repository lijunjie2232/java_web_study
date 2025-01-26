<%--
  Created by IntelliJ IDEA.
  User: 25335
  Date: 2024/11/1
  Time: 下午4:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>login</title>
</head>
<body>
<form action="user/login" method="post">
    <label name="username">
        username:
        <input name="username" type="text"/>
    </label><br/>
    <label name="password">
        password:
        <input name="password" type="password"/>
    </label><br/>
    <input type="submit" value="login"/> or
    <a href="/sign.jsp" id="signin">signin</a><br/>
</form>
</body>
</html>
