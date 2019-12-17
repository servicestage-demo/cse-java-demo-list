# CSE支持冒号格式的Rest规范

##### 参考链接：

https://docs.servicecomb.io/java-chassis/zh_CN/edge/by-servicecomb-sdk.html

这是我测试的例子，是支持url带冒号的：
demo地址：<https://github.com/apache/servicecomb-java-chassis/tree/master/demo/demo-edge>

##### 例子一：

1、修改url为带冒号格式

```
@RequestMapping(path = "/x:{x}/y:{y}/add", method = RequestMethod.GET)
public ResultWithInstance add(@PathVariable("x") int x, @PathVariable("y") int y) {
    return ResultWithInstance.create(x + y);
}
```

2、修改microservice.yaml微服务版本，接口有变更时，需要增加版本

```
APPLICATION_ID: edge
service_description:
  name: business
  version: 1.1.2
servicecomb:
  service:
    registry:
      address: http://127.0.0.1:30100
  rest:
    address: 127.0.0.1:8090
    server:
      verticle-count: 1
```

3、访问[http://127.0.0.1:8090/business/v1/x:1/y:2/add，能正常返回](http://127.0.0.1:8090/business/v1/x:1/y:2/add%EF%BC%8C%E8%83%BD%E6%AD%A3%E5%B8%B8%E8%BF%94%E5%9B%9E)

```
{
    "result":3,
    "serviceId":"xxx...",
    "instanceId:"xxx...",
    "version":"1.1.2"
}
```

##### 例子二：

如果path是这种格式/myadd/{name}/test，并且name参数带冒号，则需要将冒号转成%3A

我这边测试，%3A是支持的，后台能正常获取到name参数，浏览器+postman都支持 

1、接口代码：

```
@RequestMapping(path = "/myadd/{name}/test", method = RequestMethod.GET) 
public ResultWithInstance myadd(@PathVariable String name) { 
	System.out.println(name); 
	return ResultWithInstance.create(1); 
}
```

2、请求接口：

 <http://127.0.0.1:8080/business/v1/myadd/uuuu%3A3433/test> 

3、控制台打印：

```
2019-12-17 09:04:56,522 [WARN] sc task postponed for 31196ms for some reason... 

uuuu:3433 
```

