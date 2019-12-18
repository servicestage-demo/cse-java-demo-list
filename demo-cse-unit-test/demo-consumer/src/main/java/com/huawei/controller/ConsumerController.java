package com.huawei.controller;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;

import org.apache.servicecomb.provider.pojo.RpcReference;
import org.apache.servicecomb.provider.rest.common.RestSchema;
import org.apache.servicecomb.provider.springmvc.reference.RestTemplateBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;

import com.huawei.entity.DateResponse;
import com.huawei.entity.Person;
import com.huawei.entity.Phone;
import com.huawei.provider.ProviderController;

@RestSchema(schemaId = "consumer")
@Path("/consumer/v0")
public class ConsumerController {
  private static final Logger LOGGER = LoggerFactory.getLogger(ConsumerController.class);

  @RpcReference(microserviceName = "provider", schemaId = "provider")
  ProviderController provider;

  public RestTemplate restTemplate = RestTemplateBuilder.create();

  @Path("/hello")
  @GET
  public String sayHello(@QueryParam("name") String name) {
    return provider.sayHello(name);
  }

  @Path("/welcome")
  @POST
  public DateResponse welcomePerson(@RequestBody Person person) {
    return provider.welcomePerson(person);
  }

  @Path("/register")
  @POST
  public boolean register(@RequestBody Person person) {
    String url = "cse://provider/provider/v0/savePerson";
    ResponseEntity<Boolean> response = restTemplate.postForEntity(url, person, Boolean.class);
    return response.getBody();
  }

  @SuppressWarnings("unchecked")
  @Path("/query")
  @GET
  public List<Person> query(@QueryParam("pid") String pid) {
    String url = "cse://provider/provider/v0/queryPerson?pid=" + pid;
    @SuppressWarnings("rawtypes")
    ResponseEntity<List> response = restTemplate.getForEntity(url, List.class);
    return response.getBody();
  }

  @Path("/delete")
  @POST
  public boolean delete(@RequestBody Person person) {
    return provider.deletePerson(person);
  }

  @Path("/setphone")
  @POST
  public boolean setPhone(@RequestBody Phone phone) {
    return provider.setPhone(phone);
  }

  @Path("/getphone/{type}")
  @GET
  public List<Phone> getPhone(@PathParam("type") String type) {
    LOGGER.info("invoke getphone url,type is {}", type);
    List<Phone> list = provider.getPhone(type);
    return list;
  }

  @Path("/delphone")
  @POST
  public boolean delPhone(@RequestBody Phone phone) {
    return provider.delPhone(phone);
  }
}
