package bot.task.bali.service.bot.telegram.update;

import bot.task.bali.repo.telegram.GetterTelegramUser;
import bot.task.bali.service.ai.main.AiBotService;
import bot.task.bali.service.bot.adapter.ConverterTelegramUser;
import bot.task.bali.service.bot.telegram.api.TelegramResponseExecutor;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@AllArgsConstructor
class UpdateHandler implements TelegrapUpdateHandler {

    private AiBotService aiBotService;
    private TelegramResponseExecutor response;
    private GetterTelegramUser getterTelegramUser;
    private ConverterTelegramUser converterTelegramUser;

    @Override
    public void handleText(Update update) {
        if (!(update.hasMessage() && update.getMessage().hasText()))
            throw new RuntimeException("there is no text in update");
        var text = update.getMessage().getText();
        var chatId = update.getMessage().getChatId();

        var telegramUser = getterTelegramUser.getByUpdate(update);
        var userPromtDTO = converterTelegramUser.convertToUserPromt(telegramUser);

        var responseFromGpt = aiBotService.generateResponse(userPromtDTO, text);
        response.execute(chatId, responseFromGpt);
    }
}
