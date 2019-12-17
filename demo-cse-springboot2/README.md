### CSE升级SpringBoot2、Spring依赖版本Demo

#### 1、示例版本

将以下三个依赖配置在dependencyManagement标签中。

org.springframework.boot:spring-boot-starter-parent:2.1.6.RELEASE
org.springframework:spring-core:5.1.8.RELEASE
org.apache.servicecomb:java-chassis-dependencies-springboot2: 1.1.0.B018
com.huawei.paas.cse:cse-dependency:2.3.46

注意：spring-boot-starter-parent、spring-core依赖需要配置在java-chassis-dependencies-springboot2之前，原因：chassis内部依赖可能是比较旧的版本，先行声明将覆盖旧版本。



##### 依赖关系参考文章：

使用maven管理复杂依赖关系的技巧：

http://servicecomb.apache.org/cn/docs/maven_dependency_management/

CSE maven组件依赖关系配置参考：

https://bbs.huaweicloud.com/blogs/103473/



#### 2、查看版本

配置完成后，可以点击pom.xml文件查看依赖版本。



#### 3、增加@EnableServiceComb注解

启动类Application增加@EnableServiceComb注解

```
package org.apache.servicecomb.samples.boot.web;

import org.apache.servicecomb.springboot2.starter.EnableServiceComb;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableServiceComb
@SpringBootApplication
public class Application {
	public static void main(final String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
```



#### 4、查看启动日志


启动日志无报错，打印SpringBoot版本为：2.1.6.RELEASE

```
  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::        (v2.1.6.RELEASE)

2019-12-14 15:02:57.429  INFO 344 --- [           main] o.a.s.samples.boot.web.Application...
```



#### 5、访问示例


访问URL：http://localhost:9093/hello?name=888

端口9093在微服务配置文件microservice.yaml中定义

返回示例：

```
"Hello 888"
```



#### 6、microservice.yaml参考

```
cse-config-order: 100
APPLICATION_ID: spring-boot
service_description:
  name: spring-boot-simple
  version: 0.0.5
servicecomb:
  service:
    registry:
      address: http://localhost:30100
  rest:
    address: 0.0.0.0:9093
spring:
    main:
      web-application-type: none
```



#### 7、pom.xml参考

    <?xml version="1.0" encoding="UTF-8"?>
    <project xmlns="http://maven.apache.org/POM/4.0.0"
          xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
          xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
          
      <modelVersion>4.0.0</modelVersion>
      <groupId>org.apache.servicecomb.sample</groupId>
      <artifactId>spring-boot-simple</artifactId>
      <version>0.0.1-SNAPSHOT</version>
      <packaging>jar</packaging>
     
     <properties>
                <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
                <project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>
          </properties>
     
          <dependencyManagement>
                <dependencies>
                      <dependency>
                            <groupId>org.springframework.boot</groupId>
                            <artifactId>spring-boot-starter-parent</artifactId>
                            <version>2.1.6.RELEASE</version>
                            <type>pom</type>
                            <scope>import</scope>
                      </dependency>
                      <dependency>
                            <groupId>org.springframework</groupId>
                            <artifactId>spring-core</artifactId>
                            <version>5.1.8.RELEASE</version>
                      </dependency>
                      <dependency>
                            <groupId>org.apache.servicecomb</groupId>
                            <artifactId>java-chassis-dependencies-springboot2</artifactId>
                            <version>1.1.0.B018</version>
                            <type>pom</type>
                            <scope>import</scope>
                      </dependency>
                      <dependency>
                            <groupId>com.huawei.paas.cse</groupId>
                            <artifactId>cse-dependency</artifactId>
                            <version>2.3.46</version>
                            <type>pom</type>
                            <scope>import</scope>
                      </dependency>
                </dependencies>
          </dependencyManagement>
     
          <dependencies>
                <dependency>
                      <groupId>com.huawei.paas.cse</groupId>
                      <artifactId>cse-solution-service-engine</artifactId>
                      <exclusions>
                            <exclusion>
                                  <groupId>org.slf4j</groupId>
                                  <artifactId>slf4j-log4j12</artifactId>
                            </exclusion>
                      </exclusions>
                </dependency>
                <dependency>
                      <groupId>org.apache.servicecomb</groupId>
                      <artifactId>spring-boot2-starter-servlet</artifactId>
                </dependency>
          </dependencies>
     
          <build>
                <plugins>
                      <plugin>
                            <groupId>org.apache.maven.plugins</groupId>
                            <artifactId>maven-compiler-plugin</artifactId>
                            <configuration>
                                  <source>1.8</source>
                                  <target>1.8</target>
                            </configuration>
                      </plugin>
     
                      <plugin>
                            <groupId>org.springframework.boot</groupId>
                            <artifactId>spring-boot-maven-plugin</artifactId>
                      </plugin>
                </plugins>
          </build>
    </project>