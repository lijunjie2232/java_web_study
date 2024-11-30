<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.function.Supplier" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>forward</title>
</head>
<body>
<h2>Forward</h2>
<h2>Date: <%out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss E曜日").format(new Date()));%></h2>
<h2>
    Date: <%
    Date date = new Date();
    out.println(
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
<h2>visited: <%= getVisit.get() %></h2>
</body>
</html>
