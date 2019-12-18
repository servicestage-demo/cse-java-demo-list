package com.huawei;

import org.apache.ibatis.session.SqlSession;
import org.apache.servicecomb.foundation.common.utils.BeanUtils;
import org.apache.servicecomb.foundation.common.utils.Log4jUtils;

import com.huawei.util.MyBatisUtil;

public class ProviderApplication {

  public static void main(String[] args) throws Exception {
    Log4jUtils.init();
    BeanUtils.init();

    SqlSession sqlSession = MyBatisUtil.getSession();
    try {
      sqlSession.update("com.huawei.dao.PersonDao.isExists");
    } catch (Exception e) {
      sqlSession.update("com.huawei.dao.PersonDao.createDatabase");
      sqlSession.update("com.huawei.dao.PersonDao.isExists");
      sqlSession.update("com.huawei.dao.PersonDao.createTable");
    } finally {
      MyBatisUtil.closeSession(sqlSession);
    }
  }
}
