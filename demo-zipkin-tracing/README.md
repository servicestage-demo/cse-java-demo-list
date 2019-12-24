### ServiceComb对接调用链案例

参考链接：

[分布式调用链追踪](https://docs.servicecomb.io/java-chassis/zh_CN/general-development/customized-tracing.html)

[基于ServiceComb和Zipkin的分布式调用链追踪](http://servicecomb.apache.org/cn/docs/tracing-with-servicecomb/)

#### 创建工程

##### 1、首先在本地搭建ServiceComb工程

为了能够使开发者可以快速构建ServiceComb应用程序，官方为我们提供了一套脚手架，这样能够方便学习者及应用开发者快速入门，同时极大的提高了效率。

快速开发引导页：http://start.servicecomb.io/

填写好工程group、artifact等信息（ServiceComb参数可以使用默认），就可以生成一个ServiceComb工程了，将工程下载到本地，导入到Eclipse或者IDEA等开发工具中。

##### 2、配置microservice.yaml

servicecomb.service.registry.address：CSE（微服务引擎）服务注册发现地址

servicecomb.rest.address：本地应用访问地址

AK、SK获取请参考链接：https://support.huaweicloud.com/devg-apisign/api-sign-provide.html#section3

```
APPLICATION_ID: servicecomb.io.zipkin
service_description:
# name of the declaring microservice
  name: HelloServiceComb
  version: 0.0.1
  environment: development
servicecomb:
  service:
    registry:
      address: https://cse.xxx.myhuaweicloud.com:port
  rest:
    address: 0.0.0.0:8081
  credentials:
    accessKey: Your AK
    secretKey: Your SK
    project: cn-north-1
    akskCustomerCipher: default
  handler:
    chain:
      Provider:
        default: bizkeeper-provider,tracing-provider
```

也可以使用本地注册中心，到官网http://servicecomb.apache.org/cn/release/下载ServiceComb Service-Center（选最新版本）

本地microservice.yaml配置参考：

```
APPLICATION_ID: servicecomb.io.zipkin
service_description:
  name: HelloServiceComb
  version: 0.0.2
servicecomb:
  handler:
    chain:
      Provider:
        default: bizkeeper-provider,tracing-provider
  rest:
    address: 0.0.0.0:9080
  service:
    registry:
      address: http://127.0.0.1:30100
```

#### 概念阐述

分布式调用链追踪提供了服务间调用的时序信息，但服务内部的链路调用信息对开发者同样重要，如果能将两者合二为一，就能提供更完整的调用链，更容易定位错误和潜在性能问题。

#### 前提条件

使用自定义打点功能需要首先配置并启用Java Chassis微服务调用链。

#### 注意事项

使用@Span注释的自定义打点功能只支持和Java Chassis调用请求同一线程的方法调用。
添加@Span注释的方法必须是Spring管理的Bean，否则需要按这里提到的方法配置。

#### 自定义调用链打点

该功能集成了Zipkin，提供@Span注释为需要追踪的方法自定义打点。Java Chassis将自动追踪所有添加@Span注释的方法，把每个方法的本地调用信息与服务间调用信息连接起来。

#### 使用步骤:

##### 1、添加依赖

基于 ServiceComb Java Chassis 的微服务只需要添加如下依赖到 pom.xml：

    <dependency>
      <groupId>org.apache.servicecomb</groupId>
      <artifactId>tracing-zipkin</artifactId>
    </dependency>
##### 2、启用自定义打点功能

在应用入口或Spring配置类上添加@EnableZipkinTracing注释：

```java
@SpringBootApplication
@EnableZipkinTracing
public class ZipkinSpanTestApplication {
  public static void main(String[] args) {
    SpringApplication.run(ZipkinSpanTestApplication.class);
  }
}
```

##### 3、定制打点

在需要定制打点的方法上添加@Span注释：

```
package com.huawei.cse.logger.sample;

import org.apache.servicecomb.provider.rest.common.RestSchema;
import org.apache.servicecomb.tracing.Span;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RestSchema(schemaId = "hello")
@RequestMapping(path = "/")
public class HelloImpl {

	@Span
    @GetMapping(path = "/hello")
    public String hello() {
        return "Hello World!";
    }
	
	@Span(spanName = "say_method", callPath = "hello_say_path")
    @GetMapping(path = "/say")
    public String say() {
        return "Say Hello World!";
    }
	
}
```


就这样，通过使用@Span注释，我们启动了基于 Zipkin 的自定义打点功能。

##### 4、定制上报的数据

通过自定义打点上报的调用链包含两条数据：

span name 默认为当前注释的方法全名。
call.path 默认为当前注释的方法签名。
例如，上述例子SlowRepoImp里上报的数据如下：

| key       | value                               |
| :-------- | ----------------------------------- |
| span name | crawl                               |
| call.path | public abstract java.lang.String... |

如果需要定制上报的数据内容，可以传入自定义的参数：

```
@Span(spanName = "say_method", callPath = "hello_say_path")
@GetMapping(path = "/say")
public String say() {
    return "Say Hello World!";
}
```

##### 5、调用接口，产生zipkin日志

```
#调用示例
http://127.0.0.1:9080/hello
http://127.0.0.1:9080/say
#查看日志
http://127.0.0.1:9411/zipkin
```

