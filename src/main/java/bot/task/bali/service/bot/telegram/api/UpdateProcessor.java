package bot.task.bali.service.bot.telegram.api;

import org.telegram.telegrambots.meta.api.objects.Update;

public interface UpdateProcessor {
    void processUpdate(Update update);
}
