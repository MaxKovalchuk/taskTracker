package com.busfor.controller;

import com.busfor.db.DBConnection;
import com.busfor.db.insert.CommentQueryInsert;
import com.busfor.db.select.CommentsByTaskIdQuerySelect;
import com.busfor.model.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class CommentController {

    private final DBConnection dbConnection;

    @Autowired
    public CommentController(DBConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    public boolean save(
            String text,
            int authorId,
            int taskId
    ) {
        CommentQueryInsert queryInsert = new CommentQueryInsert(text, authorId,
                taskId, dbConnection.connection());
        return queryInsert.insert();
    }

    public List<Comment> findByTaskId(int taskId){
        return new CommentsByTaskIdQuerySelect(taskId, dbConnection.connection()).select();
    }
}
