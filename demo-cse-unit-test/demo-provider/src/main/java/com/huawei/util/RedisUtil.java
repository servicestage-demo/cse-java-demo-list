package com.huawei.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisUtil {
  private static final Logger LOGGER = LoggerFactory.getLogger(RedisUtil.class);

  private static String ADDR = "127.0.0.1";

  private static int PORT = 6379;

  private static String AUTH = "admin";

  private static int MAX_TOTAL = 8;

  private static int MIN_IDLE = 0;

  private static int MAX_IDLE = 8;

  private static int MAX_WAIT = -1;

  private static int TIMEOUT = 10000;

  private static boolean BLOCK_WHEN_EXHAUSTED = false;

  private static String EVICTION_POLICY_CLASSNAME = "org.apache.commons.pool2.impl.DefaultEvictionPolicy";

  private static boolean JMX_ENABLED = true;

  private static String JMX_NAME_PREFIX = "pool";

  private static boolean LIFO = true;

  private static long MIN_EVICTABLE_IDLE_TIME_MILLIS = 1800000L;

  private static long SOFT_MIN_EVICTABLE_IDLE_TIME_MILLIS = 1800000L;

  private static int NUM_TESTS_PER_EVICYION_RUN = 3;

  private static boolean TEST_ON_BORROW = false;

  private static boolean TEST_WHILEIDLE = false;

  private static long TIME_BERWEEN_EVICTION_RUNS_MILLIS = -1;

  private static JedisPool jedisPool = null;

  static {
    try {
      JedisPoolConfig config = new JedisPoolConfig();
      config.setBlockWhenExhausted(BLOCK_WHEN_EXHAUSTED);
      config.setEvictionPolicyClassName(EVICTION_POLICY_CLASSNAME);
      config.setJmxEnabled(JMX_ENABLED);
      config.setJmxNamePrefix(JMX_NAME_PREFIX);
      config.setLifo(LIFO);
      config.setMaxIdle(MAX_IDLE);
      config.setMaxTotal(MAX_TOTAL);
      config.setMaxWaitMillis(MAX_WAIT);
      config.setMinEvictableIdleTimeMillis(MIN_EVICTABLE_IDLE_TIME_MILLIS);
      config.setMinIdle(MIN_IDLE);
      config.setNumTestsPerEvictionRun(NUM_TESTS_PER_EVICYION_RUN);
      config.setSoftMinEvictableIdleTimeMillis(SOFT_MIN_EVICTABLE_IDLE_TIME_MILLIS);
      config.setTestOnBorrow(TEST_ON_BORROW);
      config.setTestWhileIdle(TEST_WHILEIDLE);
      config.setTimeBetweenEvictionRunsMillis(TIME_BERWEEN_EVICTION_RUNS_MILLIS);

      jedisPool = new JedisPool(config, ADDR, PORT, TIMEOUT, AUTH);
    } catch (Exception e) {
      LOGGER.error("init jedisPool failed, error: {}", e);
      e.printStackTrace();
    }
  }

  public synchronized static Jedis getJedis() {
    try {
      if (jedisPool != null) {
        Jedis resource = jedisPool.getResource();
        return resource;
      } else {
        return null;
      }
    } catch (Exception e) {
      LOGGER.error("getJedis error: {}", e);
      e.printStackTrace();
      return null;
    }
  }

  public static void close(final Jedis jedis) {
    if (jedis != null) {
      jedis.close();
    }
  }
}
