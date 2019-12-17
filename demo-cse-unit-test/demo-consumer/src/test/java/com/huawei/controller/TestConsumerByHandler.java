package com.huawei.controller;

import static org.junit.Assert.*;

import java.util.List;

import org.apache.servicecomb.foundation.common.utils.BeanUtils;
import org.apache.servicecomb.foundation.common.utils.Log4jUtils;
import org.apache.servicecomb.provider.springmvc.reference.RestTemplateBuilder;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.huawei.entity.DateResponse;
import com.huawei.entity.Person;
import com.huawei.entity.Phone;

public class TestConsumerByHandler {

  final Person person = new Person("1", "JavaFake", "test", "male", 20);

  final Phone phone = new Phone("1", "HUAWEIPFake", 158.0, 73.4, 8.41, "Black");

  @BeforeClass
  public static void setUp() throws Exception {
    Log4jUtils.init();
    BeanUtils.init();
  }

  //common
  @Test
  public void testCallSayHello() {
    final String url = "cse://consumer/consumer/v0/hello?name=JavaFake";
    final RestTemplate template = RestTemplateBuilder.create();
    ResponseEntity<String> response = template.getForEntity(url, String.class);
    assertEquals("JavaFake", response.getBody());
    assertEquals(HttpStatus.OK, response.getStatusCode());
  }

  @Test
  public void testCallWelcomePerson() {
    final String url = "cse://consumer/consumer/v0/welcome";
    final RestTemplate template = RestTemplateBuilder.create();
    ResponseEntity<DateResponse> response = template.postForEntity(url, person, DateResponse.class);
    assertEquals("JavaFake", response.getBody().getMsg());
    assertEquals(HttpStatus.OK, response.getStatusCode());
  }

  //mysql
  @Test
  public void testCallRegister() {
    final String url = "cse://consumer/consumer/v0/register";
    final RestTemplate template = RestTemplateBuilder.create();
    ResponseEntity<Boolean> response = template.postForEntity(url, person, Boolean.class);
    assertEquals(true, response.getBody());
    assertEquals(HttpStatus.OK, response.getStatusCode());
  }

  @Test
  public void testCallQuery() {
    final String url = "cse://consumer/consumer/v0/query?pid=JavaFake";
    final RestTemplate template = RestTemplateBuilder.create();
    ResponseEntity<List> response = template.getForEntity(url, List.class);
    assertNotNull(response.getBody());
    assertEquals(HttpStatus.OK, response.getStatusCode());
  }

  @Test
  public void testCallDelete() {
    final String url = "cse://consumer/consumer/v0/delete";
    final RestTemplate template = RestTemplateBuilder.create();
    ResponseEntity<Boolean> response = template.postForEntity(url, person, Boolean.class);
    assertEquals(true, response.getBody());
    assertEquals(HttpStatus.OK, response.getStatusCode());
  }

  //redis
  @Test
  public void testCallSetPhone() {
    final String url = "cse://consumer/consumer/v0/setphone";
    final RestTemplate template = RestTemplateBuilder.create();
    ResponseEntity<Boolean> response = template.postForEntity(url, phone, Boolean.class);
    assertEquals(true, response.getBody());
    assertEquals(HttpStatus.OK, response.getStatusCode());
  }

  @Test
  public void testCallGetPhone() {
    final String url = "cse://consumer/consumer/v0/getphone/HUAWEIPFake";
    final RestTemplate template = RestTemplateBuilder.create();
    ResponseEntity<List> response = template.getForEntity(url, List.class);
    assertNotNull(response.getBody());
    assertEquals(HttpStatus.OK, response.getStatusCode());
  }

  @Test
  public void testCallDelPhone() {
    final String url = "cse://consumer/consumer/v0/delphone";
    final RestTemplate template = RestTemplateBuilder.create();
    ResponseEntity<Boolean> response = template.postForEntity(url, phone, Boolean.class);
    assertEquals(true, response.getBody());
    assertEquals(HttpStatus.OK, response.getStatusCode());
  }
}
