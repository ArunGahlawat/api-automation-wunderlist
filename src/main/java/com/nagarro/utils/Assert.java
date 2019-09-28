package com.nagarro.utils;

import java.math.BigDecimal;

public class Assert {
	public static void verifyNull(Object actual, String message) {
		try {
 			org.testng.Assert.assertNull(actual, message);
		} catch (AssertionError e) {
			Log.error("Assert", "verifyNull", e.getMessage());
			throw e;
		}
	}

	public static void verifyNotNull(Object actual, String message) {
		try {
			org.testng.Assert.assertNotNull(actual, message);
		} catch (AssertionError e) {
			Log.error("Assert", "verifyNotNull", e.getMessage());
			throw e;
		}
	}

	public static void verifyEquals(Double actual, Double expected, Double delta, String message) {
		try {
			org.testng.Assert.assertEquals(actual, expected, delta, message);
		} catch (AssertionError e) {
			Log.error("Assert", "verifyEquals", e.getMessage());
			throw e;
		}
	}

	public static void verifyNotEquals(Double actual, Double expected, Double delta, String message) {
		try {
			org.testng.Assert.assertNotEquals(actual, expected, delta, message);
		} catch (AssertionError e) {
			Log.error("Assert", "verifyNotEquals", e.getMessage());
			throw e;
		}
	}

	public static void verifyEquals(Object actual, Object expected) {
		verifyEquals(actual, expected, "Parameters did not match.");
	}

	public static void verifyEquals(Object actual, Object expected, String message) {
		try {
			org.testng.Assert.assertEquals(actual, expected, message);
		} catch (AssertionError e) {
			Log.error("Assert", "verifyEquals", e.getMessage());
			throw e;
		}
	}

	public static void verifyEquals(BigDecimal actual, BigDecimal expected, Double delta, String message) {
		verifyEquals(actual.doubleValue(), expected.doubleValue(), delta, message);
	}

	public static void verifyNotEquals(BigDecimal actual, BigDecimal expected, Double delta, String message) {
		verifyNotEquals(actual.doubleValue(), expected.doubleValue(), delta, message);
	}

	public static void fail() {
		org.testng.Assert.fail();
	}

	public static void fail(String message) {
		org.testng.Assert.fail(message);
	}
}
