package org.apache.servicecomb.samples.mybatis;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTests {

	@Test
	public void user() {
		// 此测试用例需要先启动本地微服务引擎
		// Local-CSE下载地址:https://support.huaweicloud.com/productdesc-servicestage/cse_productdesc_0012.html
		String result = sendGet("http://127.0.0.1:9080/user", null);
		Assert.assertEquals("[{\"sid\":1,\"name\":\"xiaoming\",\"gender\":\"man\",\"department\":\"Cloud BU\"}]", result);
	}
	
	/**
	 * 向指定 URL 发送 GET请求
	 *
	 * @param strUrl        发送请求的 URL
	 * @param requestParams 请求参数
	 * @return 远程资源的响应结果
	 */
	public static String sendGet(String strUrl, String requestParams) {
		String responseParams = "";
		BufferedReader bufferedReader = null;
		try {
			String strRequestUrl = strUrl + "?" + requestParams;
			if (null == requestParams) {
				strRequestUrl = strUrl;
			}
			URL url = new URL(strRequestUrl);
			// 打开与 URL之间的连接
			URLConnection urlConnection = url.openConnection();

			// 设置通用的请求属性
			urlConnection.setRequestProperty("accept", "*/*");
			urlConnection.setRequestProperty("connection", "Keep-Alive");
			urlConnection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// 建立连接
			urlConnection.connect();
			// 使用BufferedReader输入流来读取URL的响应
			bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
			String strLine;
			while ((strLine = bufferedReader.readLine()) != null) {
				responseParams += strLine;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (bufferedReader != null) {
					bufferedReader.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return responseParams;
	}
}
