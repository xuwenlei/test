package com.ssh.shop.service;

import java.sql.SQLException;

import com.ssh.shop.dao.UserDao;
import com.ssh.shop.entities.User;

public class UserService {

	private UserDao dao = new UserDao();
	
	public void setDao(UserDao dao) {
		this.dao = dao;
	}

	public void regist(User user) {
		try {
			dao.regist(user);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void active(String activeCode) {
		try {
			dao.active(activeCode);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public boolean checkUsername(String username) {
		long isExist = 0l;
		try {
			isExist = dao.checkUsername(username);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return isExist > 0 ? true : false;
	}

	public User login(String username, String password) {
		User user = null;
		try {
			user = dao.login(username, password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return user;
	}

}
