package bot.task.bali.service.ai.main;

import bot.task.bali.dto.UserDTO;

public interface AiBotService {
    String generateResponse(UserDTO userDTO, String userMessage);
}
