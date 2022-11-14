package com.busfor.controller;

import com.busfor.db.DBConnection;
import com.busfor.db.exists.UserExistQuery;
import com.busfor.db.insert.UserQueryInsert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class UserController {

    private final DBConnection dbConnection;

    @Autowired
    public UserController(DBConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    public boolean save(
            String name,
            int departmentId
    ) {
        UserQueryInsert insertQuery = new UserQueryInsert(name, departmentId, dbConnection.connection());
        return insertQuery.insert();
    }

    public boolean exists(int id) {
        return new UserExistQuery(dbConnection.connection(), id).exists();
    }

}
