package com.lora.walmart.testexecutor;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.jbehave.core.configuration.Configuration;
import org.jbehave.core.configuration.MostUsefulConfiguration;
import org.jbehave.core.io.LoadFromClasspath;
import org.jbehave.core.reporters.StoryReporterBuilder;
import org.jbehave.core.steps.CandidateSteps;
import org.jbehave.core.steps.InstanceStepsFactory;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.lora.walmart.jbehaveoverride.JUnitStoryCustom;
import com.lora.walmart.utility.BrowserInitiator;
import com.lora.walmart.utility.POJOCompenentsHolder;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.relevantcodes.extentreports.NetworkMode;

public class FrameworkDriver extends JUnitStoryCustom {
	private String story;
	public static String getModuleName;
	public static String givenStoryName;
	public BrowserInitiator context;
	public static BrowserInitiator context1;
	public POJOCompenentsHolder pojoCompenentsHolder;
	public static String Environment;
	public static int invokeBrowserCounter = 1;
	public static ExtentReports extent;
	public static String reportGenerationTime;
	public static String isParallel;
	public static HashMap<ExtentTest, WebDriver> extentTests = new HashMap<ExtentTest, WebDriver>();
	public static String suiteName;
	private String TASKLIST = "tasklist.exe";
	private String KILL = "taskkill /F /IM ";

	@Test
	@Parameters(value = { "storyName" })
	public void runStory(String storyName) throws Throwable {
		this.story = storyName;
		try {
			super.run(pojoCompenentsHolder);
		} catch (Exception e) {
			pojoCompenentsHolder.getCurrentTest().log(LogStatus.WARNING, "Execution stopped and exception is: " + e.getMessage());
			e.printStackTrace();
		}
	}

	@Override
	public Configuration configuration() {
		return new MostUsefulConfiguration().useStoryLoader(new LoadFromClasspath(this.getClass())).useStoryReporterBuilder(new StoryReporterBuilder().withDefaultFormats().withFormats(org.jbehave.core.reporters.Format.CONSOLE, org.jbehave.core.reporters.Format.STATS));
	}

	@Override
	public List<CandidateSteps> candidateSteps() {
		try {
			List<Class<?>> listOfAllSetps = new LinkedList<Class<?>>();
			listOfAllSetps.addAll(GetStepClasses.find("com.lora.walmart.steps"));

			List<Object> listOfSteps = new LinkedList<Object>();
			for (Class<?> clazz : listOfAllSetps) {
				try {
					listOfSteps.add(clazz.getConstructor(BrowserInitiator.class, POJOCompenentsHolder.class).newInstance(new Object[] { context, pojoCompenentsHolder }));
				} catch (Exception e) {
					// Object t = clazz.newInstance();
					Method[] declaredMethods = clazz.getDeclaredMethods();
					String className = clazz.getName();
					for (Method method : declaredMethods) {
						Class<?> methodName = method.getDeclaringClass();
						if (className.equals(methodName)) {
							listOfSteps.add(method.invoke(clazz.newInstance(), new Object[] { context }));
						}
					}
				}
			}

			return new InstanceStepsFactory(configuration(), listOfSteps).createCandidateSteps();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			System.err.println("An InvocationTargetException was caught!");
			Throwable cause = e.getCause();
			System.out.format("Invocation of %s failed because of: %s%n", "methodname", cause.getMessage());

			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	protected String getStoryPath() {
		String codeLocation = "storyFiles/";
		return codeLocation + this.story;
	}

	@BeforeSuite
	public void beforeSuite(ITestContext Istx) throws Exception {
		System.out.println("before suite");
		suiteName = Istx.getSuite().getName();
		isParallel = Istx.getSuite().getParallel();
		reportGenerationTime = getCurrentTimeWithMilliSeconds();
		String extentFileName = System.getProperty("user.dir") + "\\test-output\\MyResults\\" + suiteName + "_" + reportGenerationTime + "_ExecutionReport.html";
		extent = new ExtentReports(extentFileName, false, NetworkMode.ONLINE);
	}

	@Parameters({ "storyName", "RunAgainst", "platform", "browser", "version" })
	@BeforeTest(alwaysRun = true)
	public void beforeTest(ITestContext ctx, String storyName, String RunAgainst, String platform, String browser, String version) throws Exception {
		System.out.println("before test");
		pojoCompenentsHolder = new POJOCompenentsHolder();
		pojoCompenentsHolder.setTestName(ctx.getCurrentXmlTest().getName());

		Environment = RunAgainst;

		if (isParallel.equalsIgnoreCase("tests")) {
			invokeParallelBrowser(RunAgainst, platform, browser, version);
		} else {
			if (invokeBrowserCounter == 1) {
				context1 = new BrowserInitiator(pojoCompenentsHolder.getTestName(), RunAgainst, browser, version);
				context1.initiateDriver(RunAgainst, platform, browser, version);
			}
			context = context1;
			pojoCompenentsHolder.setCurrentTest(extent.startTest(pojoCompenentsHolder.getTestName(), ""));
			invokeBrowserCounter = invokeBrowserCounter + 1;
		}

	}

	@AfterTest
	public void afterTest() {
		extent.endTest(pojoCompenentsHolder.getCurrentTest());
		try {
			this.context.driver.close();
		} catch (Exception e) {

		}
		System.out.println("after test");
	}

	@AfterSuite
	public void afterSuite() {
		try {
			extent.flush();
			System.gc();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean isProcessRunning(String serviceName) throws Exception {
		Runtime.getRuntime().exec("RunDll32.exe InetCpl.cpl,ClearMyTracksByProcess 255");
		Process p = Runtime.getRuntime().exec(TASKLIST);
		BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
		String line;
		while ((line = reader.readLine()) != null) {
			if (line.contains(serviceName))
				return true;
		}
		return false;
	}

	public void killProcess(String serviceName) throws Exception {
		Runtime.getRuntime().exec(KILL + serviceName);
		System.out.println("Killed Process:" + serviceName);
	}

	public void invokeParallelBrowser(String RunAgainst, String platform, String browser, String version) {
		context = new BrowserInitiator(pojoCompenentsHolder.getTestName(), RunAgainst, browser, version);
		try {
			context.initiateDriver(RunAgainst, platform, browser, version);
		} catch (Exception e) {
			System.out.println("Error in opening browser");
		}

		pojoCompenentsHolder.setCurrentTest(extent.startTest(pojoCompenentsHolder.getTestName(), ""));
		extentTests.put(pojoCompenentsHolder.getCurrentTest(), this.context.driver);
		System.out.println("Current Extent Tests: " + extentTests + "  Test Name::: " + pojoCompenentsHolder.getTestName());
	}

	public static String getCurrentTimeWithMilliSeconds() {
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		Date date = new Date();
		return dateFormat.format(date);
	}
}