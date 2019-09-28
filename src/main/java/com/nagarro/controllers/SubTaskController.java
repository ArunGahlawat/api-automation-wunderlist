package com.nagarro.controllers;

import com.nagarro.requests.SubTaskRequest;
import com.nagarro.utils.Api;
import com.nagarro.utils.Common;
import com.nagarro.utils.JsonHandler;
import com.nagarro.utils.enums.Endpoint;
import com.nagarro.utils.enums.RequestType;
import io.restassured.response.Response;
import org.apache.commons.lang3.StringUtils;

public class SubTaskController {
	public Response getSubTasks(int parent_id, String parentType) {
		Api api = new Api();
		api.apiUrl = Common.generateURL(Endpoint.SUBTASKS);
		SubTaskRequest subTaskRequest = new SubTaskRequest();
		if (StringUtils.equalsIgnoreCase(parentType,"LIST"))
			subTaskRequest.setList_id(parent_id);
		else
			subTaskRequest.setTask_id(parent_id);
		api.jsonBody = JsonHandler.toJson(subTaskRequest);
		api.sendNetworkRequest(RequestType.GET);
		return api.jsonResponse;
	}

	public Response getCompletedSubTasks(int parent_id, String parentType, boolean completed) {
		Api api = new Api();
		api.apiUrl = Common.generateURL(Endpoint.SUBTASKS);

		SubTaskRequest subTaskRequest = new SubTaskRequest();
		if (StringUtils.equalsIgnoreCase(parentType,"LIST"))
			subTaskRequest.setList_id(parent_id);
		else
			subTaskRequest.setTask_id(parent_id);
		subTaskRequest.setCompleted(completed);
		api.jsonBody = JsonHandler.toJson(subTaskRequest);
		api.sendNetworkRequest(RequestType.GET);
		return api.jsonResponse;
	}

	public Response fetch(int id) {
		Api api = new Api();
		api.apiUrl = Common.generateURL(Endpoint.SUBTASKS);
		api.pathParams.put("id", String.valueOf(id));
		api.sendNetworkRequest(RequestType.GET);
		return api.jsonResponse;
	}

	public Response create(int task_id, String title, boolean completed) {
		Api api = new Api();
		api.apiUrl = Common.generateURL(Endpoint.SUBTASKS);
		SubTaskRequest subTaskRequest = new SubTaskRequest();
		subTaskRequest.setTask_id(task_id);
		subTaskRequest.setTitle(title);
		subTaskRequest.setCompleted(completed);
		api.jsonBody = JsonHandler.toJson(subTaskRequest);
		api.sendNetworkRequest(RequestType.POST);
		return api.jsonResponse;
	}

	public Response update(int id, int revision, String title, boolean completed){
		Api api = new Api();
		api.apiUrl = Common.generateURL(Endpoint.SUBTASKS);
		SubTaskRequest subTaskRequest = new SubTaskRequest();
		subTaskRequest.setRevision(revision);
		subTaskRequest.setTitle(title);
		subTaskRequest.setCompleted(completed);
		api.jsonBody = JsonHandler.toJson(subTaskRequest);
		api.pathParams.put("id",String.valueOf(id));
		api.sendNetworkRequest(RequestType.PATCH);
		return api.jsonResponse;
	}

	public Response delete(int id, int revision) {
		Api api = new Api();
		api.apiUrl = Common.generateURL(Endpoint.SUBTASKS);
		api.queryParams.put("revision",String.valueOf(revision));
		api.pathParams.put("id",String.valueOf(id));
		api.sendNetworkRequest(RequestType.DELETE);
		return api.jsonResponse;
	}
}
