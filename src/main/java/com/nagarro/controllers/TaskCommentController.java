package com.nagarro.controllers;

import com.nagarro.requests.SubTaskRequest;
import com.nagarro.requests.TaskCommentRequest;
import com.nagarro.utils.Api;
import com.nagarro.utils.Common;
import com.nagarro.utils.JsonHandler;
import com.nagarro.utils.enums.Endpoint;
import com.nagarro.utils.enums.RequestType;
import io.restassured.response.Response;
import org.apache.commons.lang3.StringUtils;

public class TaskCommentController {
	public Response getAll(int parent_id, String parentType) {
		Api api = new Api();
		api.apiUrl = Common.generateURL(Endpoint.TASK_COMMENTS);
		TaskCommentRequest taskCommentRequest = new TaskCommentRequest();
		if (StringUtils.equalsIgnoreCase(parentType,"LIST"))
			taskCommentRequest.setList_id(parent_id);
		else
			taskCommentRequest.setTask_id(parent_id);
		api.jsonBody = JsonHandler.toJson(taskCommentRequest);
		api.sendNetworkRequest(RequestType.GET);
		return api.jsonResponse;
	}

	public Response fetch(int id) {
		Api api = new Api();
		api.apiUrl = Common.generateURL(Endpoint.TASK_COMMENTS);
		api.pathParams.put("id", String.valueOf(id));
		api.sendNetworkRequest(RequestType.GET);
		return api.jsonResponse;
	}

	public Response create(int task_id, String text) {
		Api api = new Api();
		api.apiUrl = Common.generateURL(Endpoint.TASK_COMMENTS);
		TaskCommentRequest taskCommentRequest = new TaskCommentRequest();
		taskCommentRequest.setTask_id(task_id);
		taskCommentRequest.setText(text);
		api.jsonBody = JsonHandler.toJson(taskCommentRequest);
		api.sendNetworkRequest(RequestType.POST);
		return api.jsonResponse;
	}
}
