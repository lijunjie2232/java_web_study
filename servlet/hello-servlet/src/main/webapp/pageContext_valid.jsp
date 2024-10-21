<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
name1: <%=pageContext.getAttribute("name1")%>
name2: <%=request.getAttribute("name2")%>
name3: <%=session.getAttribute("name3")%>
name4: <%=application.getAttribute("name4")%>


name1: <%=pageContext.findAttribute("name1")%>
name2: <%=pageContext.findAttribute("name2")%>
name3: <%=pageContext.findAttribute("name3")%>
name4: <%=pageContext.findAttribute("name4")%>
</body>
</html>
