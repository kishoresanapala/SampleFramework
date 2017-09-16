package com.lora.walmart.jbehaveoverride;

import static java.util.Arrays.asList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.jbehave.core.ConfigurableEmbedder;
import org.jbehave.core.embedder.Embedder;
import org.jbehave.core.embedder.StoryControls;
import org.jbehave.core.io.StoryPathResolver;
import org.junit.Test;

import com.lora.walmart.testexecutor.FrameworkDriver;
import com.lora.walmart.utility.POJOCompenentsHolder;

public class JUnitStoryCustom extends ConfigurableEmbedder {

	public Embedder embedder;

	@Test
	public void run(POJOCompenentsHolder pojo) throws Throwable {
		embedder = configuredEmbedder();
		embedder.embedderControls().useStoryTimeouts("2700");
		embedder.embedderControls().useThreads(20);
		embedder.embedderControls().doFailOnStoryTimeout(true);
		embedder.configuration().useStoryReporterBuilder(new CustomStoryReporterBuilder(pojo)).useStoryControls(new StoryControls()
			     .doResetStateBeforeScenario(true).doResetStateBeforeStory(true));
		try {
			String storyPath = getStoryPath();
			if (storyPath == null) {
				StoryPathResolver pathResolver = embedder.configuration().storyPathResolver();
				storyPath = pathResolver.resolve(this.getClass());
			}
			embedder.runStoriesAsPaths(asList(storyPath));
		}catch(Exception e){
			System.out.println("Failure observed: "+e);
		}
	}


	protected String getStoryPath() {
		return null;
	}

	protected List<String> metaFilters() {
		return null;
	}

	public void run() throws Throwable {
		// TODO Auto-generated method stub
		
	}
}
