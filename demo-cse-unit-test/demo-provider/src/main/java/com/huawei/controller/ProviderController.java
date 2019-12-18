package com.huawei.controller;

import java.util.List;

import org.apache.servicecomb.provider.rest.common.RestSchema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.huawei.entity.DateResponse;
import com.huawei.entity.Person;
import com.huawei.entity.Phone;
import com.huawei.service.impl.CommonServiceImpl;
import com.huawei.service.impl.PersonServiceImpl;
import com.huawei.service.impl.PhoneServiceImpl;

@RestSchema(schemaId = "provider")
@RequestMapping(path = "/provider/v0")
public class ProviderController {

  private static final Logger LOGGER = LoggerFactory.getLogger(ProviderController.class);

  @Autowired
  CommonServiceImpl commonService;

  @Autowired
  PersonServiceImpl personService;
  
  @Autowired
  PhoneServiceImpl phoneService;

  @GetMapping("/sayHello")
  public String sayHello(@RequestParam(name = "name") String name) {
    LOGGER.info("invoke sayHello url,name is {}", name);
    return commonService.sayHello(name);
  }

  @PostMapping("/welcomePerson")
  public DateResponse welcomePerson(@RequestBody Person person) {
    LOGGER.info("invoke welcomePerson url,person is {}", person);
    return commonService.welcomePerson(person);
  }

  @PostMapping("/savePerson")
  public boolean savePerson(@RequestBody Person person) {
    LOGGER.info("invoke savePerson url,person is {}", person);
    return personService.savePerson(person);
  }

  @GetMapping("/queryPerson")
  public List<Person> queryPerson(@RequestParam(name = "pid") String pid) {
    LOGGER.info("invoke queryPerson url,pid is {}", pid);
    return personService.queryPerson(pid);
  }

  @PostMapping("/deletePerson")
  public boolean deletePerson(@RequestBody Person person) {
    LOGGER.info("invoke deletePerson url,person is {}", person);
    return personService.deletePerson(person);
  }

  @PostMapping("/setphone")
  public boolean setPhone(@RequestBody Phone phone) {
    LOGGER.info("invoke setPhone url,type is {}", phone);
    return phoneService.savePhone(phone.getType(), phone);
  }

  @GetMapping("/getphone/{type}")
  public List<Phone> getPhone(@PathVariable("type") String type) {
    LOGGER.info("invoke getphone url,type is {}", type);
    List<Phone> list = phoneService.getPhone(type);
    return list;
  }

  @PostMapping("/delphone")
  public boolean delPhone(@RequestBody Phone phone) {
    LOGGER.info("invoke delphone url,type is {}", phone);
    return phoneService.delPhone(phone);
  }

}
