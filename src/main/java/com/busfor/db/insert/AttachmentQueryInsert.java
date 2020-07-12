package com.busfor.db.insert;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.busfor.model.Attachment;

public class AttachmentQueryInsert extends Attachment implements QueryInsert {

	private final Logger log = LoggerFactory.getLogger(AttachmentQueryInsert.class);
	private final String SQL = "INSERT INTO attachment(author_id, task_id, aws_link) " + "VALUES(?,?,?)";

	private final Connection connection;

	public AttachmentQueryInsert(Integer authorId, Integer taskId, String awsLink, Connection connection) {
		super();
		setAuthorId(authorId);
		setTaskId(taskId);
		setAwsLink(awsLink);
		this.connection = connection;
	}

	@Override
	public boolean insert() {
		boolean inserted = false;
		try (PreparedStatement pst = connection.prepareStatement(SQL)){
			pst.setInt(1, getAuthorId());
			pst.setInt(2, getTaskId());
			pst.setString(3, getAwsLink());
			int affectedRows = pst.executeUpdate();
			inserted = affectedRows > 0;
		} catch (SQLException e) {
			log.error("Error while inserting attachemnt", e);
		}
		return inserted;
	}

}
