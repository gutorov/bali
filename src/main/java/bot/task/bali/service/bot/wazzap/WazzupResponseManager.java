package bot.task.bali.service.bot.wazzap;

import bot.task.bali.dto.WazzupBody;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class WazzupResponseManager implements WazzupResponseService {

    private final WebClient webClient;
    private final Gson gson;

    public WazzupResponseManager(
            @Value("${wazzap.bot.api.key}") String apiKey,
            @Value("${wazzap.bot.api.url}") String wazzapApiUrl
    ) {
        this.webClient = WebClient.builder()
                .baseUrl(wazzapApiUrl)
                .defaultHeader("Authorization", "Bearer " + apiKey)
                .build();

        this.gson = new Gson();
    }

    @Override
    public void sendMessage(WazzupBody body) {
        String jsonBody = gson.toJson(body);
        webClient.post()
                .uri("/v3/message")
                .bodyValue(jsonBody)
                .retrieve()
                .bodyToMono(String.class)
                .onErrorResume(error -> {
                    throw new RuntimeException("Failed to send message thru Wazzup: " + error);
                })
                .block();
    }
}
