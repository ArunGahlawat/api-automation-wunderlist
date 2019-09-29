package com.nagarro.utils;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.*;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.commons.lang3.StringUtils;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsonHandler {
	public static <T> T getDtoFromResponse(Response response, String tagName, T object) {
		return getDtoFromJsonString(getResponseAsJson(response, tagName).toString(), object);
	}

	public static JsonElement getResponseAsJson(Response response, String tagName) {
		if (StringUtils.isBlank(tagName))
			return getResponseAsJson(response);
		else
			return getResponseAsJson(response).get(tagName);
	}

	public static JsonObject getResponseAsJson(Response response) {
		try {
			response = response.then().contentType(ContentType.JSON).extract().response();
			JsonParser parser = new JsonParser();
			if (response.asString().charAt(0)=='[')
				return parser.parse(response.asString()).getAsJsonArray().get(0).getAsJsonObject();
			return parser.parse(response.asString()).getAsJsonObject();
		} catch (Exception e) {
			Assert.fail("Error parsing " + e.getLocalizedMessage());
		}
		return new JsonObject();
	}

	/*
	 * Will return object if mapping fails.
	 */
	public static <T> T getDtoFromJsonString(String jsonString, T object) {
		if (StringUtils.isEmpty(jsonString))
			return null;
		try {
			List<T> listDtoFromJsonString = getListDtoFromJsonString(jsonString, object);
			if (listDtoFromJsonString != null && listDtoFromJsonString.size() > 0)
				return listDtoFromJsonString.get(0);
		} catch (Exception e) {
			Assert.fail("Error Mapping DTO : " + e.getMessage());
		}
		return null;
	}

	public static <T> List<T> getListDtoFromJsonString(String jsonString, T object) {
		if (StringUtils.isEmpty(jsonString))
			return null;
		ObjectMapper objectMapper = new ObjectMapper();
		JavaType type = objectMapper.getTypeFactory().constructCollectionType(List.class, object.getClass());
		if (jsonString.charAt(0) != '[') {
			jsonString = "[" + jsonString + "]";
		}
		try {
			List<T> returnList = objectMapper.readValue(jsonString, type);
			returnList.removeAll(Collections.singleton(null));
			return returnList;
		} catch (Exception e) {
			Assert.fail("Error Mapping DTO : " + e.getMessage());
		}
		return null;
	}



	/*
	 * Will return an empty ArrayList<T> if mapping fails.
	 */
	public static <T> List<T> getListDtoFromResponse(Response response, String tagName, T object) {
		JsonElement responseAsJson = getResponseAsJson(response, tagName);
		List<T> listDtoFromJsonString = null;
		if (responseAsJson != null)
			listDtoFromJsonString = getListDtoFromJsonString(responseAsJson.toString(), object);
		return listDtoFromJsonString;
	}

	/*
	 * Will return an empty ArrayList<T> if mapping fails.
	 */


	/*
	 * Will return an empty HashMap<T, K> if mapping fails.
	 */
	public static <T, K> Map<T, K> getMapDtoFromResponse(Response response, String tagName, T keyObject, K valueObject) {
		return getMapDtoFromJsonString(getResponseAsJson(response, tagName).toString(), keyObject, valueObject);
	}

	/*
	 * Will return an empty HashMap<T, K> if mapping fails.
	 */
	public static <T, K> Map<T, K> getMapDtoFromJsonString(String jsonString, T keyObject, K valueObject) {
		if (StringUtils.isEmpty(jsonString))
			return null;
		ObjectMapper objectMapper = new ObjectMapper();
		JavaType type;
		type = objectMapper.getTypeFactory().constructMapType(HashMap.class, keyObject.getClass(), valueObject.getClass());
		try {
			return objectMapper.readValue(jsonString, type);
		} catch (Exception e) {
			Assert.fail("Error Mapping DTO : " + e.getMessage());
			return null;
		}
	}

	/**
	 * Generic method to get the value of a key from JSON Response data
	 *
	 * @param response: response object
	 * @param tagName:  name of the key
	 * @return value of the key
	 */
	public static String getJsonResponseValue(Response response, String tagName) {
		if (StringUtils.isEmpty(response.asString()))
			return "";
		JsonPath jsonPath = new JsonPath(response.andReturn().asString());
		return getJsonPathValue(jsonPath, tagName);
	}

	private static String getJsonPathValue(JsonPath jsonPath, String tagName) {
		try {
			if (jsonPath != null && jsonPath.getString(tagName) != null) {
				return jsonPath.getString(tagName);
			}
		} catch (Exception e) {
			Assert.fail("Error : " + e.getMessage());
		}
		return null;
	}

	public static JsonObject loadJsonFile(String fileName) {
		Log.debug("Loading JSON file : " + fileName);
		try {
			ClassLoader classloader = Thread.currentThread().getContextClassLoader();
			InputStream inputStream = classloader.getResourceAsStream(fileName);
			JsonParser jsonParser = new JsonParser();
			assert inputStream != null;
			return (JsonObject) jsonParser.parse(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
		} catch (Exception e) {
			Log.error("Common", "createCSVListFromFile", e.getMessage());
		}
		return null;
	}

	/**
	 * Generic method to get the size of the object in the response
	 *
	 * @param response:     response object
	 * @param responsePath: JSON path of the object whose size needs to be fetched
	 * @return size of the object
	 */
	public static int getJsonResponseSize(Response response, String responsePath) {
		return response.body().path(responsePath + ".size()");
	}

	public static String convertToUriFromJsonString(String gsonString) {
		gsonString = gsonString.replaceAll("\"", "");
		gsonString = gsonString.replaceAll("\\{", "");
		gsonString = gsonString.replaceAll("}", "");
		try {
			gsonString = URLEncoder.encode(gsonString, "UTF-8");
		} catch (Exception e) {
			Log.error("Common", "convertToUriFromJsonString", "Invalid encoding " + e.getMessage());
		}
		gsonString = gsonString.replaceAll("%2C", "&");
		gsonString = gsonString.replaceAll("%3A", "=");
		return gsonString;
	}

	public static String toJson(Object object) {
		return new Gson().toJson(object);
	}
}
