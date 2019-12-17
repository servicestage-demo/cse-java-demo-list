## 使用证书进行安全认证Java实例

### 1 制作JKS证书

#### 1.1 在边缘节点页面，点击“添加证书”，即可下载证书

#### 1.2 证书包括3个文件

ca.crt、private_cert.crt、private_cert.key

#### 1.3 使用JDK自带工具keytool制作证书

##### 1.3.1 使用openssl命令制作p12证书

openssl pkcs12 -export -in /opt/fugaoyang/ccc/fib3az2sgq_private_cert.crt -inkey /opt/fugaoyang/ccc/fib3az2sgq_private_cert.key -out /opt/fugaoyang/ccc/mycert.p12 -name PrivateKey -CAfile /opt/fugaoyang/ccc/fib3az2sgq_ca.crt -caname root –chain

##### 1.3.2 p12证书转jks证书

keytool –import keystore -v -srckeystore mycert.p12 -srcstoretype PKCS12 -srcstorepass password -destkeystore xx.jks -deststoretype jks -deststorepass password

##### 1.3.3 导入ca.crt证书

keytool -import -alias CaKey -file fib3az2sgq_ca.crt -keystore xx.jks

##### 1.3.4 使用openssl命令获取server端证书，拷贝证书内容，保存为文件server.crt

openssl s_client -connect x.x.x.x:8883 -showcerts

##### 1.3.5 导入server.crt证书

keytool -import -alias CaKey -file server.crt -keystore xx.jks

##### 1.3.6 查看证书，可以看到有1个private证书，2个信任证书，至此证书制作完成

keytool -list -keystore xx.jks

### 2 编写发布端代码

TestMQTTPublish.java

### 3 编写订阅端代码

TestMQTTSubcribe.java

### 4 启动订阅端，再启动发布端，即可看到消息成功发送与接收

topic-ssl--Hello World!