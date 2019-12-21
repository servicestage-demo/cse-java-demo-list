# ServiceComb日志最佳实践

#### Log4j日志

[log4j2配置]https://www.cnblogs.com/hafiz/p/6170702.html

配置在log4j2.xml

```
<?xml version="1.0" encoding="UTF-8"?>
<Configuration status="WARN">
    <Appenders>
        <RollingFile name="RollingFile" fileName="${sys:user.home}/logs/dtm.log"
                     filePattern="${sys:user.home}/logs/$${date:yyyy-MM}/error-%d{yyyy-MM-dd}-%i.log">
            <PatternLayout pattern="[%d{HH:mm:ss:SSS}] [%p] - %l - %m%n"/>
            <Policies>
                <TimeBasedTriggeringPolicy/>
                <SizeBasedTriggeringPolicy size="100 MB"/>
            </Policies>
        </RollingFile>
    </Appenders>
    <Loggers>
        <Root level="debug">
            <AppenderRef ref="RollingFile"/>
        </Root>
    </Loggers>
</Configuration>
```



#### 调用链日志

[调用链配置]https://docs.servicecomb.io/java-chassis/zh_CN/general-development/customized-tracing.html

[调用链示例]https://github.com/servicestage-demo/cse-java-demo-list/tree/master/demo-zipkin-tracing

