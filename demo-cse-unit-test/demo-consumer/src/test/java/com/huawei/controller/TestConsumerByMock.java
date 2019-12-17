package com.huawei.controller;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.huawei.entity.DateResponse;
import com.huawei.entity.Person;
import com.huawei.entity.Phone;
import com.huawei.provider.ProviderController;

@RunWith(MockitoJUnitRunner.class)
public class TestConsumerByMock {

  @Mock
  ProviderController iprovider;

  @Mock
  RestTemplate irestTemplate;

  final ConsumerController consumer = new ConsumerController();

  @Test
  public void testSayHello() {
    consumer.provider = iprovider;
    when(iprovider.sayHello("Java")).thenReturn("Hello,Java");
    String value = consumer.sayHello("Java");
    verify(iprovider).sayHello("Java");
    assertEquals("Hello,Java", value);
  }

  @Test
  public void testWelcomePerson() {
    final Person person = new Person("1", "Java", "test", "male", 20);
    final DateResponse response = new DateResponse("Hello,Java", new Date());
    consumer.provider = iprovider;
    when(iprovider.welcomePerson(person)).thenReturn(response);
    DateResponse actualResponse = consumer.welcomePerson(person);
    verify(iprovider).welcomePerson(person);
    assertEquals(response.getMsg(), actualResponse.getMsg());
  }

  @Test
  public void testRegister() {
    final Person person = new Person("1", "Java", "test", "male", 20);
    final String url = "cse://provider/provider/v0/savePerson";
    final ResponseEntity response = mock(ResponseEntity.class);
    consumer.restTemplate = irestTemplate;
    final boolean expectedValue = true;
    when(irestTemplate.postForEntity(url, person, Boolean.class)).thenReturn(response);
    when(response.getBody()).thenReturn(true);
    boolean actualValue = consumer.register(person);
    assertEquals(expectedValue, actualValue);
  }

  @Test
  public void testGetPhone() {
    final List<Phone> list = new ArrayList<>();
    final Phone phone = new Phone("1", "HUAWEI P", 158.0, 73.4, 8.41, "Black");
    list.add(phone);
    consumer.provider = iprovider;
    when(iprovider.getPhone(anyString())).thenReturn(list);
    List<Phone> alist = consumer.getPhone("Java");
    verify(iprovider).getPhone("Java");
    assertEquals(phone.getId(), alist.get(0).getId());
  }

  @Test
  public void testSetPhone() {
    final Phone phone = new Phone("1", "HUAWEI P", 158.0, 73.4, 8.41, "Black");
    final boolean expectedValue = true;
    consumer.provider = iprovider;
    when(iprovider.setPhone(phone)).thenReturn(true);
    boolean actualValue = consumer.setPhone(phone);
    verify(iprovider).setPhone(phone);
    assertEquals(expectedValue, actualValue);
  }

}
