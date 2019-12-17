package com.huawei.dao;

import java.util.List;

import com.huawei.entity.Phone;

public interface PhoneDao {
  boolean set(String key, Phone value);

  boolean set(String key, Phone value, int exp);

  public boolean lpush(String key, Phone value);

  public List<Phone> lrange(String key);

  boolean remove(Phone phone);
}
