package bot.task.bali.repo.whatsapp;

import bot.task.bali.dto.WazzupBody;
import bot.task.bali.entities.TelegramUser;
import bot.task.bali.entities.WhatsAppUser;
import bot.task.bali.service.amo.AmoApiCreatorAmoUser;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@AllArgsConstructor
@Log4j2
public class TelegramUsersManage implements GetterWhatsAppUser {

    private WhatsAppUserRepository whatsAppUserRepository;
    private AmoApiCreatorAmoUser amoApiCreatorAmoUser;

    @Override
    public WhatsAppUser getByBody(WazzupBody body) {
        try {
            var chatId = body.getChatId();
            var opt = whatsAppUserRepository.findByChatId(chatId);
            if (opt.isPresent()) {
                return opt.get();
            }

            var user = WhatsAppUser.builder()
                    .chatId(chatId)
                    .amoCrmLeadId(amoApiCreatorAmoUser.createNewLead("From WhatsApp Bot : " + chatId))
                    .build();

            return whatsAppUserRepository.save(user);
        } catch (Exception e) {
            log.error(e);
            throw new RuntimeException("User not found", e);
        }
    }

}
