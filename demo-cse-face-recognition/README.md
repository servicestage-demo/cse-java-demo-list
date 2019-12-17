# Servicecomb结合人脸识别API实现人脸识别

#### 一、配置microservice.yaml

##### 1. 获取服务注册发现地址 

示例：

https://cse.xxx.myhuaweicloud.com:port

##### 2. 获取AK、SK

参考网址：[获取AK/SK](https://support.huaweicloud.com/devg-apisign/api-sign-provide.html#section3)

##### 3. microservice.yaml示例

```
APPLICATION_ID: demo-face-recognition
service_description:
# name of the declaring microservice
  name: demo-face-recognition
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
        default: tracing-provider
```

#### 二、配置application.properties

```
#图片保存路径
face_img_path=/usr/gc/img/
#获取token参数
face_token_url=https://xxx.myhuaweicloud.com/v3/auth/tokens
face_token_username=username
face_token_password=password
face_token_domain=domain
face_token_project=project
#搜索人脸API
face_api_search=https://face.xxx.myhuaweicloud.com/v1/xxx/face-sets/test/search
#增加人脸API
face_api_add=https://face.xxx.myhuaweicloud.com/v1/xxx/face-sets/test/faces
#删除人脸API
face_api_del=https://face.xxx.myhuaweicloud.com/v1/xxx/face-sets/test/faces?external_image_id=
```

[Token认证鉴权指南](https://support.huaweicloud.com/api-modelarts/modelarts_03_0004.html)

[如何调用人脸识别API](<https://support.huaweicloud.com/api-face/face_02_0006.html> )

[创建人脸库API参考](https://support.huaweicloud.com/api-face/face_02_0031.html)

[删除人脸库API参考](https://support.huaweicloud.com/api-face/face_02_0034.html)

[查询人脸库API参考](https://support.huaweicloud.com/api-face/face_02_0033.html)

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
                        \---face
        \---resources
    +   pom.xml
    +   ReadMe.md
    +   VERSION

目录说明:

1. src 为示例代码目录
2. resources为配置文件目录

#### 四. 调用示例
请求URL：http://xxx.xxx.213.158:8081/face

请求方式：POST

参数类型：body

参数示例：form-data (key:file, value:[图片文件])

返回示例：{\"姓名\":\"小明\", \"性别\":\"男\", \"部门\":\"华为云服务\"}