package com.busfor.db.update;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TaskAssignQueryUpdate implements QueryUpdate {

	private final String SQL = "UPDATE task SET user_performer_id = ? WHERE id = ?";
	private final Logger log = LoggerFactory.getLogger(TaskAssignQueryUpdate.class);
	private final int userId;
	private final int taskId;
	private final Connection connection;

	public TaskAssignQueryUpdate(int userId, int taskId, Connection connection) {
		this.userId = userId;
		this.taskId = taskId;
		this.connection = connection;
	}

	@Override
	public boolean update() {
		boolean updated = false;
		try (PreparedStatement pst = connection.prepareStatement(SQL)) {
			pst.setInt(1, userId);
			pst.setInt(2, taskId);
			updated = pst.executeUpdate() > 0;
		} catch (SQLException e) {
			log.error(this.getClass().toString(), e);
		}
		return updated;
	}

}
