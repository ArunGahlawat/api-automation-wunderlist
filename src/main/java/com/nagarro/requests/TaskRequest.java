package com.nagarro.requests;

public class TaskRequest {
	private int list_id;
	private String title;
	private int id;
	private int assignee_id;
	private String created_at;
	private int created_by_id;
	private String due_date;
	private boolean starred;
	private int revision;
	private boolean completed;
	private String completed_at;
	private int completed_by_id;
	private String recurrence_type;
	private int recurrence_count;
	private String[] remove;

	public String[] getRemove() {
		return remove;
	}

	public void setRemove(String[] remove) {
		this.remove = remove;
	}

	public int getList_id() {
		return list_id;
	}

	public void setList_id(int list_id) {
		this.list_id = list_id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getAssignee_id() {
		return assignee_id;
	}

	public void setAssignee_id(int assignee_id) {
		this.assignee_id = assignee_id;
	}

	public String getCreated_at() {
		return created_at;
	}

	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}

	public int getCreated_by_id() {
		return created_by_id;
	}

	public void setCreated_by_id(int created_by_id) {
		this.created_by_id = created_by_id;
	}

	public String getDue_date() {
		return due_date;
	}

	public void setDue_date(String due_date) {
		this.due_date = due_date;
	}

	public boolean isStarred() {
		return starred;
	}

	public void setStarred(boolean starred) {
		this.starred = starred;
	}

	public int getRevision() {
		return revision;
	}

	public void setRevision(int revision) {
		this.revision = revision;
	}

	public boolean isCompleted() {
		return completed;
	}

	public void setCompleted(boolean completed) {
		this.completed = completed;
	}

	public String getCompleted_at() {
		return completed_at;
	}

	public void setCompleted_at(String completed_at) {
		this.completed_at = completed_at;
	}

	public int getCompleted_by_id() {
		return completed_by_id;
	}

	public void setCompleted_by_id(int completed_by_id) {
		this.completed_by_id = completed_by_id;
	}

	public String getRecurrence_type() {
		return recurrence_type;
	}

	public void setRecurrence_type(String recurrence_type) {
		this.recurrence_type = recurrence_type;
	}

	public int getRecurrence_count() {
		return recurrence_count;
	}

	public void setRecurrence_count(int recurrence_count) {
		this.recurrence_count = recurrence_count;
	}
}
