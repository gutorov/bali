package bot.task.bali.service.bot.telegram.update;

import bot.task.bali.repo.telegram.GetterTelegramUser;
import bot.task.bali.service.ai.main.AiBotService;
import bot.task.bali.service.bot.adapter.ConvertAppUser;
import bot.task.bali.service.bot.telegram.api.TelegramResponseExecutor;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@AllArgsConstructor
class UpdateHandler implements TelegramUpdateHandler {

    private final AiBotService aiBotService;
    private final TelegramResponseExecutor response;
    private final GetterTelegramUser getterTelegramUser;
    private final ConvertAppUser convertAppUser;

    @Override
    public void handleText(Update update) {
        if (!(update.hasMessage() && update.getMessage().hasText()))
            throw new RuntimeException("there is no text in update");
        var text = update.getMessage().getText();
        var chatId = update.getMessage().getChatId();

        var telegramUser = getterTelegramUser.getByUpdate(update);
        var userPromptDTO = convertAppUser.convertToUserPrompt(telegramUser);

        var responseFromGpt = aiBotService.generateResponse(userPromptDTO, text);
        response.execute(chatId, responseFromGpt);
    }
}
