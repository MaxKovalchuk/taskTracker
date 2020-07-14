package com.busfor.db.select;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.busfor.model.TaskGetResponse;
import com.busfor.model.UserWithDepartment;

/*TO DO: refactor:
 * extend from swagger models to make model which forms from ResultSet
 * make SQL query as component
 * extend from it with WHERE statements
*/

public class AllTasksQuerySelect implements QuerySelect<TaskGetResponse> {

	private static final String ID = "id";
	private static final String TITLE = "title";
	private static final String DESCRIPTION = "description";
	private static final String CREATED_AT = "created_at";
	private static final String USER_CREATED_ID = "user_created_id";
	private static final String USER_CREATED_NAME = "user_created_name";
	private static final String DEPARTMENT_CREATED_ID = "department_created_id";
	private static final String DEPARTMENT_CREATED_NAME = "department_created_name";
	private static final String USER_PERFORMER_ID = "user_performer_id";
	private static final String USER_PERFORMER_NAME = "user_performer_name";
	private static final String DEPARTMENT_PERFORMED_ID = "department_performed_id";
	private static final String DEPARTMENT_PERFORMED_NAME = "department_performed_name";
	private static final String STATUS = "id";
	private static final String ESTIMATE = "estimate";

	private final Logger log = LoggerFactory.getLogger(AllTasksQuerySelect.class);
	private final int departmentId;
	private final boolean sortByDateCreated;
	private final Connection connection;

	public AllTasksQuerySelect(int departmentId, boolean sortByDateCreated,
			Connection connection) {
		this.departmentId = departmentId;
		this.sortByDateCreated = sortByDateCreated;
		this.connection = connection;
	}

	@Override
	public List<TaskGetResponse> select() {
		List<TaskGetResponse> tasks = new ArrayList<TaskGetResponse>();
		try (Statement st = connection.createStatement()) {
			ResultSet rs = st.executeQuery(query());
			while (rs.next()) {
				tasks.add(taskFromResultSet(rs));
			}
		} catch (SQLException e) {
			log.error(this.getClass().toString(), e);
		}
		return tasks;
	}

	private TaskGetResponse taskFromResultSet(ResultSet rs) throws SQLException {
		TaskGetResponse task = new TaskGetResponse();
		task.setId(rs.getInt(ID));
		task.setTitle(rs.getString(TITLE));
		task.setDescription(rs.getString(DESCRIPTION));
		task.setCreatedAt(rs.getString(CREATED_AT));
		task.setUserCreated(userFromResultSet(rs, USER_CREATED_ID, USER_CREATED_NAME, DEPARTMENT_CREATED_ID,
				DEPARTMENT_CREATED_NAME));
		task.setUserPerformer(userFromResultSet(rs, USER_PERFORMER_ID, USER_PERFORMER_NAME, DEPARTMENT_PERFORMED_ID,
				DEPARTMENT_PERFORMED_NAME));
		task.setStatus(rs.getInt(STATUS));
		task.setEstimate(rs.getInt(ESTIMATE));
		return task;
	}

	private UserWithDepartment userFromResultSet(ResultSet rs, String userId, String userName, String departmentId,
			String departmentName) throws SQLException {
		UserWithDepartment user = new UserWithDepartment();
		user.setId(rs.getInt(userId));
		user.setName(rs.getString(userName));
		user.setDepartmentId(rs.getInt(departmentId));
		user.setDepartmentName(rs.getString(departmentName));
		return user;
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
