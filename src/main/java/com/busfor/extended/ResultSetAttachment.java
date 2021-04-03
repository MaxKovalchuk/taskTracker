package com.busfor.extended;

import com.busfor.model.Attachment;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ResultSetAttachment extends Attachment {

    private static final String ID_COLUMN = "id";
    private static final String AUTHOR_ID_COLUMN = "author_id";
    private static final String TASK_ID_COLUMN = "task_id";
    private static final String FILE_NAME_COLUMN = "file_name";
    private static final String BYTES_COLUMN = "bytes";

    public ResultSetAttachment(ResultSet resultSet) throws SQLException, IOException {
        setId(resultSet.getInt(ID_COLUMN));
        setAuthorId(resultSet.getInt(AUTHOR_ID_COLUMN));
        setTaskId(resultSet.getInt(TASK_ID_COLUMN));
        setFileName(resultSet.getString(FILE_NAME_COLUMN));
        setFileBytes(new String(resultSet.getBytes(BYTES_COLUMN)));
    }

}
