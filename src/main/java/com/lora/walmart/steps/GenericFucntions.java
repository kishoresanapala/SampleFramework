package com.lora.walmart.steps;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.jbehave.core.annotations.Given;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.lora.walmart.testexecutor.FrameworkDriver;
import com.lora.walmart.utility.BrowserInitiator;
import com.lora.walmart.utility.POJOCompenentsHolder;
import com.lora.walmart.utility.PropertiesFile;
import com.relevantcodes.extentreports.LogStatus;

public class GenericFucntions {
	public BrowserInitiator context;
	private static String Url = PropertiesFile.GetEnvironmentProperty("Url");
	private static final String screenshotForPassed = PropertiesFile.GetEnvironmentProperty("ScreenshotForPassed");
	private static final String screenshotForFailed = PropertiesFile.GetEnvironmentProperty("ScreenshotForFailed");
	public static final String applicationSyncTime = PropertiesFile.GetEnvironmentProperty("ApplicationSyncTime");
	protected POJOCompenentsHolder pojoCompenentsHolder;

	public GenericFucntions(BrowserInitiator context, POJOCompenentsHolder pojoCompenentsHolder) {
		this.context = context;
		this.pojoCompenentsHolder = pojoCompenentsHolder;
	}

	@Given("I open URL from properties file")
	public void openApplication() throws Exception {
		try {
			context.driver.get(Url);
			System.out.println(context.driver.manage().window().getSize());
			addReport(LogStatus.PASS, "Application opened successfully");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public WebElement FindElement(BrowserInitiator context, By by) {
		return context.driver.findElement(by);
	}

	public void addReport(LogStatus stepStatus, String reportDescription) {
		System.out.println(reportDescription);
		if (stepStatus.name().equals("PASS") && screenshotForPassed.contains("TRUE")) {

			pojoCompenentsHolder.getCurrentTest().log(LogStatus.INFO, "[INFO] " + reportDescription + "</span>" + captureScreenShot(context));
		} else if (stepStatus.name().equals("PASS") && screenshotForPassed.contains("FALSE"))
			pojoCompenentsHolder.getCurrentTest().log(LogStatus.INFO, "[INFO] " + reportDescription + "</span>");
		else if (stepStatus.name().equals("FAIL") && screenshotForFailed.contains("TRUE")) {
			pojoCompenentsHolder.getCurrentTest().log(LogStatus.INFO, "[INFO] " + reportDescription + "</span>" + captureScreenShot(context));
		} else if (stepStatus.name().equals("FAIL") && screenshotForFailed.contains("FALSE")) {
			pojoCompenentsHolder.getCurrentTest().log(LogStatus.INFO, "[INFO] " + reportDescription + "</span>");
		} else if (stepStatus.name().equals("FATAL")) {
			System.out.println("Test Execution killed");
			context.closeDriver();
		} else if (stepStatus.name().equals("INFO")) {
			pojoCompenentsHolder.getCurrentTest().log(LogStatus.INFO, "[INFO] " + reportDescription + "</span>");
		} else if (stepStatus.name().equals("WARNING") && screenshotForFailed.contains("TRUE")) {
			pojoCompenentsHolder.getCurrentTest().log(LogStatus.WARNING, "[WARNING] " + reportDescription + "</span>" + captureScreenShot(context));
		} else if (stepStatus.name().equals("WARNING") && screenshotForFailed.contains("FALSE")) {
			pojoCompenentsHolder.getCurrentTest().log(LogStatus.WARNING, "[WARNING] " + reportDescription + "</span>");
		}
	}

	public String captureScreenShot(BrowserInitiator context) {
		String fileInfo = null;
		String screenShotPath;
		try {
			screenShotPath = System.getProperty("user.dir") + "\\test-output\\ExtentHTML\\" + FrameworkDriver.getModuleName + "\\Screenshots\\" + FrameworkDriver.Environment + "_" + getCurrentTimeWithMilliSeconds() + ".png";
			fileInfo = pojoCompenentsHolder.getCurrentTest().addScreenCapture(screenShotPath);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return fileInfo;
	}

	public static String getCurrentTime() {
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		Date date = new Date();
		return dateFormat.format(date);
	}

	public static String getCurrentTimeWithMilliSeconds() {
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		Date date = new Date();
		return dateFormat.format(date);
	}
}
