package bot.task.bali.service.bot.adapter;

import bot.task.bali.dto.UserDTO;
import bot.task.bali.entities.AppUser;

public interface ConvertAppUser {
    UserDTO convertToUserPrompt(AppUser appUser);
}
