<%@ page import="com.li.schedule.pojo.SysUser" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.HashMap" %>
<%@page import="javax.servlet.jsp.jstl.core.LoopTagSupport" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<head>
    <title>schedule</title>
</head>
<body>
<%--<table>--%>
<%--    <tr>--%>
<%--        <th>title</th>--%>
<%--        <th>complete</th>--%>
<%--    </tr>--%>
<%--    <tr>--%>
<%--        <td>1</td>--%>
<%--        <td>0</td>--%>
<%--    </tr>--%>
<%--</table>--%>
${ userList.size() }<br/>
${userList[0]}<br/>
<p>-------------------------------</p>
<table style="border-width: 1px">
    <tr>
        <th>id</th>
        <th>username</th>
    </tr>
    <%
        for (SysUser su : (List<SysUser>) request.getAttribute("userList")) {
            out.println("<tr><td>");
            out.println(su.getUid());
            out.println("</td><td>");
            out.println(su.getUsername());
            out.println("</td></tr>");
        }
    %>
</table>
<br/>
<p>-------------------------------</p>
<%--out--%>
<c:out value="${userList[0]}"/>
<%--set/remove--%>
<c:set scope="page" var="users" value="${userList}"/><br/>
${userList[0]}<br/>
<c:remove scope="page" var="users"/><br/><br/>
${userList[0]}<br/>
<%--if--%>
<c:if test="${userList.size() > 1}">${userList.size()}</c:if><br/>
<%--choose-when--%>
<%--html annotation could not in choose--%>
<c:choose>
    <c:when test="${userList.size() le 3}"><=3</c:when>
    <c:when test="${userList.size() le 5}"><=5</c:when>
    <c:otherwise>>5</c:otherwise>
</c:choose>
<br/>
<p>-------------------------------</p>
<%--forEach--%>
<c:forEach begin="1" end="10" step="2" var="i">
    <button>${i}</button>
</c:forEach>
<br/>

<p>full table</p>
<table style="border-width: 1px">
    <tr>
        <th>id</th>
        <th>username</th>
    </tr>
    <c:forEach items="${userList}" var="item">
        <tr>
            <td>${item.uid}</td>
            <td>${item.username}</td>
        </tr>
    </c:forEach>
</table>

<p>table index:[1-3]</p>
<table style="border-width: 1px">
    <tr>
        <th>id</th>
        <th>username</th>
    </tr>
    <c:forEach begin="1" end="3" items="${userList}" var="item">
        <tr>
            <td>${item.uid}</td>
            <td>${item.username}</td>
        </tr>
    </c:forEach>
</table>

<%
    Map<String, String> testMap = new HashMap<>();
    testMap.put("key1", "value1");
    testMap.put("key2", "value2");
    testMap.put("key3", "value3");
    request.setAttribute("testMap", testMap);
%>
<table style="border-width: 1px">
    <tr>
        <th>key</th>
        <th>value</th>
        <th>index</th>
        <th>count</th>
    </tr>
    <c:forEach items="${testMap}" var="entry" varStatus="status">
        <tr>
            <td>${entry.key}</td>
            <td>${entry.value}</td>
            <td>${status.index}</td>
            <td>${status.count}</td>
        </tr>
    </c:forEach>
</table>

<p>-------------------------------</p>
<%--url--%>
<c:url value="/user/login" var="loginUrl">
    <c:param name="username" value="admin"/>
    <c:param name="password" value="admin"/>
</c:url>
<a href="${loginUrl}">login</a>

</body>