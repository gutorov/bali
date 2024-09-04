package bot.task.bali.service.bot.telegram.impl;

import bot.task.bali.service.bot.telegram.api.TelegramResponseExecutor;
import bot.task.bali.service.bot.telegram.api.UpdateProcessor;
import bot.task.bali.service.bot.telegram.update.TelegrapUpdateHandler;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@Log4j2
@AllArgsConstructor
class UpdateProcessorImpl implements UpdateProcessor {

    private TelegramResponseExecutor telegramResponseExecutor;
    private final TelegrapUpdateHandler telegrapUpdateHandler;

    public void processUpdate(Update update) {
        if (update == null) {
            log.error("Received update is null");
            return;
        }
        try {
            distributeMessagesByType(update);
        } catch (Exception e) {
            if (update.hasMessage()) {
                telegramResponseExecutor.execute(
                        Long.valueOf(update.getMessage().getChatId().toString()),
                        "У меня не получилось дать ответ: " + e.getMessage());
            }
        }
    }

    private void distributeMessagesByType(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            telegrapUpdateHandler.handleText(update);
        } else {
            throw new RuntimeException("Invalid message type");
        }
    }
}
