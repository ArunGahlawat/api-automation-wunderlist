package com.nagarro.requests;

public class TaskRequest {
	private Long list_id;
	private String title;
	private Long id;
	private Long assignee_id;
	private String created_at;
	private Long created_by_id;
	private String due_date;
	private Boolean starred;
	private Long revision;
	private Boolean completed;
	private String completed_at;
	private Long completed_by_id;
	private String recurrence_type;
	private Long recurrence_count;
	private String[] remove;
	private String type;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Long getList_id() {
		return list_id;
	}

	public void setList_id(Long list_id) {
		this.list_id = list_id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getAssignee_id() {
		return assignee_id;
	}

	public void setAssignee_id(Long assignee_id) {
		this.assignee_id = assignee_id;
	}

	public String getCreated_at() {
		return created_at;
	}

	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}

	public Long getCreated_by_id() {
		return created_by_id;
	}

	public void setCreated_by_id(Long created_by_id) {
		this.created_by_id = created_by_id;
	}

	public String getDue_date() {
		return due_date;
	}

	public void setDue_date(String due_date) {
		this.due_date = due_date;
	}

	public Boolean getStarred() {
		return starred;
	}

	public void setStarred(Boolean starred) {
		this.starred = starred;
	}

	public Long getRevision() {
		return revision;
	}

	public void setRevision(Long revision) {
		this.revision = revision;
	}

	public Boolean getCompleted() {
		return completed;
	}

	public void setCompleted(Boolean completed) {
		this.completed = completed;
	}

	public String getCompleted_at() {
		return completed_at;
	}

	public void setCompleted_at(String completed_at) {
		this.completed_at = completed_at;
	}

	public Long getCompleted_by_id() {
		return completed_by_id;
	}

	public void setCompleted_by_id(Long completed_by_id) {
		this.completed_by_id = completed_by_id;
	}

	public String getRecurrence_type() {
		return recurrence_type;
	}

	public void setRecurrence_type(String recurrence_type) {
		this.recurrence_type = recurrence_type;
	}

	public Long getRecurrence_count() {
		return recurrence_count;
	}

	public void setRecurrence_count(Long recurrence_count) {
		this.recurrence_count = recurrence_count;
	}

	public String[] getRemove() {
		return remove;
	}

	public void setRemove(String[] remove) {
		this.remove = remove;
	}
}
