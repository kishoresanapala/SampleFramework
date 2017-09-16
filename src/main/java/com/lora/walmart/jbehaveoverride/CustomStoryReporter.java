package com.lora.walmart.jbehaveoverride;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.jbehave.core.model.ExamplesTable;
import org.jbehave.core.model.GivenStories;
import org.jbehave.core.model.Lifecycle;
import org.jbehave.core.model.Meta;
import org.jbehave.core.model.Narrative;
import org.jbehave.core.model.OutcomesTable;
import org.jbehave.core.model.Scenario;
import org.jbehave.core.model.Story;
import org.jbehave.core.model.StoryDuration;
import org.jbehave.core.reporters.StoryReporter;

import com.lora.walmart.utility.POJOCompenentsHolder;
import com.relevantcodes.extentreports.LogStatus;

public class CustomStoryReporter implements StoryReporter{
	public StoryReporter delegate;
	public String storyName;
	public String scName;
	public String stepName;
	public String stepStartTime;
	public String stepEndTime;
	public String failedStep;
	public Story story;
	public Boolean continueDefectCreation;
	public static int stepCounter = 1;
	public ArrayList<String> scenarioStatus = new ArrayList<String>();
	public ArrayList<String> failedScreenShots = new ArrayList<String>();
	POJOCompenentsHolder pojo;
	public CustomStoryReporter(StoryReporter delegate,POJOCompenentsHolder pojo) {
		this.delegate = delegate;
		this.pojo=pojo;
	}

	public void beforeStory(Story story, boolean givenStory) {
		delegate.beforeStory(story, givenStory);
		if (!givenStory) {
			storyName = story.getName();
		}
	}

	
	public void beforeScenario(String scenarioTitle) {
		delegate.beforeScenario(scenarioTitle);
		System.out.println(" ");
		System.out.println(scenarioTitle);
		pojo.getCurrentTest().log(LogStatus.INFO, "<span style='color:#CB5F00;font-weight:bold;'>Scenario:</span><span style='color:#CB5F00;font-style: italic;'>" + scenarioTitle + "</span>");
	}

	
	public void beforeStep(String step) {
		stepName = step;
		delegate.beforeStep(step);
	}

	
	public void storyNotAllowed(Story story, String filter) {
		// TODO Auto-generated method stub

	}

	
	public void storyCancelled(Story story, StoryDuration storyDuration) {
		// TODO Auto-generated method stub

	}

	
	public void afterStory(boolean givenStory) {

	}

	
	public void narrative(Narrative narrative) {
		// TODO Auto-generated method stub

	}

	
	public void lifecyle(Lifecycle lifecycle) {
		// TODO Auto-generated method stub

	}

	
	public void scenarioNotAllowed(Scenario scenario, String filter) {
		// TODO Auto-generated method stub

	}

	
	public void scenarioMeta(Meta meta) {
		// TODO Auto-generated method stub

	}

	
	public void afterScenario() {

	}

	
	public void givenStories(GivenStories givenStories) {
		// TODO Auto-generated method stub

	}

	
	public void givenStories(List<String> storyPaths) {
		// TODO Auto-generated method stub

	}

	
	public void beforeExamples(List<String> steps, ExamplesTable table) {
		// TODO Auto-generated method stub

	}

	
	public void example(Map<String, String> tableRow) {
		// TODO Auto-generated method stub

	}

	
	public void afterExamples() {
		// TODO Auto-generated method stub

	}

	
	public void successful(String step) {
		String[] stepValue = step.split(" ");
		String stepVal = step.replace(stepValue[0], "");
		stepName = stepVal;
		pojo.getCurrentTest().log(LogStatus.PASS, "<span style='color:#512510;font-weight:bold'>" + stepValue[0] + "</span><span style='color:#512510;'>" + stepVal + "</span>");
		scenarioStatus.add("PASS");
		System.out.println(stepVal.replaceAll("<b>'", "'").replaceAll("'</b>", "'").trim() + " [Passed]");
	}

	
	public void ignorable(String step) {

	}

	
	public void pending(String step) {
		String[] stepValue = step.split(" ");
		stepName = step;
		pojo.getCurrentTest().log(LogStatus.SKIP, "<span style='color:#512510;font-weight:bold'>" + stepValue[0] + "</span><span style='color:#512510;'>" + step.replace(stepValue[0], "") + "</span>");
		scenarioStatus.add("PENDING");
		System.out.println(step + " [Pending]");
	}

	
	public void notPerformed(String step) {
		String[] stepValue = step.split(" ");
		stepName = step;
		pojo.getCurrentTest().log(LogStatus.SKIP, "<span style='color:#512510;font-weight:bold'>" + stepValue[0] + "</span><span style='color:#512510;'>" + step.replace(stepValue[0], "") + "</span>");

		scenarioStatus.add("NOT PERFORMED");
		System.out.println(step + " [Not Performed]");
	}

	
	public void failed(String step, Throwable cause) {
		scenarioStatus.add("FAILED");
		System.out.println(step + " [Failed]");
	}

	
	public void failedOutcomes(String step, OutcomesTable table) {
		// TODO Auto-generated method stub
	}

	
	public void restarted(String step, Throwable cause) {
		// TODO Auto-generated method stub
	}

	
	public void dryRun() {
		// TODO Auto-generated method stub

	}

	
	public void pendingMethods(List<String> methods) {
		// TODO Auto-generated method stub

	}

	
	public void restartedStory(Story arg0, Throwable arg1) {
		// TODO Auto-generated method stub

	}

