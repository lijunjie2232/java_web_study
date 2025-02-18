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

# Maven Command+
