# Java Servlet
---
- [Java Servlet](#java-servlet)
  - [Maven dependency](#maven-dependency)
  - [Tips](#tips)
    - [utf-8 encoding for language support](#utf-8-encoding-for-language-support)
    - [jsp for annotation](#jsp-for-annotation)
    - [use "if" in jsp script](#use-if-in-jsp-script)
  - [JSP Grammar](#jsp-grammar)
    - [指令](#指令)
    - [Script](#script)
    - [JSP Annotation](#jsp-annotation)
    - [JSP TAG](#jsp-tag)
  - [JSP Object](#jsp-object)
    - [request / response](#request--response)
    - [session / application](#session--application)
    - [pageCntext](#pagecntext)
    - [config](#config)
    - [exception](#exception)
    - [out](#out)
    - [page](#page)
  - [JavaBean](#javabean)
  - [Servlet](#servlet)
    - [a simple servlet](#a-simple-servlet)


---
## Maven dependency
```xml
<dependency>
    <groupId>jakarta.servlet</groupId>
    <artifactId>jakarta.servlet-api</artifactId>
    <version>6.1.0</version>
    <scope>provided</scope>
</dependency>
<!-- https://mvnrepository.com/artifact/jakarta.servlet.jsp/jakarta.servlet.jsp-api -->
<dependency>
    <groupId>jakarta.servlet.jsp</groupId>
    <artifactId>jakarta.servlet.jsp-api</artifactId>
    <version>4.0.0</version>
    <scope>provided</scope>
</dependency>
<!--        &lt;!&ndash; https://mvnrepository.com/artifact/jakarta.servlet.jsp.jstl/jakarta.servlet.jsp.jstl-api &ndash;&gt;-->
<!-- https://mvnrepository.com/artifact/org.glassfish.web/jakarta.servlet.jsp.jstl -->
<!-- for tomcat10 -->
<dependency>
    <groupId>org.glassfish.web</groupId>
    <artifactId>jakarta.servlet.jsp.jstl</artifactId>
    <version>3.0.1</version>
</dependency>
<dependency>
    <groupId>jakarta.servlet.jsp.jstl</groupId>
    <artifactId>jakarta.servlet.jsp.jstl-api</artifactId>
    <version>3.0.2</version>
    <scope>compile</scope>
</dependency>
<!-- https://mvnrepository.com/artifact/org.apache.taglibs/taglibs-standard-impl -->
<dependency>
    <groupId>org.apache.taglibs</groupId>
    <artifactId>taglibs-standard-impl</artifactId>
    <version>1.2.5</version>
    <scope>runtime</scope>
</dependency>
<dependency>
    <groupId>org.apache.taglibs</groupId>
    <artifactId>taglibs-standard-spec</artifactId>
    <version>1.2.5</version>
    <scope>runtime</scope>
</dependency>
```
---
## Tips
### utf-8 encoding for language support
```jsp
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
```
### jsp for annotation
```jsp
<!-- login user: <%=username%> -->
```
### use "if" in jsp script
```jsp
<%
    if (!"admin".equals(request.getParameter("user"))) {
%>
<p>admin</p>
<%
    }
%>
```
---
## JSP Grammar
### 指令
1. page
```jsp
<%@ page import="java.util.Date"%>
```
2. include
```jsp
<% include file="header.jsp" %>
```
3. taglib
```jsp
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
```
### Script
1. Expression
```jsp
<h2>Date: <%=new Date().toString()%></h2>
```
2. Declaration
```jsp
</h2>
<%!
    int visit = 0;
    Supplier<Integer> getVisit = () -> visit++;
%>
<h2><%= getVisit.get() %></h2>
```
3. Scriptlet
```jsp
<h2>
    Date: <%
    Date date = new Date();
    out.println(
            new SimpleDateFormat("yyyy-MM-dd HH:mm:ss E")
                    .format(date)
    );
%>
</h2>
```
### JSP Annotation
```jsp
<%-- this is an annotation --%>
```
jsp annotation will not appear in HTML source code
### JSP TAG

- jsp:include(support jsp language in tag)

- jsp:useBean / jsp:setProperty / jsp:getProperty

  ```jsp
  <%--index.jsp--%>
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
  
  <%--msg.jsp--%>
  <jsp:useBean id="msg" class="bean.Message" scope="page"/>
  <jsp:setProperty property="*" name="msg"/>
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
  ```

  ```java
  //Message.java
  package bean;
  
  public class Message {
      private String msg;
  
      private String to;
  
      public Message() {
      }
  
      public Message(String msg) {
          this.msg = msg;
      }
  
      public String getMsg() {
          return msg;
      }
  
      public void setMsg(String msg) {
          this.msg = msg;
      }
  
      public String getTo() {
          return to;
      }
  
      public void setTo(String to) {
          this.to = to;
      }
  }
  ```

- jsp:forward(web route not change but content forward) / jsp:param (transport param from forward)

  ```jsp
  <%--login.jsp--%>
  <h1>login</h1>
  
  <form action="" method="post" name="Login">
      <label for="userName">username:</label>
      <input type="text" name="userName" id="userName" value=""/>
      <br/>
  
      <label for="password">password:</label>
      <input type="password" name="password" id="password" value=""/>
      <br/>
  
      <input type="submit" value="login"/>
  </form>
  <%
      String un = request.getParameter("userName");
      String pawd = request.getParameter("password");
  //    check is pass
      if (un != null && pawd != null && !un.isEmpty() && !pawd.isEmpty()) {
  %>
  <jsp:forward page="loginin.jsp">
      <jsp:param name="un" value="<%=un%>"/>
      <jsp:param name="pawd" value="<%=pawd%>"/>
  </jsp:forward>
  <%} %>
  
  <%--loginin.jsp--%>
  <%
  String un = request.getParameter("userName");
  String pawd = request.getParameter("password");
  %>
  <h1>UserName:
      <i>
          <%=un%>
      </i>
  </h1>
  <h1>Password:
      <i>
          <%=pawd%>
      </i>
  </h1>
  ```
## JSP Object
### request / response
```jsp
<%--jsp_obj_test.jsp--%>
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
<a href="locale_test.jsp">locale_test</a>
```
```jsp
<%--request_test.jsp--%>
<p>param=<%=request.getParameter("param")%></p>

<%--cookie_test.jsp--%>
<%
    Cookie[] cookies = request.getCookies();
    if (cookies != null){
        out.println("Cookies:");
        for(Cookie cookie : cookies){
            out.println(String.format("%s: %s", cookie.getName(), cookie.getValue()));
        }
    }
%>

<%--locale_test.jsp--%>
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

```
### session / application
```jsp
<%--session_login.jsp--%>
<form action="session_login.jsp" method="post" name="Login">
    <label for="userName">username:</label>
    <input type="text" name="userName" id="userName" value=""/>
    <br/>

    <label for="password">password:</label>
    <input type="password" name="password" id="password" value=""/>
    <br/>

    <%
        String un = request.getParameter("userName");
        String pawd = request.getParameter("password");
//    check is pass
        if (un != null && pawd != null && un.equals("admin") && pawd.equals("admin")) {
            session.setAttribute("username", un);
            session.setAttribute("password", pawd);
            response.sendRedirect("session_test.jsp");
        } else {
            if (un == null) out.print("<p>input username and password</p>");
            else if (pawd == null) out.print("<p>input password</p>");
            else out.print("<p>username or password not correct</p>");
        }
    %>
    <input type="submit" value="login"/>
</form>


<%--session_test.jsp--%>
<%
    if (session.getAttribute("username") == null || session.getAttribute("password") == null)
        response.sendRedirect("session_login.jsp");
%>
<%!
    SimpleDateFormat sp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss E曜日");
%>
<p>user: <%=session.getAttribute("username")%></p>
<p>password: <%=session.getAttribute("password")%></p>
<p>Session MaxInactiveInterval: <%=session.getMaxInactiveInterval()%></p>
<p>Session LastAccessedTime: <%=sp.format(session.getLastAccessedTime())%></p>
<p>Time: <%=sp.format(new Date().getTime())%></p>
<p>Session restTime: <%=new Date().getTime() - session.getLastAccessedTime()%></p>
<span><a href="session_logout.jsp">logout</a></span>

<%--session_logout.jsp--%>
<%
    if (session.getAttribute("username") != null || session.getAttribute("password") != null){
        session.invalidate();
    }
    response.sendRedirect("session_test.jsp");
%>
```
```jsp
<!--web.xml-->
<web-app>
  <context-param>
    <param-name>application-id</param-name>
    <param-value>v1.0.1</param-value>
  </context-param>
</web-app>

<%--application_test.jsp--%>
<p>application-id: <%=application.getInitParameter("application-id")%></p>
```

### pageCntext
```jsp
<%--pageContext_test.jsp--%>
<%
    pageContext.setAttribute("name1", "value1", PageContext.PAGE_SCOPE);
    pageContext.setAttribute("name2", "value2", PageContext.REQUEST_SCOPE);
    pageContext.setAttribute("name3", "value3", PageContext.SESSION_SCOPE);
    pageContext.setAttribute("name4", "value4", PageContext.APPLICATION_SCOPE);
    pageContext.forward("pageContext_valid.jsp");// request + session + application
    request.getRequestDispatcher("pageContext_valid.jsp").forward(request, response);// request + session + application
    response.sendRedirect("pageContext_valid.jsp");// session + application
%>

<%--pageContext_valid.jsp--%>
name1: <%=pageContext.getAttribute("name1")%>
name2: <%=request.getAttribute("name2")%>
name3: <%=session.getAttribute("name3")%>
name4: <%=application.getAttribute("name4")%>
```

### config
```xml
<web-app>
  <servlet>
    <!-- a id for this servlet -->
    <servlet-name>config_test</servlet-name>
    <!-- specify the jsp file -->
    <jsp-file>/config_test.jsp</jsp-file>
    <init-param>
      <param-name>name</param-name>
      <param-value>li</param-value>
    </init-param>
    <init-param>
      <param-name>age</param-name>
      <param-value>0</param-value>
    </init-param>
  </servlet>
  <servlet-mapping>
    <!--the route of config_test.jsp is /li_config_test-->
    <servlet-name>config_test</servlet-name>
    <url-pattern>/li_config_test</url-pattern>
  </servlet-mapping>
</web-app>
```
```jsp
ServletName：<%=config.getServletName() %><br>
name：<%=config.getInitParameter("name")%><br>
age：<%=config.getInitParameter("age")%><br>
```

### exception
for defined ```<%@ page errorPage="exc.jsp" %>```
```jsp
<%--exc.jsp--%>
msg: <%=exception.getMessage() %>
exc: <%=exception.toString() %>
```

### out
...
### page
...

## JavaBean
- ```<jsp:useBean id="timer" class="bean.Timer"></jsp:useBean>``` to state a instance names timer
- ```<jsp:setProperty name="timer" property="format"></jsp:setProperty>``` to get value from html form request
- ```<jsp:getProperty name="timer" property="format"/>``` to get value of java bean
- ```<%=timer.getFormatedTime()%>``` to call method of java bean

```jsp
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
```

```java
// Timer.java
public class Timer implements Serializable {
    private String format = "yyyy-MM-dd HH:mm:ss E曜日";
    private SimpleDateFormat formatter = new SimpleDateFormat(format);
    private String[] timeZone;

    public Timer() {
    }

    public long getTime() {
        return new Date().getTime();
    }

    public String getFormatedTime() {
        return this.formatter.format(new Date(getTime()));
    }

    public String getFormat() {
        return this.format;
    }

    public void setFormat(String format) {
        try {
            this.formatter = new SimpleDateFormat(format);
            this.format = format;
        } catch (Exception e) {
            System.out.println("format invalid");
            System.out.println(format);
        }
    }

    public String getTimeZone() {
        StringBuffer sb = new StringBuffer();
        if (this.timeZone != null && this.timeZone.length > 0) {
            for (String s : this.timeZone)
                sb.append(String.format("%s,", s));
            if (sb.length() > 0)
                sb.deleteCharAt(sb.length() - 1);
            return sb.toString();
        }
        else
            return "None";
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone.split(",");
        System.out.println(timeZone);
    }

    public void setTimeZone(String[] timeZone) {
        this.timeZone = timeZone;
        System.out.println(timeZone);
    }
}
```

## Servlet
### a simple servlet
1. create a servlet class extends HttpServlet(implements Servlet)
2. config servlet and servlet-mapping tag in web.xml or annotation
3. write a jsp

```java
public class UserServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        if (username != null && password != null && !username.isEmpty() && !password.isEmpty()) {
            resp.getWriter().write(new User(username, password).toString());
        }else resp.getWriter().write("bad login");
    }
}
```

```xml
  <servlet>
    <servlet-name>login</servlet-name>
    <servlet-class>servlet.UserServlet</servlet-class>
  </servlet>
  
  <servlet-mapping>
    <servlet-name>login</servlet-name>
    <url-pattern>/userlogin</url-pattern>
    <url-pattern>/login</url-pattern>
    <url-pattern>/</url-pattern><!-- match all without .jsp -->
    <url-pattern>/*</url-pattern><!-- match all including .jsp -->
    <url-pattern>/login/*</url-pattern><!-- match prefix -->
    <url-pattern>/*.action</url-pattern><!-- match suffix -->
  </servlet-mapping>
```