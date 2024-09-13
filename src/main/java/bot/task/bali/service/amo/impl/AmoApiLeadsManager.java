package bot.task.bali.service.amo.impl;

import bot.task.bali.entities.amo.AmoColumnQualification;
import bot.task.bali.entities.utils.AmoCustomField;
import bot.task.bali.service.amo.AmoApiChangerCustomFields;
import bot.task.bali.service.amo.AmoApiCreatorAmoUser;
import bot.task.bali.service.amo.AmoApiColumnMover;
import bot.task.bali.service.app.converter.ConverterToCustomField;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Component
@Log4j2
public class AmoApiLeadsManager implements AmoApiChangerCustomFields, AmoApiCreatorAmoUser, AmoApiColumnMover {

    private final WebClient webClient;
    private final Gson gson;

    public AmoApiLeadsManager(@Value("${amo.crm.api-url}") String url, @Value("${amo.crm.api-key}") String apiKey, ConverterToCustomField converter ) {
        this.webClient = WebClient.builder()
                .baseUrl(url) // /api/v4/leads
                .defaultHeader("Authorization", "Bearer " + apiKey)
                .build();
        this.gson = new Gson();
    }

    @Async
    @Override
    public CompletableFuture<Boolean> setCustomValue(@NonNull Long leadId, AmoCustomField amoCustomField) {
        try {
            List<Val> vals = amoCustomField.getValues().stream()
                    .map(e -> new Val(e.getId(), e.getValue()))
                    .toList();

            var request =
                        List.of(
                                new Lead(
                                        leadId,
                                        List.of(
                                                new Fields(amoCustomField.getId(), vals))
                                ));
            var jsonRequest = gson.toJson(request);
            Boolean result = webClient.patch()
                    .uri("/api/v4/leads")
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(jsonRequest)
                    .retrieve()
                    .bodyToMono(Void.class)
                    .thenReturn(true)
                    .doOnError(e -> log.error("Error setting custom value", e))
                    .onErrorReturn(false)
                    .block();

            return CompletableFuture.completedFuture(result);
        } catch (Exception e) {
            log.error(e);
        }
        return CompletableFuture.completedFuture(false);
    }

    @Override
    public Long createNewLead(@NonNull String name) {
        var request = List.of(
                new NewUser(name)
        );
        var jsonRequest = gson.toJson(request);

        Long result = webClient.post()
                .uri("/api/v4/leads")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(jsonRequest)
                .retrieve()
                .bodyToMono(String.class)
                .map(response -> {
                    JsonObject jsonObject = gson.fromJson(response, JsonObject.class);
                    JsonObject embedded = jsonObject.getAsJsonObject("_embedded");
                    JsonArray leads = embedded.getAsJsonArray("leads");
                    JsonObject firstLead = leads.get(0).getAsJsonObject();
                    return firstLead.get("id").getAsLong();
                })
                .doOnError(e -> log.error("Error creating new lead", e))
                .block();

        return result;
    }

    @Override
    public boolean moveUser(@NonNull Long amoLeadId, AmoColumnQualification amoColumn) {
        try{
            JsonObject jsonRequest = new JsonObject();
            jsonRequest.addProperty("status_id", amoColumn.getValue());

            Boolean result = webClient.patch()
                    .uri("/api/v4/leads/" + amoLeadId)
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(jsonRequest.toString())
                    .retrieve()
                    .bodyToMono(Void.class)
                    .thenReturn(true)
                    .doOnError(e -> log.error("Error setting custom value", e))
                    .onErrorReturn(false)
                    .block();

            return result;
        }catch (Exception e) {
            log.error(e);
        }
        return false;
    }

    record NewUser(String name) {}

    record Lead(Long id, List<Fields> custom_fields_values) {}
    record Fields(Long field_id, List<Val> values) {}
    record Val(Long enum_id, String value) {}
}
