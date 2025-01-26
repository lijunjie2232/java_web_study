<%--
  Created by IntelliJ IDEA.
  User: 25335
  Date: 2024/11/1
  Time: 下午5:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title><%=request.getAttribute("title")%></title>
</head>
<body>
<h1><%=request.getAttribute("message")%></h1>
<p>redirect after <span id="sec"></span>s or <a href="<%=request.getAttribute("forward")%>">click to redirect</a></p>
</body>
<script language="JavaScript">
    let redirectUrl = "<%=request.getAttribute("forward")%>"

    var num = 4; //倒计时的秒数
    var id = window.setInterval('doUpdate()', 1000);

    function doUpdate() {
        document.getElementById('sec').innerHTML = num;
        if (num == 0) {
            window.clearInterval(id);
            window.location = redirectUrl;
        }
        num--;
    }

</script>
</html>
