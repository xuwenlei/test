package com.ssh.shop.dao;

import java.sql.SQLException;

import com.ssh.shop.entities.User;

public class UserDao extends BaseDao {

	public void regist(User user) throws SQLException {
		getSession().saveOrUpdate(user);
	}

	public void active(String activeCode) throws SQLException {
		String hql = "update User u where u.code = ?";
		getSession().createQuery(hql).setParameter(0, activeCode)
				.executeUpdate();
	}

	public long checkUsername(String username) throws SQLException {
		String hql = "select count(*) from User where username + ?";
		long query = (long) getSession().createQuery(hql)
				.setString(0, username).uniqueResult();
		return query;
	}

	public User login(String username, String password) throws SQLException {
		String hql = "from User u where u.username = ? and u.password = ?";
		User user = (User) getSession().createQuery(hql).setString(0, username)
				.setString(1, password).uniqueResult();
		return user;
	}

}
