package com.busfor.db.exists;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TaskExistQuery extends ExistQueryImpl{
	
	private static final String DEPARTMENT_EXISTS_FUNCTION = "task_by_id";
	
	public TaskExistQuery(Connection connection, Integer taskId) {
		super(connection, taskId, DEPARTMENT_EXISTS_FUNCTION);
	}
}
