package com.huawei.util;

import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyBatisUtil {
  private static final Logger LOGGER = LoggerFactory.getLogger(MyBatisUtil.class);

  private static final String CONFIG_FILE = "mybatis-config.xml";

  public static SqlSession getSession() {
    SqlSession sqlSession = null;
    try {
      LOGGER.info("Start getting sqlSession.");
      InputStream inputStream = Resources.getResourceAsStream(CONFIG_FILE);
      SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
      sqlSession = sqlSessionFactory.openSession();
    } catch (Exception e) {
      LOGGER.error("Failed to get sqlSession. error: {}", e);
      e.printStackTrace();
    }
    return sqlSession;
  }

  public static void closeSession(SqlSession sqlSession) {
    sqlSession.close();
  }
}
