package com.busfor.controller;

import com.busfor.db.DBConnection;
import com.busfor.db.exists.DepartmentExistQuery;
import com.busfor.db.insert.DepartmentQueryInsert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class DepartmentController {

    private final DBConnection dbConnection;

    @Autowired
    public DepartmentController(DBConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    public boolean save(String name) {
        DepartmentQueryInsert insertQuery = new DepartmentQueryInsert(name, dbConnection.connection());
        return insertQuery.insert();
    }

    public boolean exists(int id) {
        return new DepartmentExistQuery(dbConnection.connection(), id).exists();
    }
}
