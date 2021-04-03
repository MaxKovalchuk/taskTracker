package com.busfor.db.insert;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.busfor.model.Attachment;
import org.springframework.web.multipart.MultipartFile;

public class AttachmentQueryInsert extends Attachment implements QueryInsert {

	private final Logger log = LoggerFactory.getLogger(AttachmentQueryInsert.class);
	private final String SQL = "INSERT INTO attachment(author_id, task_id, bytes) " + "VALUES(?,?,?)";

	private final Connection connection;
	private final MultipartFile file;

	public AttachmentQueryInsert(Integer authorId, Integer taskId, MultipartFile file, Connection connection) {
		super();
		setAuthorId(authorId);
		setTaskId(taskId);
		this.file = file;
		this.connection = connection;
	}

	@Override
	public boolean insert() {
		boolean inserted = false;
		try (PreparedStatement pst = connection.prepareStatement(SQL)){
			pst.setInt(1, getAuthorId());
			pst.setInt(2, getTaskId());
			pst.setBytes(3, file.getBytes());
			int affectedRows = pst.executeUpdate();
			inserted = affectedRows > 0;
		} catch (SQLException | IOException e) {
			log.error("Error while inserting attachment", e);
		}
		return inserted;
	}

}
