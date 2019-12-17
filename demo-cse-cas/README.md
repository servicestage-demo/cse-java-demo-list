# Servicecomb使用cas实现单点登录

#### 一、配置microservice.yaml

##### 1. 获取服务注册发现地址 

示例：

https://cse.xxx.myhuaweicloud.com:port

##### 2. 获取AK、SK

参考网址：[获取AK/SK](https://support.huaweicloud.com/devg-apisign/api-sign-provide.html#section3)

##### 3. microservice.yaml示例

```
APPLICATION_ID: demo-cse-cas
service_description:
# name of the declaring microservice
  name: demo-cse-cas
  version: 0.0.1
  environment: development
servicecomb:
  service:
    registry:
      address: https://cse.xxx.myhuaweicloud.com:port
  rest:
    address: 0.0.0.0:9002
  credentials:
    accessKey: Your AK
    secretKey: Your SK
    project: cn-north-1
    akskCustomerCipher: default
  handler:
    chain:
      Provider:
        default: bizkeeper-provider
```

#### 二. 配置application.properties

```
cas.server-url-prefix=http://cas.server.com:8443/cas
cas.server-login-url=http://cas.server.com:8443/cas/login
cas.client-host-url=http://cas.client.com:9002
cas.use-session=true
cas.validation-type=cas
server.port=9002
#http://cas.client.com:9002/logout/success
casClientLogoutUrl=http://cas.server.com:8443/cas/logout?service=http://cas.client.com:9002/logout/success
```

#### 三、配置log4j.properties

```
# LOG4J配置
log4j.rootCategory=INFO,stdout
# 控制台输出
log4j.appender.stdout=org.apache.log4j.ConsoleAppender 
log4j.appender.stdout.layout=org.apache.log4j.PatternLayout 
log4j.appender.stdout.layout.ConversionPattern=%d{yyyy-MM-dd HH:mm:ss,SSS} %5p %c{1}:%L - %m%n
```

#### 四. 目录结构说明

    +---lib
    \---src
        \---org
            \---apache
                \---servicecomb
                    \---samples
                        \---cas
        \---resources
    +   pom.xml
    +   ReadMe.md
    +   VERSION

目录说明:

1. src 为示例代码目录
2. resources为配置文件目录

#### 四. 调用示例
http://cas.server.com:8443/cas/login