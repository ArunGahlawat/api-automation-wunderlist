package com.nagarro.controllers;

import com.nagarro.requests.TaskRequest;
import com.nagarro.utils.Api;
import com.nagarro.utils.Common;
import com.nagarro.utils.JsonHandler;
import com.nagarro.utils.enums.Endpoint;
import com.nagarro.utils.enums.RequestType;
import io.restassured.response.Response;

public class TaskController {
	public Response getListTasks(Long list_id) {
		Api api = new Api();
		api.apiUrl = Common.generateURL(Endpoint.TASKS);
		api.queryParams.put("list_id", String.valueOf(list_id));
		api.sendNetworkRequest(RequestType.GET);
		return api.jsonResponse;
	}

	public Response getCompletedTasks(Long list_id, boolean completed) {
		Api api = new Api();
		api.apiUrl = Common.generateURL(Endpoint.TASKS);
		api.queryParams.put("completed",String.valueOf(completed));
		api.queryParams.put("list_id",String.valueOf(list_id));
		api.sendNetworkRequest(RequestType.GET);
		return api.jsonResponse;
	}

	public Response fetch(Long id) {
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

	public Response update(Long id, TaskRequest taskRequest){
		Api api = new Api();
		api.apiUrl = Common.generateURL(Endpoint.TASKS);
		api.jsonBody = JsonHandler.toJson(taskRequest);
		api.pathParams.put("id",String.valueOf(id));
		api.sendNetworkRequest(RequestType.PATCH);
		return api.jsonResponse;
	}

	public Response delete(Long id, Long revision) {
		Api api = new Api();
		api.apiUrl = Common.generateURL(Endpoint.TASKS);
		api.queryParams.put("revision",String.valueOf(revision));
		api.pathParams.put("id",String.valueOf(id));
		api.sendNetworkRequest(RequestType.DELETE);
		return api.jsonResponse;
	}
}
