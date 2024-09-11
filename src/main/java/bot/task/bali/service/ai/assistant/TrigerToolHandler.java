package bot.task.bali.service.ai.assistant;

import bot.task.bali.entities.Status;
import bot.task.bali.entities.enums.AmoReqiredCustomField;
import bot.task.bali.entities.utils.AmoCustomFieldExistValues;
import bot.task.bali.entities.utils.AmoCustomFieldValueExistValue;
import bot.task.bali.repo.appuser.AppUserSaver;
import bot.task.bali.repo.appuser.GetterUserById;
import bot.task.bali.service.amo.AmoApiChangerCustomFields;
import bot.task.bali.service.amo.GetterAvailableVals;
import dev.langchain4j.agent.tool.Tool;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Log4j2
@AllArgsConstructor
public class TrigerToolHandler {

    private final GetterAvailableVals getterAvailableVals;
    private final AmoApiChangerCustomFields amoApiChangerCustomFields;
    private final GetterUserById getterUserById;
    private final AppUserSaver appUserSaver;

    @Tool("Цель покупки")
    String setGoal(String value, Long amoLeadId) {
        List<AmoCustomFieldValueExistValue> vals = getValuesByAmoFields(AmoReqiredCustomField.PURPOSE);
        for (var field : vals) {
            if (field.getValue().equalsIgnoreCase(value)) {
                amoApiChangerCustomFields.setCustomValue(amoLeadId, AmoReqiredCustomField.PURPOSE.getValue(), field.getId());
                return "Цель покупки: " + field.getValue();
            }
        }
        return "Не удалось установить цель покупки. Доступные варианты: "
                + vals.stream().map(AmoCustomFieldValueExistValue::getValue).toList();
    }

    @Tool("Срок покупки")
    String setPurchaseDeadline(String value, Long amoLeadId) {
        List<AmoCustomFieldValueExistValue> vals = getValuesByAmoFields(AmoReqiredCustomField.PURCHASE_TERM);
        for (var field : vals) {
            if (field.getValue().equalsIgnoreCase(value)) {
                amoApiChangerCustomFields.setCustomValue(amoLeadId, AmoReqiredCustomField.PURCHASE_TERM.getValue(), field.getId());
                return "Срок покупки установлен: " + field.getValue();
            }
        }
        return "Не удалось установить срок покупки. Доступные варианты: "
                + vals.stream().map(AmoCustomFieldValueExistValue::getValue).toList();
    }

    @Tool("Район")
    String setDistrict(String value, Long amoLeadId) {
        List<AmoCustomFieldValueExistValue> vals = getValuesByAmoFields(AmoReqiredCustomField.REGION);
        for (var field : vals) {
            if (field.getValue().equalsIgnoreCase(value)) {
                amoApiChangerCustomFields.setCustomValue(amoLeadId, AmoReqiredCustomField.REGION.getValue(), field.getId());
                return "Район: " + field.getValue();
            }
        }
        return "Не удалось установить район. Доступные варианты: "
                + vals.stream().map(AmoCustomFieldValueExistValue::getValue).toList();
    }

    @Tool("Страна проживания")
    String getCountry(String country, Long amoLeadId) {
        List<AmoCustomFieldValueExistValue> vals = getValuesByAmoFields(AmoReqiredCustomField.COUNTRY);

        amoApiChangerCustomFields.setCustomValue(amoLeadId, AmoReqiredCustomField.COUNTRY.getValue(), country);
        return "Страна проживания: " + country;

    }

    @Tool("Сейчас на Бали")
    String isCurrentlyOnBali(String value, Long amoLeadId) {
        List<AmoCustomFieldValueExistValue> vals = getValuesByAmoFields(AmoReqiredCustomField.CURRENTLY_IN_BALI);
        for (var field : vals) {
            if (field.getValue().equalsIgnoreCase(value)) {
                amoApiChangerCustomFields.setCustomValue(amoLeadId, AmoReqiredCustomField.CURRENTLY_IN_BALI.getValue(), field.getId());
                return "Вы находитесь на Бали: " + field.getValue();
            }
        }
        return "Не удалось установить, находитесь ли вы на Бали. Доступные варианты: "
                + vals.stream().map(AmoCustomFieldValueExistValue::getValue).toList();
    }

