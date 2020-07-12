package com.busfor.db.insert;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.busfor.model.Comment;

public class CommentQueryInsert extends Comment implements QueryInsert {

	private final Logger log = LoggerFactory.getLogger(CommentQueryInsert.class);
	private final String SQL = "INSERT INTO comment(text, author_id, task_id) " + "VALUES(?,?,?)";

	private final Connection connection;

	public CommentQueryInsert(String text, Integer authorId, Integer taskId, Connection connection) {
		super();
		setText(text);
		setAuthorId(authorId);
		setTaskId(taskId);
		this.connection = connection;
	}

	@Override
	public boolean insert() {
		boolean inserted = false;
		try (PreparedStatement pst = connection.prepareStatement(SQL)){
			pst.setString(1, getText());
			pst.setInt(2, getAuthorId());
			pst.setInt(3, getTaskId());
			int affectedRows = pst.executeUpdate();
			inserted = affectedRows > 0;
		} catch (SQLException e) {
			log.error("Error while inserting task", e);
		}
		return inserted;
	}

}
