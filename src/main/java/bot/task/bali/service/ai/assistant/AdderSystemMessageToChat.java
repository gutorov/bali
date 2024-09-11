package bot.task.bali.service.ai.assistant;

import bot.task.bali.entities.AppUser;
import bot.task.bali.entities.Status;

import java.util.UUID;

public interface AdderSystemMessageToChat {

    void addMessageToChat(UUID key, String message);
}
