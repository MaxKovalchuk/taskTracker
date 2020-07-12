package com.busfor.api;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.busfor.db.DBConnection;
import com.busfor.db.exists.DepartmentExistQuery;
import com.busfor.db.exists.TaskExistQuery;
import com.busfor.db.exists.UserExistQuery;
import com.busfor.db.insert.CommentQueryInsert;
import com.busfor.db.insert.DepartmentQueryInsert;
import com.busfor.db.insert.TaskQueryInsert;
import com.busfor.db.insert.UserQueryInsert;
import com.busfor.model.AttachmentPutRequest;
import com.busfor.model.CommentPutRequest;
import com.busfor.model.DepartmentPutRequest;
import com.busfor.model.Ping;
import com.busfor.model.TaskPutRequest;
import com.busfor.model.UserPutRequest;

@Component
public class V1ApiDelegateImpl implements V1ApiDelegate {

	DBConnection dbConnection;

	public V1ApiDelegateImpl() {
	}

	@Autowired
	public V1ApiDelegateImpl(DBConnection dbConnection) {
		this.dbConnection = dbConnection;
	}

	@Override
	public ResponseEntity<Ping> v1PingGet() {
		Ping pong = new Ping().localtime(new Date().getTime());
		return new ResponseEntity<>(pong, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Void> v1DepartmentPut(DepartmentPutRequest body) {
		ResponseEntity<Void> response = null;
		if (!isValidDepartmentPutRequest(body)) {
			response = new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
		} else {
			DepartmentQueryInsert insertQuery = new DepartmentQueryInsert(body.getName(), dbConnection.connection());
			boolean inserted = insertQuery.insert();
			if (inserted) {
				response = new ResponseEntity<Void>(HttpStatus.OK);
			} else {
				response = new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		return response;
	}

	private boolean isValidDepartmentPutRequest(DepartmentPutRequest body) {
		return body.getName() != null && !body.getName().isEmpty();
	}

	@Override
	public ResponseEntity<Void> v1UserPut(UserPutRequest body) {
		ResponseEntity<Void> response = null;
		if (!isValidUserPutRequest(body)) {
			response = new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
		} else if (!new DepartmentExistQuery(dbConnection.connection(), body.getDepartmentId()).exists()) {
			response = new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		} else {
			UserQueryInsert insertQuery = new UserQueryInsert(body.getName(), body.getDepartmentId(),
					dbConnection.connection());
			boolean inserted = insertQuery.insert();
			if (inserted) {
				response = new ResponseEntity<Void>(HttpStatus.OK);
			} else {
				response = new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		return response;
	}

	private boolean isValidUserPutRequest(UserPutRequest body) {
		return body.getName() != null && !body.getName().isEmpty() && body.getDepartmentId() != null;
	}

	@Override
	public ResponseEntity<Void> v1TaskPut(TaskPutRequest body) {
		ResponseEntity<Void> response = null;
		if (!isValidTaskPutRequest(body)) {
			response = new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
		} else if (!new UserExistQuery(dbConnection.connection(), body.getUserCreatedId()).exists()) {
			response = new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		} else {
			TaskQueryInsert insertQuery = new TaskQueryInsert(body.getTitle(), body.getDescription(),
					body.getUserCreatedId(), body.getEstimate(), dbConnection.connection());
			boolean inserted = insertQuery.insert();
			if (inserted) {
				response = new ResponseEntity<Void>(HttpStatus.OK);
			} else {
				response = new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		return response;
	}

	private boolean isValidTaskPutRequest(TaskPutRequest body) {
		return body.getTitle() != null && !body.getTitle().isEmpty() && body.getUserCreatedId() != null;
	}

	@Override
	public ResponseEntity<Void> v1CommentPut(CommentPutRequest body) {
		ResponseEntity<Void> response = null;
		if (!isValidCommentPutRequest(body)) {
			response = new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
		} else if (!new UserExistQuery(dbConnection.connection(), body.getAuthorId()).exists()
				|| !new TaskExistQuery(dbConnection.connection(), body.getTaskId()).exists()) {
			response = new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		} else {
			CommentQueryInsert insertQuery = new CommentQueryInsert(body.getText(), body.getAuthorId(),
					body.getTaskId(), dbConnection.connection());
			boolean inserted = insertQuery.insert();
			if (inserted) {
				response = new ResponseEntity<Void>(HttpStatus.OK);
			} else {
				response = new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		return response;
	}

	private boolean isValidCommentPutRequest(CommentPutRequest body) {
		return body.getText() != null && !body.getText().isEmpty() && body.getAuthorId() != null
				&& body.getTaskId() != null;
	}

	@Override
	public ResponseEntity<Void> v1AttachmentPut(AttachmentPutRequest body) {
		// validate body
		// save file to s3 and create access link
		// save link and other data to database
		return new ResponseEntity<Void>(HttpStatus.NOT_IMPLEMENTED);
	}
}
