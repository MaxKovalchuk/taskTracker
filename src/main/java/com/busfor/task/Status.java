package com.busfor.task;

public enum Status {

	OPEN(0 , "Open"),
	IN_PROGRESS(1, "In progress"),
	WAITNING_FOR_CODE_REVIEW(2, "Waitning for code review"),
	CODE_REVIEW(3, "Code review"),
	WAITING_FOR_QA(4, "Waiting for QA"),
	QA(5, "QA"),
	DONE(6, "Done"),
	READY_FOR_RELISE(7, "Ready for relise"),
	REOPENED(7, "Reopened");
	
	private int code;
	private String name;
	
	private Status(int code, String name) {
		this.code = code;
		this.name = name;
	}
	
	public Status status(int code) {
		Status status = null;
		for(Status s : values()) {
			if(s.getCode() == code) {
				status = s;
				break;
			}
		}
		return status;
	}
	
	public int getCode() {
		return code;
	}
	
	public String getName() {
		return name;
	}
}
