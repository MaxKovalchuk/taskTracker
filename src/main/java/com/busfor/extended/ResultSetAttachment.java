package com.busfor.extended;

import com.busfor.model.Attachment;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ResultSetAttachment extends Attachment {

    private static final String ID_COLUMN = "id";
    private static final String AUTHOR_ID_COLUMN = "author_id";
    private static final String AWS_LINK_COLUMN = "aws_link";
    private static final String TASK_ID_COLUMN = "task_id";

    public ResultSetAttachment(ResultSet resultSet) throws SQLException {
        setId(resultSet.getInt(ID_COLUMN));
        setAuthorId(resultSet.getInt(AUTHOR_ID_COLUMN));
        setAwsLink(resultSet.getString(AWS_LINK_COLUMN));
        setTaskId(resultSet.getInt(TASK_ID_COLUMN));
    }
}
