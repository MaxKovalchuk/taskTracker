package com.busfor.db.select;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.busfor.model.TaskGetResponse;
import com.busfor.extended.ResultSetTaskGetResponse;

import rating.service.RatingService;


public class AllTasksQuerySelect implements QuerySelect<TaskGetResponse> {

	private static final String ID = "id";
	private static final String TITLE = "title";
	private static final String DESCRIPTION = "description";
	private static final String CREATED_AT = "created_at";
	private static final String DEPARTMENT_CREATED_ID = "department_created_id";
	private static final String STATUS = "status";
	private static final String ESTIMATE = "estimate";

	private final Logger log = LoggerFactory.getLogger(AllTasksQuerySelect.class);
	private final int departmentId;
	private final boolean sortByDateCreated;
	private final Connection connection;
	private final RatingService ratingService;

	public AllTasksQuerySelect(int departmentId, boolean sortByDateCreated, Connection connection,
			RatingService ratingService) {
		this.departmentId = departmentId;
		this.sortByDateCreated = sortByDateCreated;
		this.connection = connection;
		this.ratingService = ratingService;
	}

	@Override
	public List<TaskGetResponse> select() {
		List<TaskGetResponse> tasks = new ArrayList<>();
		try (Statement st = connection.createStatement()) {
			ResultSet rs = st.executeQuery(query());
			while (rs.next()) {
				tasks.add(new ResultSetTaskGetResponse(rs));
			}
		} catch (SQLException e) {
			log.error(this.getClass().toString(), e);
		}
		return tasks;
	}
	

	private String query() {
		String query = "SELECT * FROM all_tasks a";
		query = byDepartmentId(query);
		query = sortByDate(query);
		return query;
	}

	private String byDepartmentId(String query) {
		String finalQuery = query;
		if (departmentId > 0) {
			finalQuery += " WHERE a." + DEPARTMENT_CREATED_ID + " = " + departmentId;
		}
		return finalQuery;
	}

	private String sortByDate(String query) {
		String finalQuery = query;
		if (sortByDateCreated) {
			finalQuery += " ORDER BY a." + CREATED_AT;
		}
		return finalQuery;
	}
}
