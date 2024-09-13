package bot.task.bali.service.ai.main;

import bot.task.bali.dto.UserDTO;
import bot.task.bali.entities.AppUser;
import bot.task.bali.entities.Status;
import bot.task.bali.repo.appuser.GetterUserById;
import bot.task.bali.service.ai.assistant.AdderSystemMessageToChat;
import bot.task.bali.service.ai.assistant.Assistant;
import bot.task.bali.service.amo.AmoApiColumnMover;
import bot.task.bali.service.amo.GetterFilledFields;
import bot.task.bali.service.amo.GetterRequiredFields;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class AiBotServiceManager implements AiBotService {

    private final Assistant assistant;
    private final PromptService promptService;

    @Override
    public String generateResponse(UserDTO userDTO, String userMessage) {
        promptService.addPrompt(userDTO);
        return assistant.chat(userDTO.getAppUserId(), userMessage);
    }
}
