package bot.task.bali.service.amo.impl;

import bot.task.bali.entities.AppUser;
import bot.task.bali.entities.utils.AmoCustomField;
import bot.task.bali.service.amo.GetterAvailableVals;
import bot.task.bali.service.amo.GetterRequiredFields;
import bot.task.bali.service.app.converter.ConverterToCustomField;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Component
public class AmoManagerImpl implements GetterRequiredFields {

    private final WebClient webClient;
    private final Gson gson;
    private final ConverterToCustomField converter;
    private final GetterAvailableVals getterAvailableVals;

    public AmoManagerImpl(@Value("${amo.crm.api-url}") String url, @Value("${amo.crm.api-key}") String apiKey, ConverterToCustomField converter, GetterAvailableVals getterAvailableVals) {
        this.getterAvailableVals = getterAvailableVals;
        this.webClient = WebClient.builder()
                .baseUrl(url + "/api/v4/")
                .defaultHeader("Authorization", "Bearer " + apiKey)
                .build();
        this.gson = new Gson();
        this.converter = converter;
    }

    @Override
    public String getJsonFields(AppUser appUser) {

        List<Long> filledFieldsByUser = getValues(appUser.getAmoCrmLeadId())
                .stream()
                .map(AmoCustomField::getId)
                .toList();

        var neededToFill = getterAvailableVals.getAvailableVals().stream()
                .filter(e -> !filledFieldsByUser.contains(e.getId()))
                .toList();


        return gson.toJson(neededToFill);
    }

    private List<AmoCustomField> getValues(Long amoCrmUserId) {
        String response = webClient.get()
                .uri("/leads/" + amoCrmUserId)
                .retrieve()
                .bodyToMono(String.class)  // получаем JSON как строку
                .block();  // блокируем для синхронного выполнения (избегайте в реальных приложениях)

        return converter.convertToList(response);  // передаем JSON для конвертации
    }
}
