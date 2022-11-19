package com.busfor.api;

import com.busfor.controller.*;
import com.busfor.model.*;
import com.busfor.pagination.Pagination;
import com.busfor.task.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import services.EstimatorService;

import java.util.Date;
import java.util.List;

@Component
public class V1ApiDelegateImpl implements V1ApiDelegate {

	private final AttachmentController attachmentController;
	private final DepartmentController departmentController;
	private final UserController userController;
	private final TaskController taskController;
	private final CommentController commentController;
	private final EstimatorService estimatorService;

	@Autowired
	public V1ApiDelegateImpl(
			AttachmentController attachmentController,
			DepartmentController departmentController,
			UserController userController,
			TaskController taskController,
			CommentController commentController,
			EstimatorService estimatorService
	) {
		this.estimatorService = estimatorService;
		this.attachmentController = attachmentController;
		this.departmentController = departmentController;
		this.userController = userController;
		this.taskController = taskController;
		this.commentController = commentController;
	}

	@Override
	public ResponseEntity<Ping> v1PingGet() {
		Ping pong = new Ping().localtime(new Date().getTime());
		return new ResponseEntity<>(pong, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Void> v1AttachmentPut(Integer authorId, Integer taskId, MultipartFile attachment) {
		ResponseEntity<Void> response = null;
		if (!isValidAttachmentPutRequest(authorId, taskId, attachment)) {
			response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} else {
			boolean inserted = attachmentController.save(authorId, taskId, attachment);
			if (inserted) {
				response = new ResponseEntity<>(HttpStatus.OK);
			} else {
				response = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		return response;
	}

	private boolean isValidAttachmentPutRequest(Integer authorId, Integer taskId, MultipartFile attachment) {
		return authorId != null
				&& taskId != null
				&& attachment != null;
	}

	@Override
	public ResponseEntity<Void> v1DepartmentPut(DepartmentPutRequest body) {
		ResponseEntity<Void> response = null;
		if (!isValidDepartmentPutRequest(body)) {
			response = new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
		} else {
			boolean inserted = departmentController.save(body.getName());
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
		} else if (!departmentController.exists(body.getDepartmentId())) {
			response = new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		} else {
			boolean inserted = userController.save(body.getName(), body.getDepartmentId());
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
        } else if (!userController.exists(body.getUserCreatedId())) {
			response = new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		} else {
			boolean inserted = taskController.save(body.getTitle(),
					body.getDescription(),
					body.getUserCreatedId(),
					body.getEstimate());
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
		} else if (!userController.exists(body.getAuthorId())
				|| !taskController.exists(body.getTaskId())) {
			response = new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		} else {
			boolean inserted = commentController.save(body.getText(), body.getAuthorId(),
					body.getTaskId());
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
	public ResponseEntity<List<TaskGetResponse>> v1TasksGet(Integer page, Integer pageLimit, Integer departmentId,
			Boolean sortByDateCreated) {
		int pageInt = page == null ? 0 : page;
		int pageLimitInt = pageLimit == null ? 0 : pageLimit;
		int departmentIdInt = departmentId == null ? 0 : departmentId;
		boolean sortByDateCreatedBool = sortByDateCreated != null && sortByDateCreated;
		List<TaskGetResponse> tasks = taskController.findAll(departmentIdInt, sortByDateCreatedBool);
		Pagination<TaskGetResponse> paginatedTasks = new Pagination<TaskGetResponse>(tasks, pageInt, pageLimitInt);
		return new ResponseEntity<List<TaskGetResponse>>(paginatedTasks.page(), HttpStatus.OK);
	}

	@Override
	public ResponseEntity<List<Comment>> v1TaskIdCommentsGet(Integer id, Integer page, Integer pageLimit) {
		ResponseEntity<List<Comment>> response = null;
		if (id == null || id <= 0) {
			response = new ResponseEntity<List<Comment>>(HttpStatus.BAD_REQUEST);
		} else {
			int pageInt = page == null ? 0 : page;
			int pageLimitInt = pageLimit == null ? 0 : pageLimit;
			List<Comment> comments = commentController.findByTaskId(id);
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
			List<Attachment> attachments = attachmentController.findByTaskId(id);
			if (attachments.isEmpty()) {
				response = new ResponseEntity<List<Attachment>>(HttpStatus.NOT_FOUND);
			} else {
				Pagination<Attachment> pagination = new Pagination<Attachment>(attachments, pageInt, pageLimitInt);
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
			if (userController.exists(userId)) {
				boolean assigned = taskController.assign(id, userId);
				if (assigned) {
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
			boolean updated = taskController.updateStatus(id, statusId);
			if (updated) {
				response = new ResponseEntity<Void>(HttpStatus.OK);
			}
		}
		return response;
	}

	@Override
	public ResponseEntity<Void> v1EstimatorConfigurePost(EstimatorConfigureRequest body) {
		ResponseEntity<Void> response;
		if (body == null || StringUtils.isEmpty(body.getText())
				|| body.getComplexity() == null || body.getComplexity() < 0) {
			response = new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
		} else {
			estimatorService.estimate(body.getText(), body.getComplexity());
			response = new ResponseEntity<Void>(HttpStatus.OK);
		}
		return response;
	}
}
