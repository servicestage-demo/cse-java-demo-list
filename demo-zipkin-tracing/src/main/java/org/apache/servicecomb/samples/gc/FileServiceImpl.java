/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.servicecomb.samples.gc;

import java.io.File;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.servicecomb.provider.springmvc.reference.RestTemplateBuilder;
import org.apache.servicecomb.tracing.Span;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

@Service
public class FileServiceImpl implements FileService {
	private static final String TOKEN_KEY = "X-Subject-Token";
	private static final String EXPIRES_KEY = "Expires-time";
	private static final long availablePeriod = 20 * 3600 * 1000; // 每20小时获取一次token

	private static Map<String, String> tokenMap = new HashMap<String, String>();

	private static Logger LOGGER = Logger.getLogger(FileServiceImpl.class);

	@Value("${gc_img_path}")
	private String gcImgPath;
	@Value("${gc_token_url}")
	private String gcTokenUrl;
	@Value("${gc_token_username}")
	private String gcTokenUsername;
	@Value("${gc_token_password}")
	private String gcTokenPassword;
	@Value("${gc_token_domain}")
	private String gcTokenDomain;
	@Value("${gc_token_project}")
	private String gcTokenProject;
	@Value("${gc_api_url}")
	private String gcApiUrl;

	private String getToken() {
		LOGGER.info("start to get token");
		if (!tokenMap.isEmpty()) {
			long startTime = Long.valueOf(tokenMap.get(EXPIRES_KEY));
			long currTime = System.currentTimeMillis();
			if (currTime - startTime < availablePeriod) {
				return tokenMap.get(TOKEN_KEY);
			}
		}

		String raw = requestBody(gcTokenUsername, gcTokenPassword, gcTokenDomain, gcTokenProject);
		RestTemplate restTemplate = RestTemplateBuilder.create();
		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-Type", "application/json");

		HttpEntity<String> entity = new HttpEntity<String>(raw, headers);
		ResponseEntity<String> r = restTemplate.postForEntity(gcTokenUrl, entity, String.class);
		String token = r.getHeaders().getFirst(TOKEN_KEY);
		tokenMap.put(TOKEN_KEY, token);
		tokenMap.put(EXPIRES_KEY, String.valueOf(System.currentTimeMillis()));
		LOGGER.info("end to get token");
		return token;
	}

	/**
	 * 构造使用Token方式访问服务的请求Token对象
	 * 
	 * @param username    用户名
	 * @param passwd      密码
	 * @param domainName  域名
	 * @param projectName 项目名称
	 * @return 构造访问的JSON对象
	 */
	private static String requestBody(String username, String passwd, String domainName, String projectName) {
		JSONObject auth = new JSONObject();

		JSONObject identity = new JSONObject();

		JSONArray methods = new JSONArray();
		methods.add("password");
		identity.put("methods", methods);

		JSONObject password = new JSONObject();

		JSONObject user = new JSONObject();
		user.put("name", username);
		user.put("password", passwd);

		JSONObject domain = new JSONObject();
		domain.put("name", domainName);
		user.put("domain", domain);

		password.put("user", user);

		identity.put("password", password);

		JSONObject scope = new JSONObject();

		JSONObject scopeProject = new JSONObject();
		scopeProject.put("name", projectName);

		scope.put("project", scopeProject);

		auth.put("identity", identity);
		auth.put("scope", scope);

		JSONObject params = new JSONObject();
		params.put("auth", auth);
		return params.toJSONString();
	}

	@Span
	@Override
	public String uploadFile(MultipartFile file) {
		LOGGER.info("start to upload file");

		String filePath = null;
		String imgName = System.currentTimeMillis() + "-" + file.getOriginalFilename();
		if (file != null && !file.isEmpty()) {
			// 文件保存路径
			filePath = gcImgPath + imgName;
			// 转存文件
			try {
				file.transferTo(new File(filePath));
			} catch (Exception e) {
				return e.getMessage();
			}
		}

		if (filePath == null) {
			return "{\"result\": \"不可回收物/有害物品\"}";
		}

		RestTemplate restTemplate = getRestTemplate("UTF-8");

		// 设置请求头
		HttpHeaders headers = new HttpHeaders();
		MediaType type = MediaType.parseMediaType("multipart/form-data;charset=UTF-8;");
		headers.setContentType(type);
		headers.set("X-Auth-Token", getToken());

		// 设置请求体，注意是LinkedMultiValueMap
		MultiValueMap<String, Object> form = new LinkedMultiValueMap<String, Object>();
		FileSystemResource fileSystemResource = new FileSystemResource(filePath);
		form.add("input_img", fileSystemResource);

		// 用HttpEntity封装整个请求报文
		HttpEntity<MultiValueMap<String, Object>> files = new HttpEntity<MultiValueMap<String, Object>>(form, headers);
		ResponseEntity<String> s = restTemplate.postForEntity(gcApiUrl, files, String.class);
		LOGGER.info("end to upload file");
		return s.getBody();
	}

	public static RestTemplate getRestTemplate(String charset) {
		RestTemplate restTemplate = new RestTemplate();
		List<HttpMessageConverter<?>> list = restTemplate.getMessageConverters();
		for (HttpMessageConverter<?> httpMessageConverter : list) {
			if (httpMessageConverter instanceof StringHttpMessageConverter) {
				((StringHttpMessageConverter) httpMessageConverter).setDefaultCharset(Charset.forName(charset));
				break;
			}
		}
		return restTemplate;
	}
}
