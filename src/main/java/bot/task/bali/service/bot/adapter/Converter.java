package bot.task.bali.service.bot.adapter;

import bot.task.bali.dto.UserDTO;
import bot.task.bali.entities.TelegramUser;
import org.springframework.stereotype.Component;

@Component
class Converter implements ConverterTelegramUser {
    @Override
    public UserDTO convertToUserPromt(TelegramUser telegramUser) {
        return UserDTO.builder()
                .appUserId(telegramUser.getId())
                .amoLeadId(telegramUser.getAmoCrmLeadId())
                .build();
    }
}
