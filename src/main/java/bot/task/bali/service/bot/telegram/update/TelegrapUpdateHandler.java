package bot.task.bali.service.bot.telegram.update;

import org.telegram.telegrambots.meta.api.objects.Update;

public interface TelegrapUpdateHandler {
    void handleText(Update update);
}
