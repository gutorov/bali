package bot.task.bali.service.http;

import org.springframework.web.reactive.function.client.WebClient;

public interface SignatureHeaders {
    void addSignatureHeader(WebClient webClient);
}
