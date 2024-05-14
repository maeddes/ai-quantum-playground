# ai-quantum-playground

Notes:

`set -a`
`source .env`

```
import java.util.Map;

import org.springframework.ai.azure.openai.AzureOpenAiChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;
```

```
	@Autowired
	AzureOpenAiChatClient client;

	@GetMapping
	public Map home(){

		RestClient restClient = RestClient.create();

		String year = restClient.get().uri("http://localhost:5000/").retrieve().body(String.class);

		String prompt = """
				
				Dear machine, can you please give me a random movie quote with title from the year %s! Many thanks!


				""".formatted(year);

		return Map.of(prompt,client.call(prompt));
	}
```

