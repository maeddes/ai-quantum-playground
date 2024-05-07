package com.example.sample;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.azure.ai.openai.OpenAIClient;
import com.azure.ai.openai.OpenAIClientBuilder;
import com.azure.ai.openai.models.ChatChoice;
import com.azure.ai.openai.models.ChatCompletions;
import com.azure.ai.openai.models.ChatCompletionsOptions;
import com.azure.ai.openai.models.ChatRequestAssistantMessage;
import com.azure.ai.openai.models.ChatRequestMessage;
import com.azure.ai.openai.models.ChatRequestSystemMessage;
import com.azure.ai.openai.models.ChatRequestUserMessage;
import com.azure.ai.openai.models.ChatResponseMessage;
import com.azure.ai.openai.models.Choice;
import com.azure.ai.openai.models.Completions;
import com.azure.ai.openai.models.CompletionsOptions;
import com.azure.ai.openai.models.CompletionsUsage;
import com.azure.core.credential.AzureKeyCredential;
import com.azure.core.util.Configuration;

@SpringBootApplication
@RestController
public class SampleApplication {

	String azureOpenaiKey = Configuration.getGlobalConfiguration().get("AZURE_OPENAI_KEY");
	String endpoint = Configuration.getGlobalConfiguration().get("AZURE_OPENAI_ENDPOINT");
	String model = Configuration.getGlobalConfiguration().get("AZURE_OPENAI_MODEL");

	OpenAIClient client = new OpenAIClientBuilder()
		.credential(new AzureKeyCredential(azureOpenaiKey))
		.endpoint(endpoint)
		.buildClient();

	@GetMapping
	public String useOpenAPIClient(){

		String result = "probably failed";
		List<String> prompt = new ArrayList<String>();
		prompt.add("Can you give me a single line random movie quote, please?");

		Completions completions = client.getCompletions(model, new CompletionsOptions(prompt));

		System.out.printf("Model ID=%s is created at %s.%n", completions.getId(), completions.getCreatedAt());
		
		List<Choice> choices = completions.getChoices();
		System.out.println("Choices: "+choices.size()+" "+choices.toString());

		for (Choice choice : choices) {
			result = choice.getText();
			System.out.println("Text Raw: "+result);
			System.out.printf("Text formatted: %s.%n",choice.getText());
			System.out.printf("Index: %d, Text: %s.%n", choice.getIndex(), choice.getText());
		}

		return result;

	}

	private int randomNumber(){

		Random rand = new Random();
		int randomNum = rand.nextInt(55) + 45; // This will generate a random integer between 45 (inclusive) and 100 (exclusive)
		
		return randomNum;
	
	}

	@GetMapping("/alt")
	public String useOpenAPIClient2(){

        OpenAIClient client = new OpenAIClientBuilder()
            .endpoint(endpoint)
            .credential(new AzureKeyCredential(azureOpenaiKey))
            .buildClient();

        List<String> prompt = new ArrayList<>();
        prompt.add("When was Microsoft founded?");

        Completions completions = client.getCompletions(model, new CompletionsOptions(prompt));

		String result = "";

        System.out.printf("Model ID=%s is created at %s.%n", completions.getId(), completions.getCreatedAt());
        for (Choice choice : completions.getChoices()) {

			result = choice.getText();
            System.out.printf("Index: %d, Text: %s.%n", choice.getIndex(), choice.getText());
        }

        CompletionsUsage usage = completions.getUsage();
        System.out.printf("Usage: number of prompt token is %d, "
                + "number of completion token is %d, and number of total tokens in request and response is %d.%n",
            usage.getPromptTokens(), usage.getCompletionTokens(), usage.getTotalTokens());
		

		return result;
	}

	@GetMapping("/alt2")
	public String useOpenAPIClient3(){
	
        OpenAIClient client = new OpenAIClientBuilder()
            .endpoint(endpoint)
            .credential(new AzureKeyCredential(azureOpenaiKey))
            .buildClient();

        String prompt = "Print a random sentence with 5 words, please!";

        Completions completions = client.getCompletions(model, prompt);

        for (Choice choice : completions.getChoices()) {
            System.out.printf("%s.%n", choice.getText());
        }

		return "";
    }

	@GetMapping("/alt3")
	public String useChatClient(){

		List<ChatRequestMessage> chatMessages = new ArrayList<>();

		String chatQuery = "Can you give me a quote from a movie from 19"+this.randomNumber();
		//chatMessages.add(new ChatRequestSystemMessage("You are a movie database. You will return quotes."));
		chatMessages.add(new ChatRequestUserMessage(chatQuery));
		//chatMessages.add(new ChatRequestAssistantMessage("Of course, me hearty! What can I do for ye?"));
		//chatMessages.add(new ChatRequestUserMessage("What's the best way to train a parrot?"));

		ChatCompletions chatCompletions = client.getChatCompletions(model,
			new ChatCompletionsOptions(chatMessages));

		String result = "";

		System.out.printf("Model ID=%s is created at %s.%n", chatCompletions.getId(), chatCompletions.getCreatedAt());
		System.out.println(chatQuery);
		for (ChatChoice choice : chatCompletions.getChoices()) {
			ChatResponseMessage message = choice.getMessage();
			System.out.printf("Index: %d, Chat Role: %s.%n", choice.getIndex(), message.getRole());
			System.out.println("Message:");
			result = message.getContent();
			System.out.println(message.getContent());
		}

		return result;
	}

	public static void main(String[] args) {
		SpringApplication.run(SampleApplication.class, args);
	}

}
