<%@ page import="java.util.Locale" %>
<%@ page import="java.util.Enumeration" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
<p>locale: <%
    Locale locales = request.getLocales().nextElement();
    System.out.println(locales.toString());
    if (locales.equals(Locale.JAPANESE)) {
        out.println("こにちは");
    } else if (locales.equals(Locale.US)) {
        out.println("hello");
    } else if (locales.equals(Locale.CHINA)) {
        out.println("ni hao");
    } else out.println("hello");
%></p>
</body>
</html>
