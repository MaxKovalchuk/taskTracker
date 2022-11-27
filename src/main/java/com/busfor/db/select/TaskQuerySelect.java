package com.busfor.db.select;

import com.busfor.extended.ResultSetTaskGetResponse;
import com.busfor.model.TaskGetResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class TaskQuerySelect implements QuerySelect<TaskGetResponse> {

	private static final String QUERY = "SELECT * FROM all_tasks a WHERE a.id = ?";

	private final Logger log = LoggerFactory.getLogger(TaskQuerySelect.class);
	private final int taskId;
	private final Connection connection;

	public TaskQuerySelect(int taskId, Connection connection) {
		this.taskId = taskId;
		this.connection = connection;
	}

	@Override
	public List<TaskGetResponse> select() {
		List<TaskGetResponse> tasks = new ArrayList<>();
		try (PreparedStatement st = connection.prepareStatement(QUERY)) {
			st.setInt(1, taskId);
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				tasks.add(new ResultSetTaskGetResponse(rs));
			}
		} catch (SQLException e) {
			log.error(this.getClass().toString(), e);
		}
		return tasks;
	}
}
