package com.huawei.dao;

import java.util.List;

import com.huawei.entity.Person;

public interface PersonDao {
  /**
   * 新增用户
   * 
   * @param person
   * @return
   */
  public boolean savePerson(Person person);

  /**
   * 查询用户
   * 
   * @param pid
   * @return
   */
  public List<Person> queryPerson(String pid);

  /**
   * 删除用户
   * 
   * @param person
   * @return
   */
  public boolean deletePerson(Person person);
}
