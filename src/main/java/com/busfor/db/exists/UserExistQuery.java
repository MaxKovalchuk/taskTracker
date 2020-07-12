package com.busfor.db.exists;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserExistQuery extends ExistQueryImpl{
	
	private static final String DEPARTMENT_EXISTS_FUNCTION = "user_by_id";
	
	public UserExistQuery(Connection connection, Integer userId) {
		super(connection, userId, DEPARTMENT_EXISTS_FUNCTION);
	}
}
