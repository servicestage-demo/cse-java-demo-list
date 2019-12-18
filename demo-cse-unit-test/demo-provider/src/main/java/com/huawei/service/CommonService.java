package com.huawei.service;

import com.huawei.entity.DateResponse;
import com.huawei.entity.Person;

public interface CommonService {
  String sayHello(String name);

  DateResponse welcomePerson(Person person);
}
