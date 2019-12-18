package com.huawei.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.huawei.dao.PersonDao;
import com.huawei.entity.Person;
import com.huawei.service.PersonService;

@Service
public class PersonServiceImpl implements PersonService {

  @Autowired
  @Qualifier("personDao")
  public PersonDao personDao;

  @Override
  public boolean savePerson(Person person) {
    boolean flag = false;
    if (null != person) {
      flag = personDao.savePerson(person);
    }
    return flag;
  }

  @Override
  public List<Person> queryPerson(String pid) {
    List<Person> list = null;
    if (null != pid && !"".equals(pid)) {
      list = personDao.queryPerson(pid);
    }
    return list;
  }

  @Override
  public boolean deletePerson(Person person) {
    boolean flag = false;
    if (null != person) {
      flag = personDao.deletePerson(person);
    }
    return flag;
  }
}
