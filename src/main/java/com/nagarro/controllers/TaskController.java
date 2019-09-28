package com.nagarro.controllers;

import com.nagarro.requests.TaskRequest;
import com.nagarro.utils.Api;
import com.nagarro.utils.Common;
import com.nagarro.utils.JsonHandler;
import com.nagarro.utils.enums.Endpoint;
import com.nagarro.utils.enums.RequestType;
import io.restassured.response.Response;

public class TaskController {
	public Response getListTasks(int list_id) {
		Api api = new Api();
		api.apiUrl = Common.generateURL(Endpoint.TASKS);
		TaskRequest taskRequest = new TaskRequest();
		taskRequest.setList_id(list_id);
		api.jsonBody = JsonHandler.toJson(taskRequest);
		api.sendNetworkRequest(RequestType.GET);
		return api.jsonResponse;
	}

	public Response getCompletedTasks(int list_id, boolean completed) {
		Api api = new Api();
		api.apiUrl = Common.generateURL(Endpoint.TASKS);
		TaskRequest taskRequest = new TaskRequest();
		taskRequest.setList_id(list_id);
		taskRequest.setCompleted(completed);
		api.jsonBody = JsonHandler.toJson(taskRequest);
		api.sendNetworkRequest(RequestType.GET);
		return api.jsonResponse;
	}

	public Response fetch(int id) {
		Api api = new Api();
		api.apiUrl = Common.generateURL(Endpoint.TASKS);
		api.pathParams.put("id", String.valueOf(id));
		api.sendNetworkRequest(RequestType.GET);
		return api.jsonResponse;
	}

	public Response create(TaskRequest taskRequest) {
		Api api = new Api();
		api.apiUrl = Common.generateURL(Endpoint.TASKS);
		api.jsonBody = JsonHandler.toJson(taskRequest);
		api.sendNetworkRequest(RequestType.POST);
		return api.jsonResponse;
	}

	public Response update(int id, TaskRequest taskRequest){
		Api api = new Api();
		api.apiUrl = Common.generateURL(Endpoint.TASKS);
		api.jsonBody = JsonHandler.toJson(taskRequest);
		api.pathParams.put("id",String.valueOf(id));
		api.sendNetworkRequest(RequestType.PATCH);
		return api.jsonResponse;
	}

	public Response delete(int id, int revision) {
		Api api = new Api();
		api.apiUrl = Common.generateURL(Endpoint.TASKS);
		TaskRequest taskRequest = new TaskRequest();
		taskRequest.setRevision(revision);
		api.jsonBody = JsonHandler.toJson(taskRequest);
		api.pathParams.put("id",String.valueOf(id));
		api.sendNetworkRequest(RequestType.DELETE);
		return api.jsonResponse;
	}
}
