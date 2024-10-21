<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.function.Supplier" %>
<%@ page import="java.util.Locale" %>
<%@ page import="java.util.Enumeration" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
<h2>Hello World!</h2>
<h2>Date: <%out.print(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss E曜日").format(new Date()));%></h2>
<h2>
    Date: <%
    Date date = new Date();
    out.print(
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

<p>------------------JSP Object Test------------------</p>
<a href="request_test.jsp?param=123312">request_test</a>
<br/>
<%--add cookie--%>
<%
    response.addCookie(new Cookie("c1", "111111"));
    response.addCookie(new Cookie("c2", "123321"));
%>
<a href="cookie_test.jsp">cookie_test</a>
<%--request get info--%>
<p>protocol: <%=request.getProtocol()%></p>
<p>url: <%=request.getRequestURL()%></p>
<p>uri: <%=request.getRequestURI()%></p>
<p>remote host: <%=request.getRemoteHost()%></p>
<p>remote addr: <%=request.getRemoteAddr()%></p>
<%--locale test--%>
<%
    response.setLocale(Locale.JAPANESE);
//    response.setLocale(Locale.US);
//    response.sendRedirect("locale_test.jsp");
%>
<a href="locale_test.jsp">locale_test</a><br/>
<a href="response_test.jsp">response_test</a><br/>
<a href="session_login.jsp">session_test</a><br/>
<a href="application_test.jsp">application_test</a><br/>
<a href="pageContext_test.jsp">pageContest_test</a><br/>
<a href="li_config_test">config_test</a><br/>



</body>
</html>
