package bot.task.bali.service.bot.adapter;

import bot.task.bali.dto.UserPromtDTO;
import bot.task.bali.entities.AppUser;
import bot.task.bali.entities.TelegramUser;
import org.springframework.stereotype.Component;

@Component
class Converter implements ConverterTelegramUser {
    @Override
    public UserPromtDTO convertToUserPromt(TelegramUser telegramUser) {
        return UserPromtDTO.builder()
                .appUserId(telegramUser.getId())
                .build();
    }
}
