package com.huawei.provider;

import java.util.List;

import com.huawei.entity.DateResponse;
import com.huawei.entity.Person;
import com.huawei.entity.Phone;

public interface ProviderController {
  public String sayHello(String name);

  public DateResponse welcomePerson(Person person);

  public boolean savePerson(Person person);

  public List<Person> queryPerson(String pid);

  public boolean deletePerson(Person person);

  public boolean setPhone(Phone phone);

  public List<Phone> getPhone(String type);

  public boolean delPhone(Phone phone);
}
