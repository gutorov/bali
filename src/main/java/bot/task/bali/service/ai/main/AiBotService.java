package bot.task.bali.service.ai.main;

import bot.task.bali.dto.UserPromtDTO;

public interface AiBotService {
    String generateResponse(UserPromtDTO userPromtDTO, String userMessage);
}
