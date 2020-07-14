package com.busfor.db.update;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TaskStatusQueryUpdate implements QueryUpdate{

	private final String SQL = "UPDATE task SET status = ? WHERE id = ?";
	private final Logger log = LoggerFactory.getLogger(TaskStatusQueryUpdate.class);
	private final int status;
	private final int taskId;
	private final Connection connection;

	public TaskStatusQueryUpdate(int status, int taskId, Connection connection) {
		this.status = status;
		this.taskId = taskId;
		this.connection = connection;
	}

	@Override
	public boolean update() {
		boolean updated = false;
		try (PreparedStatement pst = connection.prepareStatement(SQL)) {
			pst.setInt(1, status);
			pst.setInt(2, taskId);
			updated = pst.executeUpdate() > 0;
		} catch (SQLException e) {
			log.error(this.getClass().toString(), e);
		}
		return updated;
	}

}
