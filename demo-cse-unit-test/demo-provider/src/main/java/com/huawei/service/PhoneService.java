package com.huawei.service;

import java.util.List;

import com.huawei.entity.Phone;

public interface PhoneService {
  boolean savePhone(String type, Phone phone);

  List<Phone> getPhone(String type);

  boolean delPhone(Phone phone);
}
