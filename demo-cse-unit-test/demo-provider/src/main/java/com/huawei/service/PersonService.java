package com.huawei.service;

import java.util.List;

import com.huawei.entity.Person;

public interface PersonService {
  boolean savePerson(Person person);

  List<Person> queryPerson(String pid);

  boolean deletePerson(Person person);
}
