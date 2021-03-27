package com.busfor.extended;

import com.busfor.model.TaskGetResponse;
import com.busfor.model.UserWithDepartment;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ResultSetTaskGetResponse extends TaskGetResponse {

    private static final String ID_COLUMN = "id";
    private static final String TITLE_COLUMN = "title";
    private static final String DESCRIPTION_COLUMN = "description";
    private static final String CREATED_AT_COLUMN = "created_at";
    private static final String STATUS_COLUMN = "status";
    private static final String ESTIMATE_COLUMN = "estimate";

    private static final String USER_CREATED_ID_COLUMN = "user_created_id";
    private static final String USER_CREATED_NAME_COLUMN = "user_created_name";
    private static final String DEPARTMENT_CREATED_ID_COLUMN = "department_created_id";
    private static final String DEPARTMENT_CREATED_NAME_COLUMN = "department_created_name";

    private static final String USER_PERFORMER_ID_COLUMN = "user_performer_id";
    private static final String USER_PERFORMER_NAME_COLUMN = "user_performer_name";
    private static final String DEPARTMENT_PERFORMER_ID_COLUMN = "department_performed_id";
    private static final String DEPARTMENT_PERFORMER_NAME_COLUMN = "department_performed_name";

    public ResultSetTaskGetResponse(ResultSet resultSet) throws SQLException {
        setId(resultSet.getInt(ID_COLUMN));
        setTitle(resultSet.getString(TITLE_COLUMN));
        setDescription(resultSet.getString(DESCRIPTION_COLUMN));
        setCreatedAt(resultSet.getString(CREATED_AT_COLUMN));
        setUserCreated(userCreated(resultSet));
        setUserPerformer(userPerformer(resultSet));
        setStatus(resultSet.getString(STATUS_COLUMN));
        setEstimate(resultSet.getInt(ESTIMATE_COLUMN));
    }

    private UserWithDepartment userCreated(ResultSet resultSet) throws SQLException {
        return new UserWithDepartment().id(resultSet.getInt(USER_CREATED_ID_COLUMN))
                .name(resultSet.getString(USER_CREATED_NAME_COLUMN))
                .departmentId(resultSet.getInt(DEPARTMENT_CREATED_ID_COLUMN))
                .departmentName(resultSet.getString(DEPARTMENT_CREATED_NAME_COLUMN));
    }

    private UserWithDepartment userPerformer(ResultSet resultSet) throws SQLException {
        return new UserWithDepartment().id(resultSet.getInt(USER_PERFORMER_ID_COLUMN))
                .name(resultSet.getString(USER_PERFORMER_NAME_COLUMN))
                .departmentId(resultSet.getInt(DEPARTMENT_PERFORMER_ID_COLUMN))
                .departmentName(resultSet.getString(DEPARTMENT_PERFORMER_NAME_COLUMN));
    }

}
