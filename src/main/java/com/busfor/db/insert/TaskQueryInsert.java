package com.busfor.db.insert;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.busfor.model.Task;

public class TaskQueryInsert extends Task implements QueryInsert {

	private final Logger log = LoggerFactory.getLogger(TaskQueryInsert.class);
	private final String SQL = "INSERT INTO task(title, description, user_created_id, estimate) "
			+ "VALUES(?, ?, ?, ?)";

	private final Connection connection;

	public TaskQueryInsert(String title, String description, Integer userCreatedId, Integer estimate,
			Connection connection) {
		super();
		setTitle(title);
		setDescription(description);
		setUserCreatedId(userCreatedId);
		setEstimate(estimate);
		this.connection = connection;
	}

	@Override
	public boolean insert() {
		boolean inserted = false;
		try (PreparedStatement pst = connection.prepareStatement(SQL)){
			pst.setString(1, getTitle());
			pst.setString(2, getDescription());
			pst.setInt(3, getUserCreatedId());
			pst.setInt(4, getEstimate());
			int affectedRows = pst.executeUpdate();
			inserted = affectedRows > 0;
		} catch (SQLException e) {
			log.error("Error while inserting task", e);
		}
		return inserted;
	}

}
