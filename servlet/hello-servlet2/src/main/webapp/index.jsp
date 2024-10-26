<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
<p>---------------servlet test---------------</p>
<a href="login.jsp">login</a><br/>

<p>---------------urlpattern test---------------</p>
<a href="urlpatterntest">urlpatterntest</a><br/>
<a href="upt">upt</a><br/>

<p>---------------annotation test---------------</p>
<a href="annotest">annotest</a><br/>
<a href="ant">ant</a><br/>

<p>---------------annotation test---------------</p>
<a href="slc">lifecycle</a><br/>

<p>---------------annotation test---------------</p>
<a href="si">servletInterface</a><br/>

<p>---------------file test---------------</p>
<a href="fileManager.jsp">filemanager</a><br/>


<p>---------------forward test---------------</p>
<a href="forwardtest">forwardtest</a><br/>

<p>---------------redirect test---------------</p>
<a href="redirecttest">redirecttest</a><br/>

<p>---------------path test a---------------</p>
<a href="pathtest/a">pathtesta</a><br/>
<p>---------------path test b---------------</p>
<a href="pathtest/b">pathtestb</a><br/>
<p>---------------path test d---------------</p>
<a href="a/b/c/d.html">pathtestd</a><br/>
<p>---------------path test view---------------</p>
<a href="pathtest/view">pathtestview</a><br/>
<p>---------------path test pic---------------</p>
<img src="static/img/deformableDETR.png" alt="pic not found" width="700"/>

<p>---------------cookie test---------------</p>
<%
    Cookie c1 = new Cookie("id", "2cceb827-8102-480e-aceb-6ce5868ba47c");
    Cookie c2 = new Cookie("auth", "6876434864341864");
    // set cookie affect route
    c1.setPath("/cookietest1");
    // set c2 valids in 10 seconds
    c2.setMaxAge(10);
    response.addCookie(c1);
    response.addCookie(c2);
%>
<a href="cookietest">cookietest</a><br/>
</body>
</html>
