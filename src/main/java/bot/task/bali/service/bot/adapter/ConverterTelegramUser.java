package bot.task.bali.service.bot.adapter;

import bot.task.bali.dto.UserDTO;
import bot.task.bali.entities.TelegramUser;

public interface ConverterTelegramUser {
    UserDTO convertToUserPromt(TelegramUser telegramUser);
}
