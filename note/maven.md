# Config
## settings
```xml
<settings>
    <localRepository>C:\Users\25335\.m2\repository</localRepository>
</settings>
```
## mirrors
```xml
<mirrors>
    <mirror>
      <id>huaweicloud</id>
      <mirrorOf>*</mirrorOf>
      <url>https://repo.huaweicloud.com/repository/maven/</url>
    </mirror>
    <mirror>
      <id>alimaven</id>
      <name>aliyun maven</name>
      <url>http://maven.aliyun.com/nexus/content/groups/public/</url>
      <mirrorOf>central</mirrorOf>
    </mirror>
    <mirror>
      <id>alimaven</id>
      <mirrorOf>central</mirrorOf>
      <name>aliyun maven</name>
      <url>http://maven.aliyun.com/nexus/content/repositories/central/</url>
    </mirror>
</mirrors>
```
## default JDK version
```xml
<profiles>
    <profile>
        <id>jdk-17</id>
        <activation>
            <jdk>17</jdk>
            <activeByDefault>true</activeByDefault>
        </activation>
        <properties>
            <maven.compiler.source>17</maven.compiler.source>
            <maven.compiler.target>17</maven.compiler.target>
            <maven.compiler.compilerVersion>17</maven.compiler.compilerVersion>
            <maven.compiler.release>17</maven.compiler.release>
            <maven.compiler.verbose>true</maven.compiler.verbose>
        </properties>
    </profile>
</profiles>
```

# Maven Artifact
- groupid: package name, for example: com.example.demo
- artifactid: project or module id, for example: student-management
- version: version name, for example: 1.0.0-SNAPSHOT

```xml
<groupId>com.example.demo</groupId>
<artifactId>student-management</artifactId>
<version>1.0.0-SNAPSHOT</version>
```

- jar package path in maven repo: `${localRepository}/com/example/demo/student-management/1.0.0-SNAPSHOT/student-management-1.0.0-SNAPSHOT.jar`

# Maven Usage
- `mvn [pluging]:[options]`

## Create Project
- `mvn archetype:generate`
  - -DgroupId=com.example.demo 
  - -DartifactId=student-management
  - -DarchetypeArtifactId=maven-archetype-quickstart
  - -DarchetypeVersion=1.4
  - -DinteractiveMode=false


### archetypeArtifactId
- archetypeArtifactId 用于创建 Java 项目结构

以下是几种常用的 archetypeArtifactId：

- maven-archetype-quickstart: 创建一个简单的 Java 项目。
- maven-archetype-webapp: 创建一个基于 Servlet 的 Web 应用程序。
- maven-archetype-j2ee-simple: 创建一个简单的 J2EE 项目。
- maven-archetype-site: 创建一个站点项目。

### pom.xml
```xml
<?xml version="1.0" encoding="UTF-8"?>
<!-- config of project -->
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
  <modelVersion>4.0.0</modelVersion>

  <!-- groupId: package name, means a project of an organization name -->
  <groupId>com.example.demo</groupId>
  <!-- artifactId: module id under one project -->
  <artifactId>student-management</artifactId>
  <!-- version: module version -->
  <version>1.0-SNAPSHOT</version>
  <!-- packaging: jar or war -->
  <packaging>jar</packaging>

  <name>student-management</name>
  <!-- FIXME change it to the project's website -->
  <url>http://www.example.com</url>

  <!-- properties in maven -->
  <properties>
    <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    <maven.compiler.release>17</maven.compiler.release>
  </properties>

</project>

```

### scope of a dependency
- compile: 默认范围，对所有阶级都有效，包括编译、测试和运行时。
- runtime: 只在运行时和测试时有效，比如JDBC驱动程序适用于此范围。
- test: 只在测试编译和执行阶段有效，主要用于测试代码，例如 JUnit
- provided: 类似compile，但表示JAR由运行时环境提供，如Servlet API，在编译时需要但在运行时由容器提供。
- import: 用于导入其他 pom 文件的依赖
- system: 类似于provided，但是需要显式指定依赖的路径。

