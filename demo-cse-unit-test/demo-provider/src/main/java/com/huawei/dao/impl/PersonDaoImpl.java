package com.huawei.dao.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.huawei.dao.PersonDao;
import com.huawei.entity.Person;
import com.huawei.util.MyBatisUtil;

@Service("personDao")
public class PersonDaoImpl implements PersonDao {

  private static final Logger LOGGER = LoggerFactory.getLogger(PersonDaoImpl.class);

  public static final String RESOURCE = "com.huawei.dao.PersonDao.";

  @Override
  public boolean savePerson(Person person) {
    boolean flag = false;
    SqlSession sqlSession = null;
    try {
      sqlSession = MyBatisUtil.getSession();
      sqlSession.update("com.huawei.dao.PersonDao.isExists");
      sqlSession.insert(RESOURCE + "savePerson", person);
      sqlSession.commit();
      flag = true;
    } catch (Exception e) {
      LOGGER.error("execute method savePerson error:{}", e);
      e.printStackTrace();
    } finally {
      MyBatisUtil.closeSession(sqlSession);
    }
    return flag;
  }

  @Override
  public List<Person> queryPerson(String pid) {
    List<Person> person = null;
    SqlSession sqlSession = null;
    try {
      sqlSession = MyBatisUtil.getSession();
      sqlSession.update("com.huawei.dao.PersonDao.isExists");
      person = sqlSession.selectList(RESOURCE + "queryPerson", pid);
    } catch (Exception e) {
      LOGGER.error("execute method queryPerson error:{}", e);
      e.printStackTrace();
    } finally {
      MyBatisUtil.closeSession(sqlSession);
    }
    return person;
  }

  @Override
  public boolean deletePerson(Person person) {
    boolean flag = false;
    SqlSession sqlSession = null;
    try {
      sqlSession = MyBatisUtil.getSession();
      sqlSession.update("com.huawei.dao.PersonDao.isExists");
      sqlSession.delete(RESOURCE + "deletePerson", person.getPid());
      sqlSession.commit();
      flag = true;
    } catch (Exception e) {
      LOGGER.error("execute method deletePerson error:{}", e);
      e.printStackTrace();
    } finally {
      MyBatisUtil.closeSession(sqlSession);
    }
    return flag;
  }

}
