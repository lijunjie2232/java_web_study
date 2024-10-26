# Java Servlet
---
- [Java Servlet](#java-servlet)
  - [Maven dependency](#maven-dependency)
  - [Tips](#tips)
    - [URL \& URI](#url--uri)
    - [Multicheckbox param in request](#multicheckbox-param-in-request)
    - [Form-data in request body](#form-data-in-request-body)
    - [`name` in WebServlet could not to be the same](#name-in-webservlet-could-not-to-be-the-same)
    - [The priority of `web.xml` is heigher than annotation](#the-priority-of-webxml-is-heigher-than-annotation)
  - [A simple servlet](#a-simple-servlet)
  - [Servlet life cycle](#servlet-life-cycle)
  - [From Source Code](#from-source-code)
    - [Servlet Interface](#servlet-interface)
    - [WebInitParam](#webinitparam)
    - [ServletConfig Interface](#servletconfig-interface)
    - [ServletContext Interface](#servletcontext-interface)
    - [GenericServlet Class](#genericservlet-class)
    - [HttpServlet Class](#httpservlet-class)
  - [Servlet Request and Response](#servlet-request-and-response)
    - [Different type of data in request body](#different-type-of-data-in-request-body)
    - [Servlet Request get data](#servlet-request-get-data)
    - [Servlet Response API](#servlet-response-api)
    - [forward \& redirect](#forward--redirect)
  - [Cookie \& Session](#cookie--session)
    - [Cookie](#cookie)
    - [Session](#session)
  - [Filter](#filter)
    - [A simple filter](#a-simple-filter)
  - [Use UTF-8 charset](#use-utf-8-charset)
    - [HTML](#html)
    - [JSP](#jsp)
    - [Tomcat log](#tomcat-log)
    - [Java](#java)
    - [Request parameter (param at url)](#request-parameter-param-at-url)
    - [Request parameter (param in body)](#request-parameter-param-in-body)
    - [Response](#response)
  - [Path](#path)
    - [Frontend](#frontend)
    - [Backend](#backend)


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
### Multicheckbox param in request
- multicheckbox sends params via url like:`http://localhost:8080/api?checks=v1?checks=v2`, could be received by `String[] checkBoxValues = req.getParameterValues("checks");` or `Map<String, String[]> paramsMap = req.getParameterMap();`
### Form-data in request body
- `<form action="xxx.jsp"method="post">` => sends post request with x-www-form-urlencoded data in body while `<form action="xxx.jsp"method="post" enctype="multipart/form-data">` sends post request with form-data in body could including far big mount of of data
- use `@MultipartConfig` for servlet to get text(String) param from form-data enctyped  body like `<form action="xxx.jsp"method="post" enctype="multipart/form-data">` to avoid `req.getParameter` to return `null`
### `name` in WebServlet could not to be the same
### The priority of `web.xml` is heigher than annotation

## A simple servlet
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
## Servlet Request and Response
### Different type of data in request body
| type                    | frontend                                                              | backend                                                                        |
| ----------------------- | --------------------------------------------------------------------- | ------------------------------------------------------------------------------ |
| `form-data`             | HTML form with `<form enctype="multipart/form-data">`or`FormData` API | `@MultipartConfig`decorate servlet + `request.getParameter`or`request.getPart` |
| `x-www-form-urlencoded` | HTML form or`URLSearchParams`                                         | `request.getParameter`                                                         |
| `raw` (JSON, XML)       | `fetch`using`JSON.stringify`                                          | `request.getReader` + paras JSON String                                        |

### Servlet Request get data
1. get `x-www-form-urlencoded` data in request body or param via url
```java
// get all params from params in url by param name
Enumeration<String> allParams = req.getParameterNames();
while (allParams.hasMoreElements()) {
    String param = allParams.nextElement();
    if (param.equals("lang")) {
        String[] lang = req.getParameterValues("lang");
        for (String l : lang) {
            System.out.println(String.format("[lang]: %s", l));
        }
    } else
        System.out.println(String.format("[%s]: %s", param, req.getParameter(param).toString()));
}
System.out.println("-----------------getParameterMap-----------------");
// get all params from params in url by map
Map<String, String[]> paramsMap = req.getParameterMap();
Set<Map.Entry<String, String[]>> entries = paramsMap.entrySet();
for (Map.Entry<String, String[]> entry : entries) {
    System.out.println(String.format("[%s]: %s", entry.getKey(), Arrays.toString(entry.getValue())));
}
```
2. get `form-data`
```java
@MultipartConfig
public class ServletReqTest extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
       System.out.println(req.getParameter("name"));
    }
}
```
3. get `raw` in `json`
- method1:
```java
StringBuffer sb = new StringBuffer();
try(BufferedReader input = req.getReader()){
    String line;
    while ((line = input.readLine()) != null)
        sb.append(line);
} catch (Exception e) {
    e.printStackTrace();
}
System.out.println(new String(sb));
```
- method 2
```java
StringBuffer sb = new StringBuffer();
try (ServletInputStream input = req.getInputStream()) {
    byte[] cache = new byte[128];
    for (int len = input.read(cache); len != -1; len = input.read(cache)) {
        sb.append(new String(cache, 0, len));
    }
} catch (Exception e) {
    e.printStackTrace();
}
System.out.println(new String(sb));
```

### Servlet Response API
1. `resp.setStatus` set response status codes
    1. 情報レスポンス (100 – 199)
    2. 成功レスポンス (200 – 299)
    3. リダイレクトメッセージ (300 – 399)
    4. クライアントエラーレスポンス (400 – 499)
    5. サーバーエラーレスポンス (500 – 599)

| 状态码(partial) | 分类                  | 含义                                             |
| --------------- | --------------------- | ------------------------------------------------ |
| **1xx**         | **信息响应**          | 表示请求已被接收，需要客户端继续操作             |
| 100             | Continue              | 客户端可以继续发送请求，服务器暂未处理完所有请求 |
| 101             | Switching Protocols   | 服务器同意客户端请求，正在切换协议               |
| **2xx**         | **成功响应**          | 表示请求成功且服务器已正常处理                   |
| 200             | OK                    | 请求成功，返回所请求的资源                       |
| 201             | Created               | 请求成功并在服务器上创建了新的资源               |
| 202             | Accepted              | 请求被接受，但尚未处理                           |
| 204             | No Content            | 请求成功，无内容返回                             |
| **3xx**         | **重定向**            | 表示请求资源的位置发生变更，客户端需进一步处理   |
| 301             | Moved Permanently     | 资源已永久移至新位置，使用新的URI                |
| 302             | Found                 | 资源临时移至新位置，可暂时使用新的URI            |
| 304             | Not Modified          | 资源未更改，客户端可使用缓存                     |
| **4xx**         | **客户端错误**        | 表示客户端请求错误，服务器无法处理               |
| 400             | Bad Request           | 请求语法错误或参数错误                           |
| 401             | Unauthorized          | 未授权，需进行身份验证                           |
| 403             | Forbidden             | 拒绝访问，无权查看该资源                         |
| 404             | Not Found             | 资源未找到，服务器无法找到所请求的资源           |
| 405             | Method Not Allowed    | 请求方法不被允许                                 |
| **5xx**         | **服务器错误**        | 表示服务器在处理请求时发生内部错误               |
| 500             | Internal Server Error | 服务器内部错误，无法完成请求                     |
| 501             | Not Implemented       | 服务器不支持请求的功能                           |
| 502             | Bad Gateway           | 网关或代理服务器从上游服务器接收到无效响应       |
| 503             | Service Unavailable   | 服务器暂时无法处理请求，通常是由于过载或维护     |
| 504             | Gateway Timeout       | 网关或代理服务器在等待上游服务器响应时超时       |

2. `resp.setHeader` set response header
3. `resp.getOutputStream`(binary data) & `resp.getWriter`(text data): output method to response

### forward & redirect
| action                  | forward                         | redirect                         |
| ----------------------- | ------------------------------- | -------------------------------- |
| 場所                    | inner server                    | outer server                     |
| browser url change      | x                               | o                                |
| param passing           | request + session + application | session + application            |
| response status code    | 200                             | 302 + 200 (two times of request) |
| get resource out of app | x                               | o                                |

**page change: if both, use redirect**

## Cookie & Session
- Cookie in browser, session in server

### Cookie
```java
// set cookie
Cookie c1 = new Cookie("id", "2cceb827-8102-480e-aceb-6ce5868ba47c");
Cookie c2 = new Cookie("auth", "6876434864341864");
// set cookie affect route
c1.setPath("/cookietest1");
// set c2 valids in 10 seconds
c2.setMaxAge(10);
response.addCookie(c1);
response.addCookie(c2);

// get cookie
Cookie[] cookies = req.getCookies()// if no cookie, cookies is null
```
### Session
- Session is handled automaticly by servlet, referencing `JSESSIONID` in cookie, so if browser bans all the cookie, session function is not work
- what happened after `req.getSession()`:
- check `JSESSIONID` in req cookie:
   - if `JSESSIONID` found => find session by `JSESSIONID`:
     - if could not find session by `JSESSIONID` => ***create new session and return it***
     - if found session by `JSESSIONID` => ***return found session***
   - if `JSESSIONID` not found => ***create a new session and return it***
```java
// get session
HttpSession session = req.getSession();
// set session timeout (second)
session.setMaxInactiveInterval(15*60);
// check session is newly created
System.out.println(session.isNew());
// get JSESSIONID
System.out.println(session.getId());

// get values in session
Enumeration<String> sessionParams = session.getAttributeNames();
User user = (User)session.getAttribute("user");
if (user != null) {
    System.out.println("user in session");
    System.out.println(user);
}
else if (!sessionParams.hasMoreElements())
    System.out.println("empty session");
else {
    while (sessionParams.hasMoreElements()) {
        String key = sessionParams.nextElement();
        System.out.println(String.format("[%s]: %s", key, session.getAttribute(key)));
    }
}
```
- default session timeout is 30 minute, if not requests session longer than 30 minute, session will be invalid and next request will get a new session.
- set `<session-timeout>` in `<session-config>` in web.xml  to change session timeout or call `session.setMaxInactiveInterval(second)` to specify timeout for a session object, default `session-timeout` is set in `tomcat/conf/web.xml`.
```xml
<!-- web.xml -->
<session-config>
    <session-timeout>15</session-timeout>
</session-config>
```


## Filter
### A simple filter
1. create a class implements `jakarta.servlet.Filter`
2. override three method: init / destroy / doFilter
3. add filter and filter-mapping tag in web.xml or use @WebFilter
```java
// FilterTest.java
@WebFilter(
        filterName = "filtertest",
        urlPatterns = {"/loginin.jsp"},
        dispatcherTypes = {
                DispatcherType.REQUEST,
                DispatcherType.FORWARD// filte forward by servlet
        }
)
public class FilterTest implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("pic requested");
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession();
        if (session.getAttribute("user") == null) {
            System.out.println("user not logged in");
            response.sendRedirect("login.jsp");
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            System.out.println("user logged in");
            System.out.println(session.getAttribute("user"));
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("filter init");
        Filter.super.init(filterConfig);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
        System.out.println("filter destroy");
    }
}
```
```xml
<!-- web.xml -->
<filter>
    <filter-name>filtertest</filter-name>
    <filter-class>servlet.FilterTest</filter-class>
</filter>
<filter-mapping>
    <filter-name>filtertest</filter-name>
    <url-pattern>*.png</url-pattern>
    <url-pattern>*.jpg</url-pattern>
</filter-mapping>
```
- Filter inited before servlet and destroyed after sevlet
- Filter would not filte forward of request object inner server by default in newer servlet, by adding `dispatcherTypes = {DispatcherType.REQUEST, DispatcherType.FORWARD} `to `@WebFilter` allowing filter catch forward inner server.


## Use UTF-8 charset
### HTML
```html
<head>
    <meta charset="UTF-8">
</head>
```
### JSP
```jsp
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
```
### Tomcat log
```xml
<!-- tomcat/conf/server.xml -->
<Connector connectionTimeout="20000" port="8080" protocol="HTTP/1.1" redirectPort="8443" URIEncoding="UTF-8"/>
```
use UTF-8 in conf/logging.properties
### Java
```bash
-Dfile.encoding=UTF-8
```

### Request parameter (param at url)
```xml
<!-- tomcat/conf/server.xml -->
<Connector ... ... URIEcoding="UTF-8">
```

### Request parameter (param in body)
```java
req.setCharacterEncoding("UTF-8")
```

### Response
```java
// response content encoded using utf-8 by servlet
resp.setCharacterEncoding("UTF-8")
// client decodes response using utf-8
resp.setContentType("text/html;charset=UTF-8")
```

## Path
### Frontend
**Frontend path is base on directory of the current resource**
**Backend path is base on server name**
```
http://localhost:8080/hello_servlet2_war_exploded/pathtest/view
=> name of current resource: view
=> directory of current resource: http://localhost:8080/hello_servlet2_war_exploded/pathtest
=> server name: http://localhost:8080
```
1. relative path
```java
@WebServlet(name = "/pathtestview", urlPatterns = "/pathtest/view")
```
```html
<img src="../static/img/deformableDETR.png" alt="pic not found"/>
```
1. absolute path
```html
<img src="/hello_servlet2_war_exploded/static/img/deformableDETR.png" alt="pic not found"/>
```
### Backend
**relative path always references from the path of the most current requested resource**.
1. redirect path reference is the same as frondend. Because of references from the **current requested resource**, the 2nd requested resource is the redirected resource by the 1st requested resource by returning code 302, and after receiveing code 302, the current requested resource now is the 1st resource, then following request will reference from the directory of the 1st resource.
2. forward: relative path reference is the same as frondend,
```java
// absolute path
// absolute path of servlet forward get by method getRequestDispatcher does not need app name().
req.getRequestDispatcher("/WEB-INF/view/view.html").forward(req, resp);
```