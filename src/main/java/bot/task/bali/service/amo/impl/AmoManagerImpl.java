package bot.task.bali.service.amo.impl;

import bot.task.bali.entities.AppUser;
import bot.task.bali.entities.amo.AmoReqiredCustomField;
import bot.task.bali.entities.utils.AmoCustomField;
import bot.task.bali.service.amo.GetterAvailableVals;
import bot.task.bali.service.amo.GetterFilledFields;
import bot.task.bali.service.amo.GetterRequiredFields;
import bot.task.bali.service.app.converter.ConverterToCustomField;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class AmoManagerImpl implements GetterRequiredFields, GetterFilledFields {

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

        Set<Long> excludeVals = new HashSet<>();

        switch (appUser.getStatus()) {
            case WARM -> {
                excludeVals.add(AmoReqiredCustomField.CLIENT_LANGUAGE.getValue());
                excludeVals.add(AmoReqiredCustomField.DEAL_TYPE.getValue());
                excludeVals.add(AmoReqiredCustomField.REJECTION_REASON.getValue());
                excludeVals.add(AmoReqiredCustomField.SOURCE.getValue());
            }
        }

        List<Long> filledFieldsByUser = getValues(appUser.getAmoCrmLeadId())
                .stream()
                .map(AmoCustomField::getId)
                .toList();

        var neededToFill = getterAvailableVals.getAvailableVals().stream()
                .filter(e -> !filledFieldsByUser.contains(e.getId()))
                .filter(e -> !excludeVals.contains(e.getId()))
                .toList();
        if (neededToFill.isEmpty()) {
            return "";
        }
        return gson.toJson(neededToFill);
    }

    @Override
    public String getFilledJsonFields(AppUser appUser) {
        return gson.toJson(getValues(appUser.getAmoCrmLeadId()));
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
