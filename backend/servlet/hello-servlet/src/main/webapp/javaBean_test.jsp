<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<% request.setCharacterEncoding("UTF-8"); %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<style>
    #time, #format {
        width: 250px;
    }
</style>
<body>
<jsp:useBean id="user" class="bean.User" scope="session"></jsp:useBean>
<h1>User:</h1><br/>
<h2>phone:
    <jsp:getProperty name="user" property="phone"></jsp:getProperty>
</h2>
<h2>userName:
    <jsp:getProperty name="user" property="userName"></jsp:getProperty>
</h2>
<h2>pssword:
    <jsp:getProperty name="user" property="password"></jsp:getProperty>
</h2>

<h1>---------------------------------------------------</h1>
<jsp:useBean id="timer" class="bean.Timer"></jsp:useBean>
<jsp:setProperty name="timer" property="format"></jsp:setProperty>
<%
    String[] timeZone = request.getParameterValues("timeZone");
    timer.setTimeZone(timeZone);
%>
<form action="<%=request.getRequestURI()%>" method="post">
    <label for="format">Format: </label>
    <input name="format" id="format" type="text" value="<jsp:getProperty name="timer" property="format"/>"/>
    <ul>
        <li>make a choice:</li>
        <li>
            <input type="checkbox" name="timeZone" value="z0"/>
            <input type="checkbox" name="timeZone" value="z1"/>
            <input type="checkbox" name="timeZone" value="z2"/>
        </li>
    </ul>
    <input type="submit" value="getTime">
</form>
<p>Time:<input name="time" id="time" type="text" value="<%=timer.getFormatedTime()%>" disabled/></p>
<p>choice:
    <jsp:getProperty name="timer" property="timeZone"/>
</p>
</body>
</html>
