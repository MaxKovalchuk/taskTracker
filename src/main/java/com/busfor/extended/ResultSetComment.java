package com.busfor.extended;

import com.busfor.model.Comment;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ResultSetComment extends Comment {

    private static final String ID_COLUMN = "id";
    private static final String TEXT_COLUMN = "text";
    private static final String AUTHOR_ID_COLUMN = "author_id";
    private static final String TASK_ID_COLUMN = "task_id";
    private static final String CREATED_AT_COLUMN = "created_at";
    private static final String IS_EDITED_COLUMN = "is_edited";

    public ResultSetComment(ResultSet resultSet) throws SQLException {
        setId(resultSet.getInt(ID_COLUMN));
        setText(resultSet.getString(TEXT_COLUMN));
        setTaskId(resultSet.getInt(TASK_ID_COLUMN));
        setAuthorId(resultSet.getInt(AUTHOR_ID_COLUMN));
        setCreatedAt(resultSet.getString(CREATED_AT_COLUMN));
        setIsEdited(resultSet.getBoolean(IS_EDITED_COLUMN));
    }
}
