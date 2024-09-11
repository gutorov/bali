package bot.task.bali.repo.telegram;

import bot.task.bali.entities.TelegramUser;
import bot.task.bali.service.amo.AmoApiCreatorAmoUser;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@AllArgsConstructor
@Log4j2
public class TelegramUsersManage implements GetterTelegramUser {

    private TelegramUserRepository telegramUserRepository;
    private AmoApiCreatorAmoUser amoApiCreatorAmoUser;

    @Override
    public TelegramUser getByUpdate(Update update) {
        try {
            var id = update.getMessage().getChatId();
            var opt = telegramUserRepository.findByChatId(id);
            if (opt.isPresent()) {
                return opt.get();
            }

//            var telegramUserName = update.get

            var user = TelegramUser.builder()
                    .chatId(update.getMessage().getChatId())
                    .amoCrmLeadId(amoApiCreatorAmoUser.createNewLead("Telegram User : " + update.getMessage().getChatId()))
                    .build();
            return telegramUserRepository.save(user);
        } catch (Exception e) {
            log.error(e);
            throw new RuntimeException("User not found", e);
        }
    }

}
