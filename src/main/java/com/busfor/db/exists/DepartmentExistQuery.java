package com.busfor.db.exists;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DepartmentExistQuery extends ExistQueryImpl{
	
	private static final String DEPARTMENT_EXISTS_FUNCTION = "department_by_id";
	
	public DepartmentExistQuery(Connection connection, Integer departmentId) {
		super(connection, departmentId, DEPARTMENT_EXISTS_FUNCTION);
	}
}
