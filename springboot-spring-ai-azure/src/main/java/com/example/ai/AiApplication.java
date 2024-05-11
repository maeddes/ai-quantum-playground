package com.example.ai;

import org.springframework.ai.chat.ChatClient;
import org.springframework.ai.chat.ChatResponse;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

import org.springframework.ai.autoconfigure.azure.*;
import org.springframework.ai.azure.openai.AzureOpenAiChatClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.azure.ai.openai.OpenAIClientBuilder;

@SpringBootApplication
@RestController
public class AiApplication {

	@Autowired
	AzureOpenAiChatClient chatClient;

	@GetMapping("/")
	public String home() {

		String prompt = """
				
			Dear machine! Tell me sample movie quote including title and year, please!

		""";

		String response = chatClient.call(prompt);

		return response;
	}

	@GetMapping("/ai/generate")
    public Map generate(@RequestParam(value = "message", defaultValue = "Tell me a joke") String message) {
        return Map.of("generation", chatClient.call(message));
    }

	@GetMapping("/movie")
    public Map generate() {

		String prompt = """
				
			Dear machine! Tell me sample movie quote including title and year, please!

		""";

        return Map.of("Quote", chatClient.call(prompt));
    }

	public static void main(String[] args) {
		SpringApplication.run(AiApplication.class, args);
	}

}
