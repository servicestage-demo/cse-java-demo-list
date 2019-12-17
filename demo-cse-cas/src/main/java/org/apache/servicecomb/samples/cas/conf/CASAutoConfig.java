package org.apache.servicecomb.samples.cas.conf;

import java.util.EventListener;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.servicecomb.samples.cas.controller.CASController;
import org.jasig.cas.client.authentication.AuthenticationFilter;
import org.jasig.cas.client.session.SingleSignOutFilter;
import org.jasig.cas.client.session.SingleSignOutHttpSessionListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CASAutoConfig {
	@Value("${cas.server-url-prefix}")
	private String serverUrlPrefix;
	@Value("${cas.server-login-url}")
	private String serverLoginUrl;
	@Value("${casClientLogoutUrl}")
	private String serverLogoutUrl;
	@Value("${cas.client-host-url}")
	private String clientHostUrl;

	private static Logger LOGGER = Logger.getLogger(CASController.class);

	/**
	 * 配置登出过滤器
	 * @return
	 */
	@Bean
	public FilterRegistrationBean filterSingleRegistration() {
		final FilterRegistrationBean registration = new FilterRegistrationBean();
		registration.setFilter(new SingleSignOutFilter());
		// 设定匹配的路径
		registration.addUrlPatterns("/*");
		Map<String, String> initParameters = new HashMap<String, String>();
		initParameters.put("casServerUrlPrefix", serverLogoutUrl);
		registration.setInitParameters(initParameters);
		// 设定加载的顺序
		registration.setOrder(1);
		LOGGER.info("login out success");
		return registration;
	}

	/**
	 * 添加登出监听器
	 * @return
	 */
	@Bean
	public ServletListenerRegistrationBean<EventListener> singleSignOutListenerRegistration() {
		ServletListenerRegistrationBean<EventListener> registrationBean = new ServletListenerRegistrationBean<EventListener>();
		registrationBean.setListener(new SingleSignOutHttpSessionListener());
		registrationBean.setOrder(2);
		LOGGER.info("login out success");
		return registrationBean;
	}

	/**
	 * 授权过滤器
	 * @return
	 */
	@Bean
	public FilterRegistrationBean filterAuthenticationRegistration() {
		FilterRegistrationBean registration = new FilterRegistrationBean();
		registration.setFilter(new AuthenticationFilter());
		// 设定匹配的路径
		registration.addUrlPatterns("/*");
		Map<String, String> initParameters = new HashMap<String, String>();
		initParameters.put("casServerLoginUrl", serverLoginUrl);
		initParameters.put("serverName", clientHostUrl);
		// 忽略的url，"|"分隔多个url
		// initParameters.put("ignorePattern", "/logout/success|/index");

		registration.setInitParameters(initParameters);
		// 设定加载的顺序
		registration.setOrder(3);
		return registration;
	}

}
