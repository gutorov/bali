package bot.task.bali.service.ai.assistant;

import bot.task.bali.entities.Status;
import bot.task.bali.entities.amo.AmoColumnQualification;
import bot.task.bali.entities.amo.AmoReqiredCustomField;
import bot.task.bali.entities.utils.AmoCustomFieldExistValues;
import bot.task.bali.entities.utils.AmoCustomFieldValueExistValue;
import bot.task.bali.repo.appuser.AppUserSaver;
import bot.task.bali.repo.appuser.GetterUserById;
import bot.task.bali.service.amo.AmoApiChangerCustomFields;
import bot.task.bali.service.amo.AmoApiColumnMover;
import bot.task.bali.service.amo.GetterAvailableVals;
import dev.langchain4j.agent.tool.P;
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
    private final AmoApiColumnMover amoApiColumnMover;

    @Tool("Тип проекта")
    String typeProject(String value, Long amoLeadId) {
        List<AmoCustomFieldValueExistValue> vals = getValuesByAmoFields(AmoReqiredCustomField.PROJECT);
        for (var field : vals) {
            if (field.getValue().equalsIgnoreCase(value)) {
                amoApiChangerCustomFields.setCustomValue(amoLeadId, AmoReqiredCustomField.PROJECT.getValue(), field.getId());
                log.debug("Тип проекта зафиксирован для юзера {} тип: {}", amoLeadId, value);
                return "Зафиксирован тип: " + field.getValue();
            }
        }
        return "Не удалось установить проект. Доступные варианты: "
                + vals.stream().map(AmoCustomFieldValueExistValue::getValue).toList();
    }

    @Tool("Цель покупки")
    String setGoal(String value, Long amoLeadId) {
        List<AmoCustomFieldValueExistValue> vals = getValuesByAmoFields(AmoReqiredCustomField.PURPOSE);
        for (var field : vals) {
            if (field.getValue().equalsIgnoreCase(value)) {
                amoApiChangerCustomFields.setCustomValue(amoLeadId, AmoReqiredCustomField.PURPOSE.getValue(), field.getId());
                log.debug("Цель покупки зафиксирован для юзера {} тип: {}", amoLeadId, value);
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
                log.debug("Срок покупки установлен для юзера {} тип: {}", amoLeadId, value);
                return "Срок покупки установлен: " + field.getValue();
            }
        }
        return "Не удалось установить срок покупки. Доступные варианты: "
                + vals.stream().map(AmoCustomFieldValueExistValue::getValue).toList();
    }

    // todo проверку, если уже установлено, то не надо менять
    @Tool("Район строительства")
    String setDistrict(String value, Long amoLeadId) {
        List<AmoCustomFieldValueExistValue> vals = getValuesByAmoFields(AmoReqiredCustomField.REGION);
        for (var field : vals) {
            if (field.getValue().equalsIgnoreCase(value)) {
                amoApiChangerCustomFields.setCustomValue(amoLeadId, AmoReqiredCustomField.REGION.getValue(), field.getId());
                log.debug("Район сторительства зафиксирован для юзера {} тип: {}", amoLeadId, value);
                return "Район сторительства: " + field.getValue();
            }
        }
        return "Не удалось установить район для строительства. Доступные варианты: "
                + vals.stream().map(AmoCustomFieldValueExistValue::getValue).toList();
    }

    @Tool("Страна проживания")
    String getCountry(String country, Long amoLeadId) {
        List<AmoCustomFieldValueExistValue> vals = getValuesByAmoFields(AmoReqiredCustomField.COUNTRY);

        amoApiChangerCustomFields.setCustomValue(amoLeadId, AmoReqiredCustomField.COUNTRY.getValue(), country);
        log.debug("Страна проживания зафиксирована для юзера {} тип: {}", amoLeadId, country);
        return "Страна проживания: " + country;

    }

    @Tool("Сейчас на Бали")
    String isCurrentlyOnBali(String value, Long amoLeadId) {
        List<AmoCustomFieldValueExistValue> vals = getValuesByAmoFields(AmoReqiredCustomField.CURRENTLY_IN_BALI);
        for (var field : vals) {
            if (field.getValue().equalsIgnoreCase(value)) {
                amoApiChangerCustomFields.setCustomValue(amoLeadId, AmoReqiredCustomField.CURRENTLY_IN_BALI.getValue(), field.getId());
                log.debug("Тип Сейчас на Бали зафиксирован для юзера {} тип: {}", amoLeadId, value);
                return "Хорошо, локация сохранена " + field.getValue();
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
                log.debug("Тип Покупка онлайн зафиксирован для юзера {} тип: {}", amoLeadId, value);
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
                log.debug("Причина отказа зафиксирован для юзера {} тип: {}", amoLeadId, value);
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
                log.debug("Тип Язык речи зафиксирован для юзера {} тип: {}", amoLeadId, value);
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
                log.debug("Тип сделки зафиксирован для юзера {} тип: {}", amoLeadId, value);
                return "Тип сделки: " + field.getValue();
            }
        }
        return "Не удалось установить тип сделки. Доступные варианты: "
                + vals.stream().map(AmoCustomFieldValueExistValue::getValue).toList();
    }

    /**
     * Конвертирует слиента в статус Warm, что позволяет задавать вопросы для заполнения заявки.
     * Так-же передвигается в колонку
     *
     * @param amoLeadId
     * @return
     */
    @Tool("Нравится клиенту Bali Investment")
    String convertedToWarm(@P("AmoLeadId клиента") Long amoLeadId) {
        var user = getterUserById.getByAmoLeadId(amoLeadId);
        user.setStatus(Status.WARM);
        appUserSaver.save(user);
        amoApiColumnMover.moveUser(amoLeadId, AmoColumnQualification.CONTACT_ESTABLISHED);
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
