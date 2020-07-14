package com.busfor.api;

import java.util.Date;
import java.util.List;

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
import com.busfor.db.select.AllTasksQuerySelect;
import com.busfor.db.select.AttachmentsByTaskIdQuerySelect;
import com.busfor.db.select.CommentsByTaskIdQuerySelect;
import com.busfor.db.update.TaskAssignQueryUpdate;
import com.busfor.db.update.TaskStatusQueryUpdate;
import com.busfor.model.Attachment;
import com.busfor.model.AttachmentPutRequest;
import com.busfor.model.Comment;
import com.busfor.model.CommentPutRequest;
import com.busfor.model.DepartmentPutRequest;
import com.busfor.model.Ping;
import com.busfor.model.TaskGetResponse;
import com.busfor.model.TaskPutRequest;
import com.busfor.model.UserPutRequest;
import com.busfor.pagination.Pagination;
import com.busfor.task.Status;

import rating.service.RatingService;

@Component
public class V1ApiDelegateImpl implements V1ApiDelegate {

	DBConnection dbConnection;
	RatingService ratingService;

	public V1ApiDelegateImpl() {
	}

	@Autowired
	public V1ApiDelegateImpl(DBConnection dbConnection, RatingService ratingService) {
		this.dbConnection = dbConnection;
		this.ratingService = ratingService;
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

	@Override
	public ResponseEntity<List<TaskGetResponse>> v1TasksGet(Integer page, Integer pageLimit, Integer departmentId,
			Boolean sortByDateCreated) {
		int pageInt = page == null ? 0 : page;
		int pageLimitInt = pageLimit == null ? 0 : pageLimit;
		int departmentIdInt = departmentId == null ? 0 : departmentId;
		boolean sortByDateCreatedBool = sortByDateCreated == null ? false : sortByDateCreated;
		AllTasksQuerySelect query = new AllTasksQuerySelect(departmentIdInt, sortByDateCreatedBool,
				dbConnection.connection(), ratingService);
		Pagination<TaskGetResponse> tasks = new Pagination<TaskGetResponse>(query.select(), pageInt, pageLimitInt);
		return new ResponseEntity<List<TaskGetResponse>>(tasks.page(), HttpStatus.OK);
	}

	@Override
	public ResponseEntity<List<Comment>> v1TaskIdCommentsGet(Integer id, Integer page, Integer pageLimit) {
		ResponseEntity<List<Comment>> response = null;
		if (id == null || id <= 0) {
			response = new ResponseEntity<List<Comment>>(HttpStatus.BAD_REQUEST);
		} else {
			int pageInt = page == null ? 0 : page;
			int pageLimitInt = pageLimit == null ? 0 : pageLimit;
			CommentsByTaskIdQuerySelect query = new CommentsByTaskIdQuerySelect(id, dbConnection.connection());
			List<Comment> comments = query.select();
			if (comments.isEmpty()) {
				response = new ResponseEntity<List<Comment>>(HttpStatus.NOT_FOUND);
			} else {
				Pagination<Comment> pagination = new Pagination<Comment>(comments, pageInt, pageLimitInt);
				response = new ResponseEntity<List<Comment>>(pagination.page(), HttpStatus.OK);
			}
		}
		return response;
	}

	@Override
	public ResponseEntity<List<Attachment>> v1TaskIdAttachmentsGet(Integer id, Integer page, Integer pageLimit) {
		ResponseEntity<List<Attachment>> response = null;
		if (id == null || id <= 0) {
			response = new ResponseEntity<List<Attachment>>(HttpStatus.BAD_REQUEST);
		} else {
			int pageInt = page == null ? 0 : page;
			int pageLimitInt = pageLimit == null ? 0 : pageLimit;
			AttachmentsByTaskIdQuerySelect query = new AttachmentsByTaskIdQuerySelect(id, dbConnection.connection());
			List<Attachment> comments = query.select();
			if (comments.isEmpty()) {
				response = new ResponseEntity<List<Attachment>>(HttpStatus.NOT_FOUND);
			} else {
				Pagination<Attachment> pagination = new Pagination<Attachment>(comments, pageInt, pageLimitInt);
				response = new ResponseEntity<List<Attachment>>(pagination.page(), HttpStatus.OK);
			}
		}
		return response;
	}

	@Override
	public ResponseEntity<Void> v1TaskIdAssignPost(Integer id, Integer userId) {
		ResponseEntity<Void> response = new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		if (id == null || id <= 0 || userId == null || userId <= 0) {
			response = new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
		} else {
			if (new UserExistQuery(dbConnection.connection(), userId).exists()) {
				TaskAssignQueryUpdate query = new TaskAssignQueryUpdate(userId, id, dbConnection.connection());
				if (query.update()) {
					response = new ResponseEntity<Void>(HttpStatus.OK);
				}
			}
		}
		return response;
	}

	@Override
	public ResponseEntity<Void> v1TaskIdStatusPost(Integer id, Integer statusId) {
		ResponseEntity<Void> response = new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		if (id == null || id <= 0 || statusId == null || statusId < 0 || statusId > Status.values().length) {
			response = new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
		} else {
			TaskStatusQueryUpdate query = new TaskStatusQueryUpdate(statusId, id, dbConnection.connection());
			if (query.update()) {
				response = new ResponseEntity<Void>(HttpStatus.OK);
			}
		}
		return response;
	}
}
