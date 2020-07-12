package com.busfor.db.insert;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.busfor.model.User;

public class UserQueryInsert extends User implements QueryInsert {

	private final Logger log = LoggerFactory.getLogger(UserQueryInsert.class);
	private final String SQL = "INSERT INTO \"user\"(name, department_Id) " + "VALUES(?,?)";

	private final Connection connection;

	public UserQueryInsert(String name, Integer departmentId, Connection connection) {
		super();
		setName(name);
		setDepartmentId(departmentId);
		this.connection = connection;
	}

	@Override
	public boolean insert() {
		boolean inserted = false;
		try (PreparedStatement pst = connection.prepareStatement(SQL)){
			pst.setString(1, getName());
			pst.setInt(2, getDepartmentId());
			int affectedRows = pst.executeUpdate();
			inserted = affectedRows > 0;
		} catch (SQLException e) {
			log.error("Error while inserting user", e);
		}
		return inserted;
	}

}
