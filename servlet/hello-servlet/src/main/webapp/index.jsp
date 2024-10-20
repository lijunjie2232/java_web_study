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
<h2>Hello World!</h2>
<h2>Date: <%System.out.print(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss E曜日").format(new Date()));%></h2>
<h2>
    Date: <%
    Date date = new Date();
    System.out.print(
            new SimpleDateFormat("yyyy-MM-dd HH:mm:ss E曜日")
                    .format(date)
    );
%>
</h2>
<h2>Date: <%=new Date().toString()%>
</h2>
<%!
    int visit = 0;
    Supplier<Integer> getVisit = () -> visit++;
%>
<h2>visited: <%= getVisit.get() %>
</h2>
<%--<jsp:forward page="forward.jsp"/>--%>
<jsp:useBean id="msg" class="bean.Message" scope="page"/>
<jsp:setProperty name="msg"
                 property="msg"
                 value="Hello WWW"/>
<h1>Message:
    <i>
        <jsp:getProperty name="msg" property="msg"/>
    </i>
</h1>
<form action="msg.jsp" method="post">
    <label for="msg">To:</label>
    <input type="text" name="to" id="to" value="" />
    <br/>
    <label for="msg">Message:</label>
    <input type="text" name="msg" id="msg" value="" />
    <br/>
    <input type="submit" value="SEND" />
</form>
</body>
</html>
