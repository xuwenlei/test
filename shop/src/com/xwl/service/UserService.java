package com.xwl.service;

import java.sql.SQLException;

import com.xwl.dao.UserDao;
import com.xwl.domain.User;

public class UserService {
	
	private UserDao dao = new UserDao();

	public boolean regist(User user) {
		int row = 0;
		try {
			row = dao.regist(user);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return row>0?true:false;
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
		return isExist>0?true:false;
	}

	public User login(String username, String password) {
		User user = null;
		try {
			user = dao.login(username,password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return user;
	}
	
}
