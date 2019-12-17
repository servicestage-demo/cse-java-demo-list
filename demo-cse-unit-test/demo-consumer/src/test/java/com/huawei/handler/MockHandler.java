package com.huawei.handler;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.servicecomb.core.Handler;
import org.apache.servicecomb.core.Invocation;
import org.apache.servicecomb.swagger.invocation.AsyncResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.huawei.entity.DateResponse;
import com.huawei.entity.Person;
import com.huawei.entity.Phone;

public class MockHandler implements Handler {

  private static final Logger LOGGER = LoggerFactory.getLogger(MockHandler.class);

  @Override
  public void handle(Invocation invocation, AsyncResponse asyncResp) throws Exception {
    String operationName = invocation.getOperationMeta().getMicroserviceQualifiedName();
    LOGGER.info("operationName:{}", operationName);
    if ("consumer.consumer.sayHello".equals(operationName)) {
      Object name = invocation.getSwaggerArgument(0);
      LOGGER.info("invoke method sayHello, parameter is :{}", name);
      if ("JavaFake".equals(name)) {
        asyncResp.success(name);
        return;
      }
    }
    if ("consumer.consumer.welcomePerson".equals(operationName)) {
      Person person = invocation.getSwaggerArgument(0);
      LOGGER.info("invoke method welcomePerson, parameter is :{}", person);
      if ("JavaFake".equals(person.getName())) {
        DateResponse response = new DateResponse(person.getName(), new Date());
        asyncResp.success(response);
        return;
      }
    }
    if ("consumer.consumer.register".equals(operationName)) {
      Person person = invocation.getSwaggerArgument(0);
      LOGGER.info("invoke method savePerson, parameter is :{}", person);
      if ("JavaFake".equals(person.getName())) {
        asyncResp.success(true);
        return;
      }
    }
    if ("consumer.consumer.query".equals(operationName)) {
      String pid = invocation.getSwaggerArgument(0);
      LOGGER.info("invoke method queryPerson, parameter is :{}", pid);
      if ("JavaFake".equals(pid)) {
        List<Person> list = new ArrayList<>();
        Person person = new Person();
        person.setName(pid);
        list.add(person);
        asyncResp.success(list);
        return;
      }
    }
    if ("consumer.consumer.delete".equals(operationName)) {
      Person person = invocation.getSwaggerArgument(0);
      LOGGER.info("invoke method deletePerson, parameter is :{}", person);
      if ("JavaFake".equals(person.getName())) {
        asyncResp.success(true);
        return;
      }
    }
    if ("consumer.consumer.setPhone".equals(operationName)) {
      Phone phone = invocation.getSwaggerArgument(0);
      LOGGER.info("invoke method setPhone, parameter is :{}", phone);
      if ("HUAWEIPFake".equals(phone.getType())) {
        asyncResp.success(true);
        return;
      }
    }
    if ("consumer.consumer.getPhone".equals(operationName)) {
      String type = invocation.getSwaggerArgument(0);
      if ("HUAWEIPFake".equals(type)) {
        Phone phone = new Phone("1", "HUAWEI P", 158.0, 73.4, 8.41, "Black");
        List<Phone> list = new ArrayList<>();
        LOGGER.info("invoke method getPhone, parameter is :{}", type);
        list.add(phone);
        asyncResp.success(list);
        return;
      }
    }
    if ("consumer.consumer.delPhone".equals(operationName)) {
      Phone phone = invocation.getSwaggerArgument(0);
      LOGGER.info("invoke method getPhone, parameter is :{}", phone);
      if ("HUAWEIPFake".equals(phone.getType())) {
        asyncResp.success(true);
        return;
      }
    }
    invocation.next(asyncResp);
  }
}
