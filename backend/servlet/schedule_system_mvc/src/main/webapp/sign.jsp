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
    <title>sign</title>
</head>
<body>
<form action="user/sign" method="post">
    <label name="username">
        username:
        <input name="username" id="username" type="text" /><br/>
        <p id="usernameInfo"></p>
    </label><br/>
    <label name="password">
        password:
        <input name="password" id="password" type="password"/><br/>
        <p id="passwordInfo"></p>
    </label><br/>
    <input type="submit" value="signin"/><br/>
</form>
</body>
<script language="JavaScript">
document.getElementById("username").on("focusout", ()=>{
    console.log("abort")
})
</script>
</html>
