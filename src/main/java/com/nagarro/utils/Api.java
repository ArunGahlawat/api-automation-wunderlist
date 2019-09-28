package com.nagarro.utils;

import com.nagarro.utils.enums.Config;
import com.nagarro.utils.enums.RequestType;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.config.LogConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.io.PrintStream;
import java.util.*;

import static io.restassured.RestAssured.given;

public class Api {
	public String apiUrl;
	public Map<String, String> headers;
	public Map<String, String> queryParams;
	public Map<String, String> pathParams;
	public String jsonBody;
	public Response jsonResponse;
	private RequestSpecification given = Log.LOGGER.isDebugEnabled() ? given().log().all(true) : given();
	//LogConfig logConfig = restAssuredConfig.getLogConfig().defaultStream(printStream).enablePrettyPrinting(true);
	private static RequestSpecification specification;


	public Api() {
		headers = new HashMap<>();
		queryParams = new HashMap<>();
		pathParams = new LinkedHashMap<>();
		apiUrl = null;
		jsonBody = null;
		jsonResponse = null;
		new RequestLoggingFilter();
		new ResponseLoggingFilter();
		RestAssuredConfig restAssuredConfig = new RestAssuredConfig();
		PrintStream printStream = Log.getPrintStream();
		specification = new RequestSpecBuilder()
				.addFilter(RequestLoggingFilter.logRequestTo(printStream))
				.addFilter(ResponseLoggingFilter.logResponseTo(printStream))
				.setConfig(restAssuredConfig)
				.build();
	}

	public void sendNetworkRequest(RequestType requestType) {
		apiUrl = processPathParams(apiUrl, pathParams);
		headers = processHeaders(requestType, headers);
		given = given.spec(specification);
		try {
			given = headers == null ? given : given.headers(headers);
			given = queryParams == null ? given : given.queryParams(queryParams);
			given = pathParams == null ? given : given.pathParams(pathParams);
			given = jsonBody == null ? given : given.body(jsonBody);
			given = given.when();
			switch (requestType) {
				case GET:
					jsonResponse = given.get(apiUrl);
					break;
				case POST:
					jsonResponse = given.post(apiUrl);
					break;
				case PUT:
					jsonResponse = given.put(apiUrl);
					break;
				case PATCH:
					jsonResponse = given.patch(apiUrl);
					break;
				case DELETE:
					jsonResponse = given.delete(apiUrl);
					break;
				default:
					jsonResponse = null;
			}
			if (jsonResponse == null)
				Log.error("Api","sendNetworkRequest","Error sending network request");
		} catch (Exception e) {
			Log.error("Api","sendNetworkRequest",
					"Unable to send a " + requestType.name() + " request. " + e.getMessage());
		}
	}

	private Map<String, String> processHeaders(RequestType requestType, Map<String, String> headers) {
		if (headers != null) {
			if (headers.containsKey("headless"))
				headers = null;
			else if (!headers.containsKey("custom")){
				headers.putIfAbsent("X-Access-Token", Common.getAccessToken());
				headers.putIfAbsent("X-Client-ID", Common.getConfig(Config.CREDS).getString("CLIENT_ID"));
				headers.putIfAbsent("Content-Type", "application/json");
			}
		}
		return headers;
	}

	private String processPathParams(String apiUrl, Map<String, String> pathParams) {
		StringBuilder apiUrlBuilder = new StringBuilder(apiUrl);
		if (pathParams != null) {
			Set<String> paramKeys = pathParams.keySet();
			for (String paramKey : paramKeys) {
				if (!apiUrl.contains("{" + paramKey + "}"))
					apiUrlBuilder.append("/{").append(paramKey).append("}");
			}
		}
		return apiUrlBuilder.toString();
	}
}
