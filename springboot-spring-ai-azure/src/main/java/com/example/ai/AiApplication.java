package com.example.ai;

import java.util.Map;
import java.util.Random;

import org.springframework.ai.azure.openai.AzureOpenAiChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;

@SpringBootApplication
@RestController
public class AiApplication {

	@Autowired
	AzureOpenAiChatClient chatClient;

	@GetMapping("/")
	public String home() {

		int rand = new Random().nextInt(100);

		String prompt = """
				
			Dear machine! Tell me sample movie quote including title from the year %d , please!

		""".formatted(rand);

		String response = chatClient.call(prompt);

		return response;
	}

	@GetMapping("/ai/generate")
    public Map generate(@RequestParam(value = "message", defaultValue = "Tell me a joke") String message) {
        return Map.of("generation", chatClient.call(message));
    }

	@GetMapping("/client")
	public String client() {
		
		RestClient restClient = RestClient.create();

		String result = restClient.get().uri("http://localhost:5000/random").retrieve().body(String.class);

		return result;

	}

	@GetMapping("/movie")
    public Map generate() {

		//int rand = new Random().nextInt(100);

		RestClient restClient = RestClient.create();

		String rand = restClient.get().uri("http://localhost:5000/").retrieve().body(String.class);
		System.out.println(rand);

		String prompt = """
				
			Dear machine! Tell me sample movie quote including title from the year %d , please!

		""".formatted(Integer.parseInt(rand));

        return Map.of(prompt, chatClient.call(prompt));

    }

	public static void main(String[] args) {
		SpringApplication.run(AiApplication.class, args);
	}

}