	public Boolean isIgnorableStep(String step) {
		boolean returnValue = false;
		try{
			stepName = step;
			ArrayList<String> ignoredTestSteps = new ArrayList<String>();
			String stepDummy = step;
			ignoredTestSteps.add("COMMON: ADPR URL from properties file");
			ignoredTestSteps.add("COMMON: I logged into application with given credentials from properties file");
			ignoredTestSteps.add("COMMON: I Navigate to dashboard");
			ignoredTestSteps.add("SIMPLIFIED: Analytics Home Page is displayed");
			ignoredTestSteps.add("SIMPLIFIED: I see the Reports Flyout");
			ignoredTestSteps.add("SIMPLIFIED: the Report Canvas is displayed");
			ignoredTestSteps.add("SIMPLIFIED: the View Reports is displayed");
			ignoredTestSteps.add("SIMPLIFIED: I click Save + Run button");

			stepDummy = stepDummy.split(" ", 2)[1];
			if (ignoredTestSteps.contains(stepDummy))
				returnValue = true;
			else
				returnValue = false;
		}catch(Exception e){

		}
		return returnValue;
	}

	public Boolean isP1Step(String step){
		boolean returnValue = false;
		try{
			stepName = step;
			String stepDummy = step;
			ArrayList<String> p1Steps = new ArrayList<String>();
			p1Steps.add("COMMON: ADPR URL from properties file");
			p1Steps.add("COMMON: I logged into application with given credentials from properties file");
			p1Steps.add("COMMON: I Navigate to dashboard");
			p1Steps.add("SIMPLIFIED: Analytics Home Page is displayed");
			p1Steps.add("SIMPLIFIED: I see the Reports Flyout");
			p1Steps.add("SIMPLIFIED: the Report Canvas is displayed");
			p1Steps.add("SIMPLIFIED: the View Reports is displayed");
			p1Steps.add("SIMPLIFIED: I click Save + Run button");
			p1Steps.add("SIMPLIFIED: I search and select by field ｟Field1｠ in Canvas");
			p1Steps.add("SIMPLIFIED: I search and select by field First Name in Canvas");

			stepDummy = stepDummy.split(" ", 2)[1];
			if (p1Steps.contains(stepDummy))
				returnValue = true;
			else
				returnValue = false;
		}catch(Exception e){

		}
	return returnValue;
	}

	
	public void comment(String arg0) {
		// TODO Auto-generated method stub

	}
}