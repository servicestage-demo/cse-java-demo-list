package com.huawei.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.huawei.dao.PhoneDao;
import com.huawei.entity.Phone;
import com.huawei.util.RedisUtil;

import net.sf.json.JSONObject;
import redis.clients.jedis.Jedis;

@Service("phoneDao")
public class PhoneDaoImpl implements PhoneDao {

  private static final Logger LOGGER = LoggerFactory.getLogger(PhoneDaoImpl.class);

  private static final int EXPRIE_TIME = 3600 * 24;

  @Override
  public boolean set(String key, Phone phone) {
    return set(key, phone, EXPRIE_TIME);
  }

  @Override
  public boolean set(String key, Phone phone, int exp) {
    Jedis jedis = null;
    if (StringUtils.isEmpty(key)) {
      return false;
    }
    try {
      jedis = RedisUtil.getJedis();
      JSONObject json = JSONObject.fromObject(phone);
      String jsonValue = json.toString();
      jedis.setex(key, exp, jsonValue);
    } catch (Exception e) {
      RedisUtil.close(jedis);
      LOGGER.info("set method is error:{}.", e);
      return false;
    } finally {
      RedisUtil.close(jedis);
    }
    return true;
  }

  @Override
  public boolean lpush(String key, Phone phone) {
    Jedis jedis = null;
    if (StringUtils.isEmpty(key)) {
      return false;
    }
    try {
      jedis = RedisUtil.getJedis();
      JSONObject json = JSONObject.fromObject(phone);
      String jsonValue = json.toString();
      LOGGER.info("key is {} ,phone is {}", key, phone);
      jedis.lpush(key, jsonValue);
      return true;
    } catch (Exception e) {
      RedisUtil.close(jedis);
      LOGGER.info("lpush method is error:{}.", e);
      return false;
    } finally {
      RedisUtil.close(jedis);
    }
  }

  @Override
  public List<Phone> lrange(String key) {
    Jedis jedis = null;
    List<Phone> list = null;
    if (StringUtils.isEmpty(key)) {
      LOGGER.info("key is null.");
      return list;
    }
    try {
      jedis = RedisUtil.getJedis();
      List<String> jsonValue = jedis.lrange(key, 0, Integer.MAX_VALUE);
      if (jsonValue == null) {
        return list;
      }
      list = new ArrayList<>();

      for (String jsonStr : jsonValue) {
        JSONObject obj = JSONObject.fromObject(jsonStr);
        Phone phone = (Phone) JSONObject.toBean(obj, Phone.class);
        list.add(phone);
      }
      return list;
    } catch (Exception e) {
      RedisUtil.close(jedis);
      LOGGER.info("lrange method is error:{}.", e);
      return list;
    } finally {
      RedisUtil.close(jedis);
    }
  }

  @Override
  public boolean remove(Phone phone) {
    Jedis jedis = null;
    try {
      jedis = RedisUtil.getJedis();
      if (StringUtils.isEmpty(phone.getType())) {
        LOGGER.info("key is null.");
        return false;
      }
      jedis.del(phone.getType());
      return true;
    } catch (Exception e) {
      RedisUtil.close(jedis);
      LOGGER.info("remove method is error:{}.", e);
      return false;
    } finally {
      RedisUtil.close(jedis);
    }
  }
}
