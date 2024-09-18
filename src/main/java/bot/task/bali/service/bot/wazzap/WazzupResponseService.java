package bot.task.bali.service.bot.wazzap;

import bot.task.bali.dto.WazzupBody;

public interface WazzupResponseService {
    void sendMessage(WazzupBody body);
}
