package bot.task.bali.repo.whatsapp;

import bot.task.bali.dto.WazzupBody;
import bot.task.bali.entities.WazzupUser;

public interface GetterWhatsAppUser {
    WazzupUser getByBody(WazzupBody body);
}
