package com.example.langchain;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LangchainApplication {

	public static void main(String[] args) {
		SpringApplication.run(LangchainApplication.class, args);
	}

	private void test() {
		
        AzureOpenAiChatModel modelA = AzureOpenAiChatModel.builder()
            .apiKey("")
            .endpoint("")
            .deploymentName("")
            .temperature(0.3)
            .logRequestsAndResponses(false)
            .build();

        for(int i=0; i < 10; i++){    

            int year = new Random().nextInt(100)+1924;

            String answerB = modelA.generate("Generate a a single line, random movie quote including movie title from the year "+year);
            System.out.println("Year: "+year+" Answer B: " + answerB);

        }
	}

}
