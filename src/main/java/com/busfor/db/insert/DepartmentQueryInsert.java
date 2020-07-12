package com.busfor.db.insert;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.busfor.model.Department;

public class DepartmentQueryInsert extends Department implements QueryInsert {

	private final Logger log = LoggerFactory.getLogger(DepartmentQueryInsert.class);
	private final String SQL = "INSERT INTO department(name) " + "VALUES(?)";

	private final Connection connection;

	public DepartmentQueryInsert(String name, Connection connection) {
		super();
		setName(name);
		this.connection = connection;
	}

	@Override
	public boolean insert() {
		boolean inserted = false;
		try (PreparedStatement pst = connection.prepareStatement(SQL)){
			pst.setString(1, getName());
			int affectedRows = pst.executeUpdate();
			inserted = affectedRows > 0;
		} catch (SQLException e) {
			log.error("Error while inserting department", e);
		}
		return inserted;
	}

}
