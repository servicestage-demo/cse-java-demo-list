package com.huawei.service.impl;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.huawei.entity.DateResponse;
import com.huawei.entity.Gender;
import com.huawei.entity.Person;
import com.huawei.service.CommonService;

@Service
public class CommonServiceImpl implements CommonService {

  @Override
  public String sayHello(String name) {
    return "Hello " + name;
  }

  @Override
  public DateResponse welcomePerson(Person person) {
    final DateResponse response;
    if (StringUtils.isEmpty(person.getName()) || null == person.getGender()) {
      throw new IllegalArgumentException("Lack of property");
    }
    if (Gender.MALE.equals(person.getGender())) {
      response = new DateResponse("Welcome,Mr." + person.getName(), new Date());
    } else {
      response = new DateResponse("Welcome,Ms." + person.getName(), new Date());
    }
    return response;
  }
}
