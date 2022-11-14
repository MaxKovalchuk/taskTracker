package com.busfor.controller;

import com.busfor.db.DBConnection;
import com.busfor.db.exists.TaskExistQuery;
import com.busfor.db.insert.TaskQueryInsert;
import com.busfor.db.select.AllTasksQuerySelect;
import com.busfor.db.update.TaskAssignQueryUpdate;
import com.busfor.db.update.TaskStatusQueryUpdate;
import com.busfor.model.TaskGetResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class TaskController {

    private final DBConnection dbConnection;

    @Autowired
    public TaskController(DBConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    public boolean save(
            String title,
            String description,
            int userCreatedId,
            int estimate
    ) {
        TaskQueryInsert queryInsert = new TaskQueryInsert(title, description,
                userCreatedId, estimate, dbConnection.connection());
        return queryInsert.insert();
    }

    public boolean exists(int id) {
        return new TaskExistQuery(dbConnection.connection(), id).exists();
    }

    public List<TaskGetResponse> findAll(
            int departmentId,
            boolean sortByDateCreated
    ) {
        return new AllTasksQuerySelect(departmentId, sortByDateCreated,
                dbConnection.connection()).select();
    }

    public boolean assign(
            int id,
            int userId
    ) {
        return new TaskAssignQueryUpdate(userId, id, dbConnection.connection()).update();
    }

    public boolean updateStatus(
            int id,
            int statusId
    ) {
        return new TaskStatusQueryUpdate(statusId, id, dbConnection.connection()).update();
    }
}
