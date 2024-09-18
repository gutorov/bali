package bot.task.bali.repo.telegram;

import bot.task.bali.entities.TelegramUser;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface GetterTelegramUser {
    TelegramUser getByUpdate(Update update);
}
