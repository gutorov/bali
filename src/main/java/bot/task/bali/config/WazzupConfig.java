package bot.task.bali.config;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WazzupConfig {

    @Value("${wazzap.bot.api.key}")
    private String apiKey;

    @Value("${shared.webhook.bot.uri}")
    private String webhookUri;

    @Value("${wazzap.bot.api.url}")
    private String wazzapApiUrl;

    @PostConstruct
    public void init() {

        var webClient = WebClient.builder()
                .baseUrl(wazzapApiUrl)
                .defaultHeader("Authorization", "Bearer " + apiKey)
                .build();

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("webhooksUri", webhookUri + "/callback/wazzap");

        String jsonBody = jsonObject.toString();

        webClient.patch()
                .uri("/v3/webhooks")
                .bodyValue(jsonBody)
                .retrieve()
                .bodyToMono(String.class)
                .onErrorResume(error -> {
                    throw new RuntimeException("Failed to fetch url: " + error);
                })
                .block();
    }
}