    @Tool("Покупка онлайн")
    String onlinePurchase(String value, Long amoLeadId) {
        List<AmoCustomFieldValueExistValue> vals = getValuesByAmoFields(AmoReqiredCustomField.PURCHASE_ONLINE);
        for (var field : vals) {
            if (field.getValue().equalsIgnoreCase(value)) {
                amoApiChangerCustomFields.setCustomValue(amoLeadId, AmoReqiredCustomField.PURCHASE_ONLINE.getValue(), field.getId());
                return "Покупка онлайн: " + field.getValue();
            }
        }
        return "Не удалось установить онлайн-покупку. Доступные варианты: "
                + vals.stream().map(AmoCustomFieldValueExistValue::getValue).toList();
    }

    @Tool("Причина отказа")
    String setRejectionReason(String value, Long amoLeadId) {
        List<AmoCustomFieldValueExistValue> vals = getValuesByAmoFields(AmoReqiredCustomField.REJECTION_REASON);
        for (var field : vals) {
            if (field.getValue().equalsIgnoreCase(value)) {
                amoApiChangerCustomFields.setCustomValue(amoLeadId, AmoReqiredCustomField.REJECTION_REASON.getValue(), field.getId());
                return "Причина отказа: " + field.getValue();
            }
        }
        return "Не удалось установить причину отказа. Доступные варианты: "
                + vals.stream().map(AmoCustomFieldValueExistValue::getValue).toList();
    }

    @Tool("Язык речи")
    String setLanguage(String value, Long amoLeadId) {
        List<AmoCustomFieldValueExistValue> vals = getValuesByAmoFields(AmoReqiredCustomField.CLIENT_LANGUAGE);
        for (var field : vals) {
            if (field.getValue().equalsIgnoreCase(value)) {
                amoApiChangerCustomFields.setCustomValue(amoLeadId, AmoReqiredCustomField.CLIENT_LANGUAGE.getValue(), field.getId());
                return "Язык речи: " + field.getValue();
            }
        }
        return "Не удалось установить язык речи. Доступные варианты: "
                + vals.stream().map(AmoCustomFieldValueExistValue::getValue).toList();
    }

    @Tool("Тип сделки")
    String setDealType(String value, Long amoLeadId) {
        List<AmoCustomFieldValueExistValue> vals = getValuesByAmoFields(AmoReqiredCustomField.DEAL_TYPE);
        for (var field : vals) {
            if (field.getValue().equalsIgnoreCase(value)) {
                amoApiChangerCustomFields.setCustomValue(amoLeadId, AmoReqiredCustomField.DEAL_TYPE.getValue(), field.getId());
                return "Тип сделки: " + field.getValue();
            }
        }
        return "Не удалось установить тип сделки. Доступные варианты: "
                + vals.stream().map(AmoCustomFieldValueExistValue::getValue).toList();
    }

    @Tool("Я готов сделать покупку / я заинтересован вашим сервисом")
    String convertedToWarm(Long amoLeadId) {
        var user = getterUserById.getByAmoLeadId(amoLeadId);
        user.setStatus(Status.WARM);
        appUserSaver.save(user);
        return "Могу ли задать несколько вопросов?";
    }

    @NonNull
    private List<AmoCustomFieldValueExistValue> getValuesByAmoFields(AmoReqiredCustomField amoReqiredCustomField) {
        List<AmoCustomFieldExistValues> availableVals = getterAvailableVals.getAvailableVals();
        var opt = availableVals.stream()
                .filter(e -> e.getId().equals(amoReqiredCustomField.getValue()))
                .findFirst();
        if (opt.isPresent()) {
            return opt.get().getValues();
        }
        return new ArrayList<>();
    }


}
