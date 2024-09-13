package bot.task.bali.service.ai.main;

import bot.task.bali.dto.UserDTO;
import bot.task.bali.entities.AppUser;
import bot.task.bali.entities.Status;
import bot.task.bali.entities.amo.AmoColumnQualification;
import bot.task.bali.entities.amo.AmoReqiredCustomField;
import bot.task.bali.entities.utils.AmoCustomField;
import bot.task.bali.entities.utils.AmoCustomFieldExistValues;
import bot.task.bali.entities.utils.AmoCustomFieldValue;
import bot.task.bali.entities.utils.AmoCustomFieldValueExistValue;
import bot.task.bali.repo.appuser.GetterUserById;
import bot.task.bali.service.ai.assistant.AdderSystemMessageToChat;
import bot.task.bali.service.ai.assistant.ServiceAssistant;
import bot.task.bali.service.amo.*;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Log4j2
@Component
@AllArgsConstructor
class PromptServiceManager implements PromptService {

    private final GetterUserById getterUserById;
    private final AdderSystemMessageToChat adderSystemMessageToChat;
    private final GetterRequiredFields getterRequiredFields;
    private final GetterFilledFields getterFilledFields;
    private final GetterAvailableVals getterAvailableVals;
    private final AmoApiColumnMover amoApiColumnMover;
    private final ServiceAssistant serviceAssistant;
    private final AmoApiChangerCustomFields amoApiChangerCustomFields;

    @Override
    public void addPrompt(UserDTO userDTO) {

        var user = getterUserById.getUserById(userDTO.getAppUserId());
        StringBuilder builder = new StringBuilder();
        builder.append("Клиент с AmoLeadId : " ).append(user.getAmoCrmLeadId());
        builder.append("\n");
        if (user.getStatus() == Status.WARM) {
            var requiredFields = getterRequiredFields.getJsonFields(user);
            if (!requiredFields.isEmpty()) {
                builder.append("Текущие заполненные параметры клиента: ").append(getterFilledFields.getFilledJsonFields(user));
                builder.append( "Твоя задача узнать у клиента ряд вопросов, "
                                + "Но задавай только по одному и тот вопрос, который лучше всего подходит по контексту "
                                + "\n\nПриведу вопросы в json формате: \n")
                        .append(requiredFields);
            } else { // если уже все заполнено, можно двигать на след колонку
                moveToQualifiedColumn(user);
            }
        }

        adderSystemMessageToChat.addMessageToChat(user.getId(), builder.toString());
    }

    /**
     * Тут нужно чтобы заполнились оставшиеся поля и только потом выполнился запрос
     * Нужно создать еще одного ассистента, который определяет:
     * * агент или нет пользователь.
     * * Определить язык
     * *
     *
     * @param user
     */
    private void moveToQualifiedColumn(AppUser user) {
        AmoCustomFieldValueExistValue language = searchVals(user, AmoReqiredCustomField.CLIENT_LANGUAGE, "Определить язык клиента");
        AmoCustomFieldValueExistValue isAgent = searchVals(user, AmoReqiredCustomField.DEAL_TYPE, "Определить тип сделки. На чье повидение больше похоже");

        CompletableFuture<Boolean> resultLang = amoApiChangerCustomFields.setCustomValue(user.getAmoCrmLeadId(), AmoReqiredCustomField.CLIENT_LANGUAGE.getValue(), language.getId());
        CompletableFuture<Boolean> resultDeal = amoApiChangerCustomFields.setCustomValue(user.getAmoCrmLeadId(), AmoReqiredCustomField.DEAL_TYPE.getValue(), isAgent.getId());

        var success = resultLang.join() && resultDeal.join();

        if (success) {
            var result = amoApiColumnMover.moveUser(user.getAmoCrmLeadId(), AmoColumnQualification.QUALIFIED);
            if (!result) {
                log.warn("Fail to move user to qualified column user with amoId {}", user.getAmoCrmLeadId());
            }
        }
    }

    private AmoCustomFieldValueExistValue searchVals(AppUser user, AmoReqiredCustomField amoReqiredCustomField, String additionalInfo) {
        AmoCustomFieldExistValues vals = getterAvailableVals.getAvailableVals().stream()
                .filter(e -> e.getId().equals(amoReqiredCustomField.getValue()))
                .findFirst()
                .orElseThrow();

        return serviceAssistant.searchVals(user.getChatMessages(), vals, additionalInfo);
    }
}
