package bot.task.bali.service.ai.assistant;

import java.util.UUID;

public interface AdderSystemMessageToChat {

    void addMessageToChat(UUID key, String message);
}
