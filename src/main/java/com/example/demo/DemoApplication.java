package com.example.demo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.azure.ai.openai.OpenAIClient;
import com.azure.ai.openai.OpenAIClientBuilder;
import com.azure.ai.openai.models.Choice;
import com.azure.ai.openai.models.Completions;
import com.azure.ai.openai.models.CompletionsOptions;
import com.azure.ai.openai.models.CompletionsUsage;
import com.azure.core.credential.AzureKeyCredential;
import com.azure.core.util.Configuration;

import jakarta.annotation.PostConstruct;

@RestController
@SpringBootApplication
public class DemoApplication {

	OpenAIClient client;

	String azureOpenaiKey;
	String endpoint;
	String deploymentOrModelId;

	@Value("${AZURE_OPENAI_KEY:not_set}")
	String test;

	@PostConstruct
	public void postConstruct() {

		azureOpenaiKey = Configuration.getGlobalConfiguration().get("AZURE_OPENAI_KEY");
		endpoint = Configuration.getGlobalConfiguration().get("AZURE_OPENAI_ENDPOINT");
		deploymentOrModelId = Configuration.getGlobalConfiguration().get("AZURE_OPENAI_MODEL");

		System.out.println(azureOpenaiKey);
		System.out.println(endpoint);
		System.out.println(deploymentOrModelId);

		client = new OpenAIClientBuilder()
				.endpoint(endpoint)
				.credential(new AzureKeyCredential(azureOpenaiKey))
				.buildClient();

		System.out.println(test);

	}

	@GetMapping("/")
	public String helloWorld() {

		List<String> prompt = new ArrayList<>();
		prompt.add("Why did the eagles not carry Frodo Baggins to Mordor?");

		Completions completions = client.getCompletions(deploymentOrModelId, new CompletionsOptions(prompt));

		System.out.printf("Model ID=%s is created at %s.%n", completions.getId(), completions.getCreatedAt());
		for (Choice choice : completions.getChoices()) {
			System.out.printf("Index: %d, Text: %s.%n", choice.getIndex(), choice.getText());
		}

		CompletionsUsage usage = completions.getUsage();
		System.out.printf("Usage: number of prompt token is %d, "
				+ "number of completion token is %d, and number of total tokens in request and response is %d.%n",
				usage.getPromptTokens(), usage.getCompletionTokens(), usage.getTotalTokens());

		return "Hello Bosch World!";

	}

	void oldExperiments() {

		List<String> prompt = new ArrayList<>();
		prompt.add("Say this is a test");

		Completions completions = client.getCompletions("gpt-35-turbo", new CompletionsOptions(prompt));

		System.out.printf("Model ID=%s is created at %s.%n", completions.getId(), completions.getCreatedAt());
		for (Choice choice : completions.getChoices()) {
			System.out.printf("Index: %d, Text: %s.%n", choice.getIndex(), choice.getText());
		}

	}

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

}
