package com.busfor.controller;

import com.busfor.db.DBConnection;
import com.busfor.db.exists.TaskExistQuery;
import com.busfor.db.insert.TaskQueryInsert;
import com.busfor.db.select.AllTasksQuerySelect;
import com.busfor.db.select.TaskQuerySelect;
import com.busfor.db.update.*;
import com.busfor.model.TaskGetResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import services.EstimatorService;

import java.io.IOException;
import java.util.List;

@Controller
public class TaskController {

    private final DBConnection dbConnection;
    private final EstimatorService estimatorService;

    @Autowired
    public TaskController(
            DBConnection dbConnection,
            EstimatorService estimatorService
    ) {
        this.dbConnection = dbConnection;
        this.estimatorService = estimatorService;
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

    public boolean updateTitle(
            int id,
            String title
    ) {
        boolean updated = new TaskTitleQueryUpdate(title, id, dbConnection.connection()).update();
        if (updated) {
            List<TaskGetResponse> select = new TaskQuerySelect(id, dbConnection.connection()).select();
            if (!select.isEmpty()) {
                TaskGetResponse task = select.get(0);
                try {
                    int estimate = estimatorService.estimate(task.getTitle(), task.getDescription());
                    updateEstimate(id, estimate);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return updated;
    }

    public boolean updateDescription(
            int id,
            String description
    ) {
        boolean updated = new TaskDescriptionQueryUpdate(description, id, dbConnection.connection()).update();
        if (updated) {
            List<TaskGetResponse> select = new TaskQuerySelect(id, dbConnection.connection()).select();
            if (!select.isEmpty()) {
                TaskGetResponse task = select.get(0);
                try {
                    int estimate = estimatorService.estimate(task.getTitle(), task.getDescription());
                    updateEstimate(id, estimate);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return updated;
    }

    public boolean updateEstimate(
            int id,
            int estimate
    ) {
        return new TaskEstimateQueryUpdate(estimate, id, dbConnection.connection()).update();
    }
}
