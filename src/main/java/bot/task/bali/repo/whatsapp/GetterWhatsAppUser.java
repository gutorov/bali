package bot.task.bali.repo.whatsapp;

import bot.task.bali.dto.WazzupBody;
import bot.task.bali.entities.WhatsAppUser;

public interface GetterWhatsAppUser {

    WhatsAppUser getByBody(WazzupBody body);
}
