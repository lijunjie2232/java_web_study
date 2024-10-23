# Java Servlet
---
- [Java Servlet](#java-servlet)
  - [Maven dependency](#maven-dependency)
  - [Tips](#tips)
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
