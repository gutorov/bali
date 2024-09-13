package bot.task.bali.service.amo.impl;

import bot.task.bali.entities.amo.AmoReqiredCustomField;
import bot.task.bali.entities.utils.AmoCustomFieldExistValues;
import bot.task.bali.service.amo.GetterAvailableVals;
import bot.task.bali.service.app.converter.ConverterToCustomField;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

//todo сделать шедулер, который обновляет значения
@Component
class AmoCustomFieldExistValuesManager implements GetterAvailableVals {

    private List<AmoCustomFieldExistValues> existValues;
    private final WebClient webClient;
    private final ConverterToCustomField converter;


    public AmoCustomFieldExistValuesManager(@Value("${amo.crm.api-url}") String url, @Value("${amo.crm.api-key}") String apiKey, ConverterToCustomField converter ) {
        this.webClient = WebClient.builder()
                .baseUrl(url + "/api/v4/")
                .defaultHeader("Authorization", "Bearer " + apiKey)
                .build();
        this.converter = converter;
    }


    @PostConstruct
    private void initVals() {
        var customFieldsId = Arrays.stream(AmoReqiredCustomField.values())
                .map(AmoReqiredCustomField::getValue)
                .toList();
        this.existValues = getAvailable(customFieldsId);
    }

    public List<AmoCustomFieldExistValues> getAvailableVals() {
        return existValues;
    }

    private List<AmoCustomFieldExistValues> getAvailable(List<Long> customFieldIds) {

        List<String> urls = customFieldIds.stream()
                .map(e -> String.format("/leads/custom_fields/%s", e))
                .toList();

        // Выполняем запросы последовательно с задержкой между каждым запросом
        Flux<AmoCustomFieldExistValues> resultFlux = Flux.fromIterable(urls)
                .concatMap(url -> fetchUrl(url).delayElement(Duration.ofMillis(200))); // используем concatMap для последовательного выполнения

        // Подписываемся на Flux и выводим результаты, когда все запросы завершатся
        var result = resultFlux.collectList().block();
        System.out.println(result);
        return result;
    }

    private Mono<AmoCustomFieldExistValues> fetchUrl(String url) {
        return webClient.get()
                .uri(url)
                .retrieve()
                .bodyToMono(String.class)
                .map(converter::convertExistValues)
                .onErrorResume(error -> {
                    // В случае ошибки возвращаем сообщение с ошибкой
                    throw new RuntimeException("Failed to fetch url: " + url, error);
                });
    }
}
