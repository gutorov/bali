package bot.task.bali.repo.telegram;

import bot.task.bali.entities.TelegramUser;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@AllArgsConstructor
public class TelegramUsersManage implements GetterTelegramUser {

    private TelegramUserRepository telegramUserRepository;

    @Override
    public TelegramUser getByUpdate(Update update) {
        try {
            var id = update.getMessage().getChatId();
            var opt = telegramUserRepository.findByChatId(id);
            if (opt.isPresent()) {
                return opt.get();
            }
            throw new RuntimeException("User not found");
        } catch (Exception e) {
            throw new RuntimeException("User not found", e);
        }
    }
}
