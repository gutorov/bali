package bot.task.bali.service.bot.telegram.impl;

import bot.task.bali.service.bot.telegram.api.TelegramResponseExecutor;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

@Component
@AllArgsConstructor
class TelegramResponseExecutorManager implements TelegramResponseExecutor {

    private TelegramBotManager telegramBotManager;

    @Override
    public void execute(Long chatId, String message) {
        telegramBotManager.sendAnswerMessage(new SendMessage(chatId.toString(), message));
    }
}
