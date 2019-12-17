package org.apache.servicecomb.samples.cas.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.servicecomb.provider.rest.common.RestSchema;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import net.unicon.cas.client.configuration.EnableCasClient;

/**
 * CAS控制器类
 */
@EnableCasClient
@RestSchema(schemaId = "hello")
@RequestMapping(path = "/")
public class CASController {
	private static Logger LOGGER = Logger.getLogger(CASController.class);
	
	@Value("${casClientLogoutUrl}")
	private String clientLogoutUrl;

	public String index(ModelMap map) {
		map.addAttribute("name", "clien B");
		return "index";
	}

	@RequestMapping(path = "hello", method = RequestMethod.GET)
	public String hello() {
		return "hello";
	}

	@RequestMapping(path = "logout", method = RequestMethod.GET)
	public String logout() {
		LOGGER.info("logout success");
		ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes();
		HttpServletRequest request = servletRequestAttributes.getRequest();
		HttpSession session = request.getSession(true);
		// 销毁session
		session.invalidate();
		// 使用cas退出成功后,跳转到http://cas.client1.com:9002/logout/success
		return "redirect:" + clientLogoutUrl;
	}

	public String logoutsuccess(HttpSession session) {
		return "logout success";
	}
}
