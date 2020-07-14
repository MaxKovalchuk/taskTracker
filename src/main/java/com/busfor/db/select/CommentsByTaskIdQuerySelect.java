package com.busfor.db.select;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.busfor.model.Comment;
import com.busfor.model.extended.ResultSetComment;

public class CommentsByTaskIdQuerySelect implements QuerySelect<Comment> {

	private final String SQL = "select * from comment_by_task_id(?)";
	private final Logger log = LoggerFactory.getLogger(CommentsByTaskIdQuerySelect.class);
	private final int taskId;
	private final Connection connection;

	public CommentsByTaskIdQuerySelect(int taskId, Connection connection) {
		super();
		this.taskId = taskId;
		this.connection = connection;
	}

	@Override
	public List<Comment> select() {
		List<Comment> comments = new ArrayList<Comment>();
		try (PreparedStatement pst = connection.prepareStatement(SQL)) {
			pst.setInt(1, taskId);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				comments.add(new ResultSetComment(rs));
			}
		} catch (SQLException e) {
			log.error(this.getClass().toString(), e);
		}
		return comments;
	}

}
