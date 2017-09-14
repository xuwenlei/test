package com.xwl.dao;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.xwl.domain.User;
import com.xwl.utils.DataSourceUtils;

public class UserDao {
	
	private QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());

	public int regist(User user) throws SQLException {
		String sql = "insert into user values(?,?,?,?,?,?,?,?,?,?)";
		int update = runner.update(sql, user.getUid(),user.getUsername(),user.getPassword(),user.getName(),user.getEmail(),
						user.getTelephone(),user.getBirthday(),user.getSex(),user.getState(),user.getCode());
		return update;
	}

	public void active(String activeCode) throws SQLException {
		String sql = "update user set state = ? where code = ?";
		runner.update(sql, 1, activeCode);
	}

	public long checkUsername(String username) throws SQLException {
		String sql = "select count(*) from user where username = ?";
		long query = (long) runner.query(sql, new ScalarHandler<>(), username);
		return query;
	}

	public User login(String username, String password) throws SQLException {
		String sql = "select * from user where username = ? and password = ?";
		User user = runner.query(sql, new BeanHandler<>(User.class), username,password);
		return user;
	}
	
	
	
}
