package com.huawei.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.huawei.dao.impl.PhoneDaoImpl;
import com.huawei.entity.Phone;
import com.huawei.service.PhoneService;

@Service
public class PhoneServiceImpl implements PhoneService {
  @Autowired
  @Qualifier("phoneDao")
  PhoneDaoImpl phoneDaoImpl;

  @Override
  public boolean savePhone(String type, Phone phone) {
    return phoneDaoImpl.lpush(type, phone);
  }

  @Override
  public List<Phone> getPhone(String type) {
    List<Phone> list = phoneDaoImpl.lrange(type);
    return list;
  }

  @Override
  public boolean delPhone(Phone phone) {
    return phoneDaoImpl.remove(phone);
  }
}
