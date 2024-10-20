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
<jsp:useBean id="msg" class="bean.Message" scope="page"/>
<jsp:setProperty property="to" name="msg"/>
<jsp:setProperty property="msg" name="msg"/>
<h1>To:
    <i>
        <jsp:getProperty name="msg" property="to"/>
    </i>
</h1>
<h1>Message:
    <i>
        <jsp:getProperty name="msg" property="msg"/>
    </i>
</h1>
</body>
</html>
