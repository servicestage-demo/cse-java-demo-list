package demo;

import java.io.FileInputStream;
import java.security.KeyStore;

import javax.net.SocketFactory;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttSecurityException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class TestMQTTSubcribe {
	private static String broker = "ssl://x.x.x.x:8883";
	private static String topic = "topic-ssl";
	
	private static MqttClient client;
	private static String clientId = String.valueOf(Math.random());
	
	private static String CLIENT_KEY_STORE = "D:/fugaoyang/jks3/rr.jks";
    private static String CLIENT_KEY_STORE_PASSWORD = "password";
	
	private static Logger log = LogManager.getLogger(TestMQTTSubcribe.class); 
	
	public static void main(String[] args){
	try {
		// 初始化mqtt订阅端
		client = new MqttClient(broker, clientId, new MemoryPersistence());
	} catch (MqttException e) {
		log.error("client init failed, error:{}", e.getMessage());
	}
	
	// 连接服务器
    connect();
    
	
 // 订阅消息
    String[] topicArr = { topic };
    try {
		client.subscribe(topicArr);
	} catch (MqttException e) {
		log.error("subscribe failed, error:{}", e.getMessage());
	}
}

	private static void connect() {
		// MQTT的连接设置
		MqttConnectOptions options = new MqttConnectOptions();
		// 设置是否清空session,这里如果设置为false表示服务器会保留客户端的连接记录，设置为true表示每次连接到服务器都以新的身份连接
		options.setCleanSession(false);
		// 设置超时时间 单位为秒
		options.setConnectionTimeout(20);
		// 设置会话心跳时间 单位为秒 服务器会每隔5秒的时间向客户端发送个消息判断客户端是否在线，但这个方法并没有重连的机制
		options.setKeepAliveInterval(10);
		options.setAutomaticReconnect(true);// 设置自动重连
		
		// 设置回调
		try {
			options.setSocketFactory(getSocketFactory());
		} catch (Exception e) {
			log.error("get socket failed, error:{}", e.getMessage());
		}
		client.setCallback(new MqttCallbackExtended() {
			public void connectionLost(Throwable cause) {
				log.info("connection lost");
			}

			public void messageArrived(String topic, MqttMessage message) throws Exception {
				log.info("message arrived");
				System.out.println(topic+"--"+new String(message.getPayload()));
			}

			public void deliveryComplete(IMqttDeliveryToken token) {
				log.info("delivery complete");
			}

			public void connectComplete(boolean reconnect, String serverURI) {
				log.info("connect complete");
			}});
		
		try {
			client.connect(options);
		} catch (MqttSecurityException e) {
			log.error("publish failed, error:{}", e.getMessage());
		} catch (MqttException e) {
			log.error("publish failed, error:{}", e.getMessage());
		}
	}
	
	public static SocketFactory getSocketFactory() throws Exception {
        KeyStore ks = KeyStore.getInstance("jks");
        ks.load(new FileInputStream(CLIENT_KEY_STORE), CLIENT_KEY_STORE_PASSWORD.toCharArray());
        KeyManagerFactory kf = KeyManagerFactory.getInstance("SunX509");
        kf.init(ks, CLIENT_KEY_STORE_PASSWORD.toCharArray());
        TrustManagerFactory tf = TrustManagerFactory.getInstance("SunX509");
        tf.init(ks);
        SSLContext context = SSLContext.getInstance("TLS");
        context.init(kf.getKeyManagers(), tf.getTrustManagers(), null);

        SocketFactory f = context.getSocketFactory();
        return f;
    }
}
