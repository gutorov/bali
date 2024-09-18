package bot.task.bali.service.ai.main;

import bot.task.bali.dto.UserDTO;
import bot.task.bali.service.ai.assistant.Assistant;
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
