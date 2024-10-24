# Java Servlet
---
- [Java Servlet](#java-servlet)
  - [Maven dependency](#maven-dependency)
  - [Tips](#tips)
    - [URL \& URI](#url--uri)
  - [a simple servlet](#a-simple-servlet)
  - [Servlet life cycle](#servlet-life-cycle)
  - [From Source Code](#from-source-code)
    - [Servlet Interface](#servlet-interface)
    - [WebInitParam](#webinitparam)
    - [ServletConfig Interface](#servletconfig-interface)
    - [ServletContext Interface](#servletcontext-interface)
    - [GenericServlet Class](#genericservlet-class)
    - [HttpServlet Class](#httpservlet-class)


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
### URL & URI
- URL：统一资源定位符(Uniform resource location)
  - http://localhost:8080/hello_servlet2_war_exploded/si
- URI：统一资源标志符(Uniform Resource Identifier)
  - /hello_servlet2_war_exploded/si
- "localhost:8080" is pc(server) name


## a simple servlet
1. create a servlet class extends HttpServlet(implements Servlet) and override method (init/service/doGet/doPost/destroy)
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

## Servlet life cycle
1. first serlvet request/loadOnStartup>-1 -> constructor()  -> instance(single instance)
2. after constructor -> init()
3. every serlvet request -> service()
4. close tomcat -> destroy()

## From Source Code
### Servlet Interface
```java
// Servlet.class
public interface Servlet {
    void init(ServletConfig var1) throws ServletException;

    ServletConfig getServletConfig();// servlet config class

    void service(ServletRequest var1, ServletResponse var2) throws ServletException, IOException;// handle request

    String getServletInfo();

    void destroy();
}
```
### WebInitParam
```java
// usage:
import jakarta.servlet.annotation.WebInitParam;
@WebInitParam(name = "initParam1",value = "abc",description = "initParam1")
```
```java
// WebInitParam.class
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface WebInitParam {
    String name();

    String value();

    String description() default "";
}
```
### ServletConfig Interface
```java
// ServletInterface.java
@WebServlet(
        name = "xxx",
        value = "/xxx",
        initParams = {
                @WebInitParam(
                        name = "initParam1",
                        value = "abc",
                        description = "initParam1"
                ),
                @WebInitParam(
                        name = "initParam2",
                        value = "123",
                        description = "initParam2"
                )
        }
)
public class ServletInterface extends HttpServlet {
    ...
}
```
```xml
// web.xml
  <servlet>
    <servlet-name>xxx</servlet-name>
    <servlet-class>...</servlet-class>
    <init-param>
      <param-name>initParam1</param-name>
      <param-value>abc</param-value>
    </init-param>
  </servlet>
  <servlet-mapping>
    <servlet-name>xxx</servlet-name>
    <url-pattern>/xxx</url-pattern>
  </servlet-mapping>
```
```java
// ServletConfig.class
public interface ServletConfig {
    // <servlet-name> in web.xml or name in @WebServlet
    String getServletName();

    ServletContext getServletContext();

    String getInitParameter(String var1);

    // get initParams from @WebServlet upon
    // servlet class or <init-param> in web.xml
    Enumeration<String> getInitParameterNames();
}
```
### ServletContext Interface
- a ServletContext instance is a application object like application object in jsp
- a ServletContext provides config parameters for all servlet class
- this.getServletConfig().getServletContext() => org.apache.catalina.core.ApplicationContextFacade(a inner class of tomcat) -> ServletContext
- getInitParameter(String) / getInitParameterNames() to get <param-value> and <param-name> of <context-param> in web.xml
- getRealPath("/") get path of where project deployed
- getContextPath() get the application path in tomcat
- setAttribute / getAttribute / removeAttribute could control param value which scope is whole application
```xml
<!-- from web.xml -->
<web-app>
    <context-param>
        <param-name>encoding</param-name>
        <param-value>utf-8</param-value>
    </context-param>
</web-app>
```
```java
// ServletContext.class
public interface ServletContext {
    String TEMPDIR = "jakarta.servlet.context.tempdir";
    String ORDERED_LIBS = "jakarta.servlet.context.orderedLibs";

    // get the application path in tomcat
    String getContextPath();

    ServletContext getContext(String var1);

    int getMajorVersion();

    int getMinorVersion();

    int getEffectiveMajorVersion();

    int getEffectiveMinorVersion();

    String getMimeType(String var1);

    Set<String> getResourcePaths(String var1);

    URL getResource(String var1) throws MalformedURLException;

    InputStream getResourceAsStream(String var1);

    RequestDispatcher getRequestDispatcher(String var1);

    RequestDispatcher getNamedDispatcher(String var1);

    void log(String var1);

    void log(String var1, Throwable var2);

    // get path of where project deployed
    String getRealPath(String var1);

    String getServerInfo();

    // get <param-value> of <context-param> in web.xml
    String getInitParameter(String var1);

    // get <param-name> in <context-param> of web.xml
    Enumeration<String> getInitParameterNames();

    boolean setInitParameter(String var1, String var2);

    Object getAttribute(String var1);

    Enumeration<String> getAttributeNames();

    void setAttribute(String var1, Object var2);

    void removeAttribute(String var1);

    String getServletContextName();

    ServletRegistration.Dynamic addServlet(String var1, String var2);

    ServletRegistration.Dynamic addServlet(String var1, Servlet var2);

    ServletRegistration.Dynamic addServlet(String var1, Class<? extends Servlet> var2);

    ServletRegistration.Dynamic addJspFile(String var1, String var2);

    <T extends Servlet> T createServlet(Class<T> var1) throws ServletException;

    ServletRegistration getServletRegistration(String var1);

    Map<String, ? extends ServletRegistration> getServletRegistrations();

    FilterRegistration.Dynamic addFilter(String var1, String var2);

    FilterRegistration.Dynamic addFilter(String var1, Filter var2);

    FilterRegistration.Dynamic addFilter(String var1, Class<? extends Filter> var2);

    <T extends Filter> T createFilter(Class<T> var1) throws ServletException;

    FilterRegistration getFilterRegistration(String var1);

    Map<String, ? extends FilterRegistration> getFilterRegistrations();

    SessionCookieConfig getSessionCookieConfig();

    void setSessionTrackingModes(Set<SessionTrackingMode> var1);

    Set<SessionTrackingMode> getDefaultSessionTrackingModes();

    Set<SessionTrackingMode> getEffectiveSessionTrackingModes();

    void addListener(String var1);

    <T extends EventListener> void addListener(T var1);

    void addListener(Class<? extends EventListener> var1);

    <T extends EventListener> T createListener(Class<T> var1) throws ServletException;

    JspConfigDescriptor getJspConfigDescriptor();

    ClassLoader getClassLoader();

    void declareRoles(String... var1);

    String getVirtualServerName();

    int getSessionTimeout();

    void setSessionTimeout(int var1);

    String getRequestCharacterEncoding();

    void setRequestCharacterEncoding(String var1);

    default void setRequestCharacterEncoding(Charset encoding) {
        this.setRequestCharacterEncoding(encoding.name());
    }

    String getResponseCharacterEncoding();

    void setResponseCharacterEncoding(String var1);

    default void setResponseCharacterEncoding(Charset encoding) {
        this.setResponseCharacterEncoding(encoding.name());
    }
}
```
### GenericServlet Class
- GenericServlet -> Servlet, SerlvetConfig, Serializable
- destroy(){}
- init() to be override
- abstrace state service(...) again
```java
// GenericServlet.class
public abstract class GenericServlet implements Servlet, ServletConfig, Serializable {
    private static final long serialVersionUID = -8592279577370996712L;
    private static final String LSTRING_FILE = "jakarta.servlet.LocalStrings";
    private static final ResourceBundle lStrings = ResourceBundle.getBundle("jakarta.servlet.LocalStrings");
    private transient ServletConfig config;

    public GenericServlet() {
    }

    public void destroy() {
    }

    public String getInitParameter(String name) {
        ServletConfig sc = this.getServletConfig();
        if (sc == null) {
            throw new IllegalStateException(lStrings.getString("err.servlet_config_not_initialized"));
        } else {
            return sc.getInitParameter(name);
        }
    }

    public Enumeration<String> getInitParameterNames() {
        ServletConfig sc = this.getServletConfig();
        if (sc == null) {
            throw new IllegalStateException(lStrings.getString("err.servlet_config_not_initialized"));
        } else {
            return sc.getInitParameterNames();
        }
    }

    public ServletConfig getServletConfig() {
        return this.config;
    }

    public ServletContext getServletContext() {
        ServletConfig sc = this.getServletConfig();
        if (sc == null) {
            throw new IllegalStateException(lStrings.getString("err.servlet_config_not_initialized"));
        } else {
            return sc.getServletContext();
        }
    }

    public String getServletInfo() {
        return "";
    }

    public void init(ServletConfig config) throws ServletException {
        this.config = config;
        this.init();
    }

    public void init() throws ServletException {
    }

    public void log(String msg) {
        ServletContext var10000 = this.getServletContext();
        String var10001 = this.getServletName();
        var10000.log(var10001 + ": " + msg);
    }

    public void log(String message, Throwable t) {
        this.getServletContext().log(this.getServletName() + ": " + message, t);
    }

    public abstract void service(ServletRequest var1, ServletResponse var2) throws ServletException, IOException;

    public String getServletName() {
        ServletConfig sc = this.getServletConfig();
        if (sc == null) {
            throw new IllegalStateException(lStrings.getString("err.servlet_config_not_initialized"));
        } else {
            return sc.getServletName();
        }
    }
}
```
### HttpServlet Class
- HttpServlet -> GenericServlet:
- protected service() or protected doXxxx() should to be overrided, otherwise raises "method not allowed(405)".
- ServletConfig could be get from init(ServletConfig) or getServletConfig()
```java
// from HttpServlet.class
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        this.legacyHeadHandling = Boolean.parseBoolean(config.getInitParameter("jakarta.servlet.http.legacyDoHead"));
    }

    // override GenericServlet.service(ServletRequest, ServletResponse)
    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
        if (req instanceof HttpServletRequest && res instanceof HttpServletResponse) {
            // downcasting
            HttpServletRequest request = (HttpServletRequest)req;
            HttpServletResponse response = (HttpServletResponse)res;
            // override service with param (HttpServletRequest, HttpServletResponse)
            this.service(request, response);
        } else {
            throw new ServletException("non-HTTP request or response");
        }
    }

    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String method = req.getMethod();// GET POST PUT DELETE OPTIONS ...
        long lastModified;
        if (method.equals("GET")) {
            lastModified = this.getLastModified(req);
            if (lastModified == -1L) {
                this.doGet(req, resp);
            } else {
                long ifModifiedSince = req.getDateHeader("If-Modified-Since");
                if (ifModifiedSince < lastModified) {
                    this.maybeSetLastModified(resp, lastModified);
                    this.doGet(req, resp);
                } else {
                    resp.setStatus(304);
                }
            }
        } else if (method.equals("HEAD")) {
            lastModified = this.getLastModified(req);
            this.maybeSetLastModified(resp, lastModified);
            this.doHead(req, resp);
        } else if (method.equals("POST")) {
            this.doPost(req, resp);
        } else if (method.equals("PUT")) {
            this.doPut(req, resp);
        } else if (method.equals("DELETE")) {
            this.doDelete(req, resp);
        } else if (method.equals("OPTIONS")) {
            this.doOptions(req, resp);
        } else if (method.equals("TRACE")) {
            this.doTrace(req, resp);
        } else if (method.equals("PATCH")) {
            this.doPatch(req, resp);
        } else {
            String errMsg = lStrings.getString("http.method_not_implemented");
            Object[] errArgs = new Object[]{method};
            errMsg = MessageFormat.format(errMsg, errArgs);
            resp.sendError(501, errMsg);
        }
    }

    // handle get request
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String protocol = req.getProtocol();
        String msg = lStrings.getString("http.method_get_not_supported");
        // method not allowed if not been overrided
        resp.sendError(this.getMethodNotSupportedCode(protocol), msg);
    }

    // handle post request
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String protocol = req.getProtocol();
        String msg = lStrings.getString("http.method_post_not_supported");
        // method not allowed if not been overrided
        resp.sendError(this.getMethodNotSupportedCode(protocol), msg);
    }
```