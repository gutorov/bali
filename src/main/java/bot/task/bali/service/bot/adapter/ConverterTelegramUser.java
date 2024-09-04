package bot.task.bali.service.bot.adapter;

import bot.task.bali.dto.UserPromtDTO;
import bot.task.bali.entities.TelegramUser;

public interface ConverterTelegramUser {
    UserPromtDTO convertToUserPromt(TelegramUser telegramUser);
}
