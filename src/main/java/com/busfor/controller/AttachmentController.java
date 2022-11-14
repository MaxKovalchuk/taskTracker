package com.busfor.controller;

import com.busfor.db.DBConnection;
import com.busfor.db.insert.AttachmentQueryInsert;
import com.busfor.db.select.AttachmentsByTaskIdQuerySelect;
import com.busfor.model.Attachment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
public class AttachmentController {

    private final DBConnection dbConnection;

    @Autowired
    public AttachmentController(DBConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    public boolean save(
            Integer authorId,
            Integer taskId,
            MultipartFile attachment
    ) {
        AttachmentQueryInsert queryInsert = new AttachmentQueryInsert(
                authorId,
                taskId,
                attachment,
                dbConnection.connection()
        );
        return queryInsert.insert();
    }

    public List<Attachment> findByTaskId(int taskId){
        return new AttachmentsByTaskIdQuerySelect(taskId, dbConnection.connection()).select();
    }


}
