package com.busfor.db.exists;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DepartmentExistQuery implements ExistQuery{
	
	private final Logger log = LoggerFactory.getLogger(DepartmentExistQuery.class);
	private final String SQL = "SELECT * FROM department_by_id(?)";
	private Connection connection;
	private Integer departmentId;
	
	public DepartmentExistQuery(Connection connection, Integer departmentId) {
		this.connection = connection;
		this.departmentId = departmentId;
	}

	@Override
	public boolean exists() {
		boolean exist = false;
		try(PreparedStatement pst = connection.prepareStatement(SQL)){
			pst.setInt(1, departmentId);
			ResultSet rs = pst.executeQuery();
			
		}catch (SQLException e) {
			log.error(this.getClass().toString(), e);
		}
		return exist;
	}

}
