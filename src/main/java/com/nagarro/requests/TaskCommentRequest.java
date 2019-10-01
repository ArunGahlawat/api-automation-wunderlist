package com.nagarro.requests;

import com.nagarro.requests.helper.Author;

public class TaskCommentRequest {
	private Long list_id;
	private Long task_id;
	private Long id;
	private Long revision;
	private String text;
	private String type;
	private String created_at;
	private String local_created_at;

	public String getLocal_created_at() {
		return local_created_at;
	}

	public void setLocal_created_at(String local_created_at) {
		this.local_created_at = local_created_at;
	}

	private Author author;
	private String title;

	public Long getList_id() {
		return list_id;
	}

	public void setList_id(Long list_id) {
		this.list_id = list_id;
	}

	public Long getTask_id() {
		return task_id;
	}

	public void setTask_id(Long task_id) {
		this.task_id = task_id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getRevision() {
		return revision;
	}

	public void setRevision(Long revision) {
		this.revision = revision;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCreated_at() {
		return created_at;
	}

	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}

	public Author getAuthor() {
		return author;
	}

	public void setAuthor(Author author) {
		this.author = author;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
}
