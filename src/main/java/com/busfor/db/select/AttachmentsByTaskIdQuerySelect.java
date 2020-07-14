package com.busfor.db.select;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.busfor.model.Attachment;
import com.busfor.model.extended.ResultSetAttachment;

public class AttachmentsByTaskIdQuerySelect implements QuerySelect<Attachment> {

	private final String SQL = "select * from attachment_by_task_id(?)";
	private final Logger log = LoggerFactory.getLogger(AttachmentsByTaskIdQuerySelect.class);
	private final int taskId;
	private final Connection connection;

	public AttachmentsByTaskIdQuerySelect(int taskId, Connection connection) {
		super();
		this.taskId = taskId;
		this.connection = connection;
	}

	@Override
	public List<Attachment> select() {
		List<Attachment> attachments = new ArrayList<Attachment>();
		try (PreparedStatement pst = connection.prepareStatement(SQL)) {
			pst.setInt(1, taskId);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				attachments.add(new ResultSetAttachment(rs));
			}
		} catch (SQLException e) {
			log.error(this.getClass().toString(), e);
		}
		return attachments;
	}

}
