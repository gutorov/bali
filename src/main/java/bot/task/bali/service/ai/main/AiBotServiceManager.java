package bot.task.bali.service.ai.main;

import bot.task.bali.dto.UserPromtDTO;
import bot.task.bali.service.ai.assistant.Assistant;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class AiBotServiceManager implements AiBotService {

    private final Assistant assistant;

    @Override
    public String generateResponse(UserPromtDTO userPromtDTO, String userMessage) {
        return assistant.chat(userPromtDTO.getAppUserId(), userMessage);
    }
}
