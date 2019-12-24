# ServiceComb日志最佳实践

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

#### Log4j日志

##### 1、配置log4j2.xml（与microservice.yaml同目录）

```
<?xml version="1.0" encoding="UTF-8"?>
<!--日志级别以及优先级排序: OFF > FATAL > ERROR > WARN > INFO > DEBUG > TRACE > ALL -->
<!--Configuration后面的status，这个用于设置log4j2自身内部的信息输出，可以不设置，当设置成trace时，你会看到log4j2内部各种详细输出-->
<!--monitorInterval：Log4j能够自动检测修改配置 文件和重新配置本身，设置间隔秒数-->
<configuration status="WARN" monitorInterval="30">
    <!--先定义所有的appender-->
    <appenders>
    <!--这个输出控制台的配置-->
        <console name="Console" target="SYSTEM_OUT">
        <!--输出日志的格式-->
            <PatternLayout pattern="[%d{HH:mm:ss:SSS}] [%p] - %l - %m%n"/>
        </console>
    <!--文件会打印出所有信息，这个log每次运行程序会自动清空，由append属性决定，这个也挺有用的，适合临时测试用-->
    <File name="log" fileName="log/servicecomb_test.log" append="false">
       <PatternLayout pattern="%d{HH:mm:ss.SSS} %-5level %class{36} %L %M - %msg%xEx%n"/>
    </File>
    <!-- 这个会打印出所有的info及以下级别的信息，每次大小超过size，则这size大小的日志会自动存入按年份-月份建立的文件夹下面并进行压缩，作为存档-->
        <RollingFile name="RollingFileInfo" fileName="${sys:user.home}/logs/servicecomb_info.log"
                     filePattern="${sys:user.home}/logs/$${date:yyyy-MM}/info-%d{yyyy-MM-dd}-%i.log">
            <!--控制台只输出level及以上级别的信息（onMatch），其他的直接拒绝（onMismatch）-->        
            <ThresholdFilter level="info" onMatch="ACCEPT" onMismatch="DENY"/>
            <PatternLayout pattern="[%d{HH:mm:ss:SSS}] [%p] - %l - %m%n"/>
            <Policies>
                <TimeBasedTriggeringPolicy/>
                <SizeBasedTriggeringPolicy size="100 MB"/>
            </Policies>
        </RollingFile>
        <RollingFile name="RollingFileWarn" fileName="${sys:user.home}/logs/servicecomb_warn.log"
                     filePattern="${sys:user.home}/logs/$${date:yyyy-MM}/warn-%d{yyyy-MM-dd}-%i.log">
            <ThresholdFilter level="warn" onMatch="ACCEPT" onMismatch="DENY"/>
            <ThresholdFilter level="warn" onMatch="ACCEPT" onMismatch="DENY"/>
            <PatternLayout pattern="[%d{HH:mm:ss:SSS}] [%p] - %l - %m%n"/>
            <Policies>
                <TimeBasedTriggeringPolicy/>
                <SizeBasedTriggeringPolicy size="100 MB"/>
            </Policies>
        <!-- DefaultRolloverStrategy属性如不设置，则默认为最多同一文件夹下7个文件，这里设置了20 -->
            <DefaultRolloverStrategy max="20"/>
        </RollingFile>
        <RollingFile name="RollingFileError" fileName="${sys:user.home}/logs/servicecomb_error.log"
                     filePattern="${sys:user.home}/logs/$${date:yyyy-MM}/error-%d{yyyy-MM-dd}-%i.log">
            <ThresholdFilter level="error" onMatch="ACCEPT" onMismatch="DENY"/>
            <PatternLayout pattern="[%d{HH:mm:ss:SSS}] [%p] - %l - %m%n"/>
            <Policies>
                <TimeBasedTriggeringPolicy/>
                <SizeBasedTriggeringPolicy size="100 MB"/>
            </Policies>
        </RollingFile>
    </appenders>
    <!--然后定义logger，只有定义了logger并引入的appender，appender才会生效-->
    <loggers>
        <!--过滤掉spring和mybatis的一些无用的DEBUG信息-->
        <logger name="org.springframework" level="INFO"></logger>
        <logger name="org.mybatis" level="INFO"></logger>
        <root level="all">
            <appender-ref ref="Console"/>
            <appender-ref ref="RollingFileInfo"/>
            <appender-ref ref="RollingFileWarn"/>
            <appender-ref ref="RollingFileError"/>
        </root>
    </loggers>
</configuration>
```

##### 2、调用接口，生成日志文件

```
#调用示例
http://127.0.0.1:9080/hello
http://127.0.0.1:9080/say
#查看日志文件（C:\Users\username\logs）
servicecomb_info.log
servicecomb_warn.log
servicecomb_error.log
#日志示例
[10:34:02:676] [WARN] - com.huawei.cse.logger.sample.HelloImpl.hello(HelloImpl.java:35) - This is Hello World!
[10:34:10:583] [WARN] - com.huawei.cse.logger.sample.HelloImpl.say(HelloImpl.java:42) - This is Say Hello World!
```

#### 调用链日志

[调用链配置](https://docs.servicecomb.io/java-chassis/zh_CN/general-development/customized-tracing.html)

[调用链示例](https://github.com/servicestage-demo/cse-java-demo-list/tree/master/demo-zipkin-tracing)

