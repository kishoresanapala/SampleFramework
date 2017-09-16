package com.lora.walmart.utility;

import com.relevantcodes.extentreports.ExtentTest;

public class POJOCompenentsHolder {

	private String testName;
	private ExtentTest test;
	private String browser;
	private String appName;
	private String applicationLogins;
	private String applicationSharedLogins;
	private String environment;
	private ExtentTest currentTest;
	
	public String getTestName() {
		return testName;
	}
	public void setTestName(String testName) {
		this.testName = testName;
	}
	public ExtentTest getTest() {
		return test;
	}
	public void setTest(ExtentTest test) {
		this.test = test;
	}
	public String getBrowser() {
		return browser;
	}
	public void setBrowser(String browser) {
		this.browser = browser;
	}
	public String getAppName() {
		return appName;
	}
	public void setAppName(String appName) {
		this.appName = appName;
	}
	public String getApplicationLogins() {
		return applicationLogins;
	}
	public void setApplicationLogins(String applicationLogins) {
		this.applicationLogins = applicationLogins;
	}
	public String getApplicationSharedLogins() {
		return applicationSharedLogins;
	}
	public void setApplicationSharedLogins(String applicationSharedLogins) {
		this.applicationSharedLogins = applicationSharedLogins;
	}
	public String getEnvironment() {
		return environment;
	}
	public void setEnvironment(String environment) {
		this.environment = environment;
	}
	public ExtentTest getCurrentTest() {
		return currentTest;
	}
	public void setCurrentTest(ExtentTest currentTest) {
		this.currentTest = currentTest;
	}
	
}