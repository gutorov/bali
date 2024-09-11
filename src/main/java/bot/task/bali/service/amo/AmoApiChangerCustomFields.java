package bot.task.bali.service.amo;

import bot.task.bali.entities.utils.AmoCustomField;
import bot.task.bali.entities.utils.AmoCustomFieldValue;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface AmoApiChangerCustomFields {
    CompletableFuture<Boolean> setCustomValue(Long leadId, AmoCustomField amoCustomField);
    default CompletableFuture<Boolean> setCustomValue(Long amoLeadId, Long fieldId, Long value) {
        return setCustomValue(amoLeadId, AmoCustomField.builder()
                .id(fieldId)
                .values(List.of(AmoCustomFieldValue.builder()
                        .id(value)
                        .build())
                )
                .build());
    }
    default CompletableFuture<Boolean> setCustomValue(Long amoLeadId, Long fieldId, String value) {
        return setCustomValue(amoLeadId, AmoCustomField.builder()
                .id(fieldId)
                .values(List.of(AmoCustomFieldValue.builder()
                        .value(value)
                        .build())
                )
                .build());
    }
}
