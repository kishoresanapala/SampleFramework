package com.lora.walmart.jbehaveoverride;

import org.jbehave.core.reporters.StoryReporter;
import org.jbehave.core.reporters.StoryReporterBuilder;

import com.lora.walmart.utility.POJOCompenentsHolder;

public class CustomStoryReporterBuilder extends StoryReporterBuilder {
	POJOCompenentsHolder pojo;
	public CustomStoryReporterBuilder(POJOCompenentsHolder pojo) {
		this.pojo=pojo;
	}

	@Override
	public StoryReporter build(String storyPath) {
	    StoryReporter delegate = super.build(storyPath);
	    return new CustomStoryReporter(delegate,pojo);
	}

}
