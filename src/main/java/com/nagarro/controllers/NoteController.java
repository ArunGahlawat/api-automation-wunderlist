package com.nagarro.controllers;

import com.nagarro.requests.NoteRequest;
import com.nagarro.requests.SubTaskRequest;
import com.nagarro.utils.Api;
import com.nagarro.utils.Common;
import com.nagarro.utils.JsonHandler;
import com.nagarro.utils.enums.Endpoint;
import com.nagarro.utils.enums.RequestType;
import io.restassured.response.Response;
import org.apache.commons.lang3.StringUtils;

public class NoteController {
	public Response getTaskNotes(int parent_id, String parentType) {
		Api api = new Api();
		api.apiUrl = Common.generateURL(Endpoint.NOTES);
		NoteRequest noteRequest = new NoteRequest();
		if (StringUtils.equalsIgnoreCase(parentType,"LIST"))
			noteRequest.setList_id(parent_id);
		else
			noteRequest.setTask_id(parent_id);
		api.jsonBody = JsonHandler.toJson(noteRequest);
		api.sendNetworkRequest(RequestType.GET);
		return api.jsonResponse;
	}

	public Response fetch(int id) {
		Api api = new Api();
		api.apiUrl = Common.generateURL(Endpoint.NOTES);
		api.pathParams.put("id", String.valueOf(id));
		api.sendNetworkRequest(RequestType.GET);
		return api.jsonResponse;
	}

	public Response create(int task_id, String content) {
		Api api = new Api();
		api.apiUrl = Common.generateURL(Endpoint.NOTES);
		NoteRequest noteRequest = new NoteRequest();
		noteRequest.setTask_id(task_id);
		noteRequest.setContent(content);
		api.jsonBody = JsonHandler.toJson(noteRequest);
		api.sendNetworkRequest(RequestType.POST);
		return api.jsonResponse;
	}

	public Response update(int id, int revision, String content){
		Api api = new Api();
		api.apiUrl = Common.generateURL(Endpoint.NOTES);
		NoteRequest noteRequest = new NoteRequest();
		noteRequest.setRevision(revision);
		noteRequest.setContent(content);
		api.jsonBody = JsonHandler.toJson(noteRequest);
		api.pathParams.put("id",String.valueOf(id));
		api.sendNetworkRequest(RequestType.PATCH);
		return api.jsonResponse;
	}

	public Response delete(int id, int revision) {
		Api api = new Api();
		api.apiUrl = Common.generateURL(Endpoint.NOTES);
		NoteRequest noteRequest = new NoteRequest();
		noteRequest.setRevision(revision);
		api.jsonBody = JsonHandler.toJson(noteRequest);
		api.pathParams.put("id",String.valueOf(id));
		api.sendNetworkRequest(RequestType.DELETE);
		return api.jsonResponse;
	}
}
