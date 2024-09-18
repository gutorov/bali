package bot.task.bali.service.bot.adapter;

import bot.task.bali.dto.UserDTO;
import bot.task.bali.entities.AppUser;
import bot.task.bali.entities.TelegramUser;
import bot.task.bali.entities.WhatsAppUser;
import org.springframework.stereotype.Component;

@Component
class Converter implements ConvertAppUser {

    @Override
    public UserDTO convertToUserPrompt(AppUser appUser) {
        return UserDTO.builder()
                .appUserId(appUser.getId())
                .amoLeadId(appUser.getAmoCrmLeadId())
                .build();
    }
}
