/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.servicecomb.samples.mybatis.dao.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.servicecomb.samples.mybatis.dao.IUserDao;
import org.apache.servicecomb.samples.mybatis.entity.User;
import org.apache.servicecomb.samples.mybatis.util.MyBatisUtil;
import org.springframework.stereotype.Service;

@Service
public class UserDaoImpl implements IUserDao {

	private static final String namespace = "org.apache.servicecomb.samples.mybatis.dao.userDao.";

	@Override
	public List<User> findAllUsers() {
		List<User> users = null;
		SqlSession session = MyBatisUtil.getSession();
		try {
			users = session.selectList(namespace + "findAllUsers", User.class);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			MyBatisUtil.closeSession(session);
		}
		return users;
	}

	@Override
	public int addUser(User user) {
		SqlSession session = MyBatisUtil.getSession();
		int result = 0;
		try {
			result = session.insert(namespace + "addUser", user);
			session.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			MyBatisUtil.closeSession(session);
		}
		return result;
	}
}
