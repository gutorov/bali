package bot.task.bali.service.ai.main;

import bot.task.bali.dto.UserPromtDTO;
import bot.task.bali.entities.AppUser;
import bot.task.bali.entities.Status;
import bot.task.bali.repo.appuser.GetterUserById;
import bot.task.bali.service.ai.assistant.AdderSystemMessageToChat;
import bot.task.bali.service.ai.assistant.Assistant;
import bot.task.bali.service.amo.GetterRequiredFields;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class AiBotServiceManager implements AiBotService {

    private final Assistant assistant;
    private final GetterUserById getterUserById;
    private final AdderSystemMessageToChat adderSystemMessageToChat;
    private final GetterRequiredFields getterRequiredFields;

    @Override
    public String generateResponse(UserPromtDTO userPromtDTO, String userMessage) {
        addParamsOnCurrentStage(userPromtDTO);
        return assistant.chat(userPromtDTO.getAppUserId(), userMessage);
    }

    private void addParamsOnCurrentStage(UserPromtDTO userPromtDTO) {

        var user = getterUserById.getUserById(userPromtDTO.getAppUserId());
        var prompt = "Клиент с AmoLeadId : " + user.getAmoCrmLeadId();
        if (user.getStatus() == Status.WARM) {
            var requiredFields = getterRequiredFields.getJsonFields(user);
            prompt = "Твоя задача незаметно узнать у клиента ряд вопросов, " +
                    "Но задавай только по одному. " +
                    "\n\nПриведу вопросы в json формате: \n"
                    + requiredFields;
        }

        adderSystemMessageToChat.addMessageToChat(user.getId(), prompt);
    }
}
