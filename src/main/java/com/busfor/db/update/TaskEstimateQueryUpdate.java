package com.busfor.db.update;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TaskEstimateQueryUpdate implements QueryUpdate{

	private final String SQL = "UPDATE task SET estimate = ? WHERE id = ?";
	private final Logger log = LoggerFactory.getLogger(TaskEstimateQueryUpdate.class);
	private final int estimate;
	private final int taskId;
	private final Connection connection;

	public TaskEstimateQueryUpdate(int estimate, int taskId, Connection connection) {
		this.estimate = estimate;
		this.taskId = taskId;
		this.connection = connection;
	}

	@Override
	public boolean update() {
		boolean updated = false;
		try (PreparedStatement pst = connection.prepareStatement(SQL)) {
			pst.setInt(1, estimate);
			pst.setInt(2, taskId);
			updated = pst.executeUpdate() > 0;
		} catch (SQLException e) {
			log.error(this.getClass().toString(), e);
		}
		return updated;
	}

}
