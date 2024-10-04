package bot.task.bali.repo.whatsapp;

import bot.task.bali.dto.WazzupBody;
import bot.task.bali.entities.WazzupUser;
import bot.task.bali.service.amo.AmoApiCreatorAmoUser;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Log4j2
public class WhatsappUsersManage implements GetterWhatsAppUser {

    private WhatsAppUserRepository whatsAppUserRepository;
    private AmoApiCreatorAmoUser amoApiCreatorAmoUser;

    @Override
    public WazzupUser getByBody(WazzupBody body) {
        try {
            var chatId = body.getChatId();
            var opt = whatsAppUserRepository.findByChatId(chatId);
            if (opt.isPresent()) {
                return opt.get();
            }

            var user = WazzupUser.builder()
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
