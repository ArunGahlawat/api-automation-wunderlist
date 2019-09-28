package com.nagarro.utils.enums;

public enum Config {
	COMMON("common"),
	CREDS("credentials"),
	TOKEN("token");

	String config;
	Config(String config) {
		this.config = config;
	}

	public String value() {
		return this.config;
	}
}
