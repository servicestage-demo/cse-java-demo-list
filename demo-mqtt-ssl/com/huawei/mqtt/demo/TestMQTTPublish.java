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
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

/**
 * MQTT 发布类
 */
public class TestMQTTPublish {
	private static String broker = "ssl://x.x.x.x:8883";
	private static String topic = "topic-ssl";
	private static String msg = "Hello World!";
	
	private static MqttClient client;
	private static String clientId = String.valueOf(Math.random());
	
	private static String CLIENT_KEY_STORE = "D:/fugaoyang/jks3/rr.jks";
    private static String CLIENT_KEY_STORE_PASSWORD = "password";
	
	private static Logger log = LogManager.getLogger(TestMQTTPublish.class); 
	
	public static void main(String[] args){
		try {
			// 初始化mqtt发布端
			client = new MqttClient(broker, clientId, new MemoryPersistence());
		} catch (MqttException e) {
			log.error("client init failed, error:{}", e.getMessage());
		}
		
        // 连接服务器
        connect();
        
        MqttMessage message = new MqttMessage();
        message.setPayload(msg.getBytes());
        //设置消息传输的类型 0,1,2可选
        message.setQos(0);
        //设置服务器是否保存消息
        message.setRetained(false);
        
        try {
			client.publish(topic, message);
		} catch (MqttPersistenceException e) {
			log.error("publish failed, error:{}", e.getMessage());
		} catch (MqttException e) {
			log.error("publish failed, error:{}", e.getMessage());
		}
    }

	private static void connect() {
        MqttConnectOptions options = new MqttConnectOptions();
        // 设置是否清空session
        options.setCleanSession(true);
        // 设置超时时间
        options.setConnectionTimeout(20);
        // 设置会话心跳时间
        options.setKeepAliveInterval(10);
        // 设置自动重连
        options.setAutomaticReconnect(true);
        
        try {
            options.setSocketFactory(getSocketFactory());
            client.setCallback(new MqttCallbackExtended() {
            	public void connectionLost(Throwable cause) {
                    // 连接丢失后，一般在这里面进行重连
            		log.info("publish callback connect lost");
                }

                public void deliveryComplete(IMqttDeliveryToken token) {
                	log.info("delivery complete: " + token.isComplete());
                }

                public void messageArrived(String topic, MqttMessage message) throws Exception {
                	log.info("message arrived, topic:{}, msg:{}", topic, msg);
                }

                public void connectComplete(boolean b, String s) {
                	log.info("connect completed");
                }});
            
            client.connect(options);
        } catch (Exception e) {
        	log.error("connect failed, error:{}", e.getMessage());
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
