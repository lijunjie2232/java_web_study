# Java Servlet
---
- [Java Servlet](#java-servlet)
  - [Maven dependency](#maven-dependency)
  - [Tips](#tips)
    - [utf-8 encoding for language support](#utf-8-encoding-for-language-support)
    - [jsp for annotation](#jsp-for-annotation)
  - [JSP Grammar](#jsp-grammar)
    - [指令](#指令)
    - [Script](#script)
    - [JSP Annotation](#jsp-annotation)
    - [JSP TAG](#jsp-tag)


---
## Maven dependency
```xml
<!-- https://mvnrepository.com/artifact/javax.servlet/javax.servlet-api -->
<dependency>
    <groupId>javax.servlet</groupId>
    <artifactId>javax.servlet-api</artifactId>
    <version>4.0.1</version>
    <scope>provided</scope>
</dependency>
        <!-- https://mvnrepository.com/artifact/javax.servlet.jsp/javax.servlet.jsp-api -->
<dependency>
<groupId>javax.servlet.jsp</groupId>
<artifactId>javax.servlet.jsp-api</artifactId>
<version>2.3.3</version>
<scope>provided</scope>
</dependency>
        <!-- https://mvnrepository.com/artifact/jakarta.servlet.jsp.jstl/jakarta.servlet.jsp.jstl-api -->
<dependency>
<groupId>jakarta.servlet.jsp.jstl</groupId>
<artifactId>jakarta.servlet.jsp.jstl-api</artifactId>
<version>3.0.2</version>
</dependency>
        <!-- https://mvnrepository.com/artifact/org.apache.taglibs/taglibs-standard-impl -->
<dependency>
<groupId>org.apache.taglibs</groupId>
<artifactId>taglibs-standard-impl</artifactId>
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

  
