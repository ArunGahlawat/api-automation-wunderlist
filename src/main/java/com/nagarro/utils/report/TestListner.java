package com.nagarro.utils.report;

import com.nagarro.utils.Log;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import com.aventstack.extentreports.Status;

public class TestListner implements ITestListener {
	public void onStart(ITestContext context) {
		Log.info("*** Test Suite " + context.getName() + " started ***");
	}

	public void onFinish(ITestContext context) {
		Log.info(("*** Test Suite " + context.getName() + " ending ***"));
		ExtentTestManager.endTest();
		ExtentManager.getInstance().flush();
	}

	public void onTestStart(ITestResult result) {
		ExtentTestManager.startTest(result.getMethod().getMethodName());
		Log.info(("*** Running test method " + result.getMethod().getMethodName() + "..."));
	}

	public void onTestSuccess(ITestResult result) {
		Log.info("*** Executed " + result.getMethod().getMethodName() + " test successfully...");
		ExtentTestManager.getTest().log(Status.PASS, "Test passed");
	}

	public void onTestFailure(ITestResult result) {
		Log.info("*** Test execution " + result.getMethod().getMethodName() + " failed...");
		ExtentTestManager.getTest().log(Status.FAIL, "Test Failed");
	}

	public void onTestSkipped(ITestResult result) {
		Log.info("*** Test " + result.getMethod().getMethodName() + " skipped...");
		ExtentTestManager.getTest().log(Status.SKIP, "Test Skipped");
	}

	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		Log.info("*** Test failed but within pass % " + result.getMethod().getMethodName());
		ExtentTestManager.getTest().log(Status.PASS,"Test failed but within pass %");
	}
}
