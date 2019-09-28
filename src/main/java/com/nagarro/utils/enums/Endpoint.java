package com.nagarro.utils.enums;

public enum  Endpoint {
	AUTH_CODE("authorize"),
	AUTH_ACCESS_TOKEN("access_token"),
	LISTS("lists"),
	NOTES("notes"),
	SUBTASKS("subtasks"),
	TASKS("tasks"),
	TASK_COMMENTS("task_comments")
	;

	String endpoint;
	Endpoint(String endpoint) {
		this.endpoint = endpoint;
	}

	public String value() {
		return this.endpoint;
	}
}
