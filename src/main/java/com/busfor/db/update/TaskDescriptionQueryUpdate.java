package com.busfor.db.update;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TaskDescriptionQueryUpdate implements QueryUpdate{

	private final String SQL = "UPDATE task SET description = ? WHERE id = ?";
	private final Logger log = LoggerFactory.getLogger(TaskDescriptionQueryUpdate.class);
	private final String description;
	private final int taskId;
	private final Connection connection;

	public TaskDescriptionQueryUpdate(String description, int taskId, Connection connection) {
		this.description = description;
		this.taskId = taskId;
		this.connection = connection;
	}

	@Override
	public boolean update() {
		boolean updated = false;
		try (PreparedStatement pst = connection.prepareStatement(SQL)) {
			pst.setString(1, description);
			pst.setInt(2, taskId);
			updated = pst.executeUpdate() > 0;
		} catch (SQLException e) {
			log.error(this.getClass().toString(), e);
		}
		return updated;
	}

}
