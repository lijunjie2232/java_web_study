# Tomcat

## project structure

```
tomcat/webapps
└─appName
  ├─xxx.html
  ├─static
  │ ├─css
  │ ├─img
  │ └─js
  └─WEB-INF
    ├─classes
    ├─lib
    ├─...
    └─web.xml
```



- appName: application root (html and static resource)

- WEB-INF: resource protected from browser(including jsp)

## How to deploy a web application to tomcat

- method 1: copy directory or war package into ```tomcat/webapps/appName```

- method 2: create a appName.xml at ```tomcat/conf/Catalina/localhost/``` with following content:

  ```xml
  <Context path="/[appRoute]" docBase="[path_to_appName]"
  ```
