# Servicecomb结合ModelArts模型实现垃圾分类识别

#### 一、配置microservice.yaml

##### 1. 获取服务注册发现地址 

示例：

https://cse.xxx.myhuaweicloud.com:port

##### 2. 获取AK、SK

参考网址：[获取AK/SK](https://support.huaweicloud.com/devg-apisign/api-sign-provide.html#section3)

##### 3. microservice.yaml示例

```
APPLICATION_ID: demo-garbage-classify
service_description:
# name of the declaring microservice
  name: demo-garbage-classify
  version: 0.0.1
  environment: development
servicecomb:
  service:
    registry:
      address: https://xxx.myhuaweicloud.com:port
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
        default: tracing-provider
```

#### 二、配置application.properties

```
#图片保存在服务器路径
gc_img_path=/usr/gc/img/
#获取token
gc_token_url=https://xxx.myhuaweicloud.com/v3/auth/tokens
gc_token_username=username
gc_token_password=password
gc_token_domain=domain
gc_token_project=project
#ModelArts API接口地址
gc_api_url=ModelArts_api_url
```

[ModelArts使用指南](https://support.huaweicloud.com/engineers-modelarts/modelarts_23_0001.html)

[Token认证鉴权指南](https://support.huaweicloud.com/api-modelarts/modelarts_03_0004.html)

#### 三. 配置log4j2.xml

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


#### 三. 目录结构说明

    +---lib
    \---src
        \---org
            \---apache
                \---servicecomb
                    \---samples
                        \---gc
        \---resources
    +   pom.xml
    +   ReadMe.md
    +   VERSION

目录说明:

1. src 为示例代码目录
2. resources为配置文件目录

#### 四. 调用示例
请求URL：http://xxx.xxx.213.158:8081/gc

请求方式：POST

参数类型：body

参数示例：form-data (key:file, value:[图片文件])

返回示例：{"result": "可回收物/易拉罐"}