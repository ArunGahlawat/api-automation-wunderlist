package com.nagarro.utils.enums;

public enum RequestType {
	GET("GET"),
	POST("POST"),
	PUT("PUT"),
	PATCH("PATCH"),
	DELETE("DELETE");

	String requestType;

	RequestType(String requestType) {
		this.requestType = requestType;
	}
}
