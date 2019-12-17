# Servicecomb使用Mybatis

#### 一、配置microservice.yaml

##### 1. 获取服务注册发现地址 

示例：

https://cse.xxx.myhuaweicloud.com:port

##### 2. 获取AK、SK

参考网址：[获取AK/SK](https://support.huaweicloud.com/devg-apisign/api-sign-provide.html#section3)

##### 3. microservice.yaml示例

```
APPLICATION_ID: cse-mybatis-app
service_description:
# name of the declaring microservice
  name: cse-mybatis-app
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

#### 二. 配置jdbc.properties

```
driver=com.mysql.jdbc.Driver
url=jdbc:mysql://localhost:3306/test
username=root
password=root
```


#### 三. 目录结构说明

    +---lib
    \---src
        \---org
            \---apache
                \---servicecomb
                    \---samples
                        \---mybatis
        \---resources
        	\---mapper
    +   pom.xml
    +   ReadMe.md
    +   VERSION

目录说明:

1. src 为示例代码目录
2. resources为配置文件目录
3. mapper为sql语句配置目录

#### 四. 调用示例
GET http://127.0.0.1:8081/user

Response：

[

​    {

​        "sid": 1,

​        "name": "小明",

​        "gender": "男",

​        "department": "华为云服务"

​    }

]