package com.nagarro.controllers;

import com.nagarro.requests.ListRequest;
import com.nagarro.utils.Api;
import com.nagarro.utils.Common;
import com.nagarro.utils.JsonHandler;
import com.nagarro.utils.enums.Endpoint;
import com.nagarro.utils.enums.RequestType;
import io.restassured.response.Response;

public class ListController {
	public Response getAll() {
		Api api = new Api();
		api.apiUrl = Common.generateURL(Endpoint.LISTS);
		api.sendNetworkRequest(RequestType.GET);
		return api.jsonResponse;
	}

	public Response fetch(int id) {
		Api api = new Api();
		api.apiUrl = Common.generateURL(Endpoint.LISTS);
		api.pathParams.put("id",String.valueOf(id));
		api.sendNetworkRequest(RequestType.GET);
		return api.jsonResponse;
	}

	public Response create(String title) {
		Api api = new Api();
		api.apiUrl = Common.generateURL(Endpoint.LISTS);
		ListRequest listRequest = new ListRequest();
		listRequest.setTitle(title);
		api.jsonBody = JsonHandler.toJson(listRequest);
		api.sendNetworkRequest(RequestType.POST);
		return api.jsonResponse;
	}

	public Response update(int id, int revision, String title) {
		Api api = new Api();
		api.apiUrl = Common.generateURL(Endpoint.LISTS);
		ListRequest listRequest = new ListRequest();
		listRequest.setRevision(revision);
		listRequest.setTitle(title);
		api.pathParams.put("id", String.valueOf(id));
		api.jsonBody = JsonHandler.toJson(listRequest);
		api.sendNetworkRequest(RequestType.PATCH);
		return api.jsonResponse;
	}

	public Response delete(int id, int revision) {
		Api api = new Api();
		api.apiUrl = Common.generateURL(Endpoint.LISTS);
		ListRequest listRequest = new ListRequest();
		listRequest.setRevision(revision);
		api.pathParams.put("id", String.valueOf(id));
		api.jsonBody = JsonHandler.toJson(listRequest);
		api.sendNetworkRequest(RequestType.DELETE);
		return api.jsonResponse;
	}
}
