package com.nagarro.requests;

public class TaskRequest {
	private Integer list_id;
	private String title;
	private Integer id;
	private Integer assignee_id;
	private String created_at;
	private Integer created_by_id;
	private String due_date;
	private Boolean starred;
	private Integer revision;
	private Boolean completed;
	private String completed_at;
	private Integer completed_by_id;
	private String recurrence_type;
	private Integer recurrence_count;
	private String[] remove;

	public Integer getList_id() {
		return list_id;
	}

	public void setList_id(Integer list_id) {
		this.list_id = list_id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getAssignee_id() {
		return assignee_id;
	}

	public void setAssignee_id(Integer assignee_id) {
		this.assignee_id = assignee_id;
	}

	public String getCreated_at() {
		return created_at;
	}

	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}

	public Integer getCreated_by_id() {
		return created_by_id;
	}

	public void setCreated_by_id(Integer created_by_id) {
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

	public Integer getRevision() {
		return revision;
	}

	public void setRevision(Integer revision) {
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

	public Integer getCompleted_by_id() {
		return completed_by_id;
	}

	public void setCompleted_by_id(Integer completed_by_id) {
		this.completed_by_id = completed_by_id;
	}

	public String getRecurrence_type() {
		return recurrence_type;
	}

	public void setRecurrence_type(String recurrence_type) {
		this.recurrence_type = recurrence_type;
	}

	public Integer getRecurrence_count() {
		return recurrence_count;
	}

	public void setRecurrence_count(Integer recurrence_count) {
		this.recurrence_count = recurrence_count;
	}

	public String[] getRemove() {
		return remove;
	}

	public void setRemove(String[] remove) {
		this.remove = remove;
	}
}
