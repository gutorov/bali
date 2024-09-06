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
        var sendMessage = new SendMessage(chatId.toString(), message);
        sendMessage.setDisableWebPagePreview(true);
        sendMessage.setText(formatMessage(message));
        sendMessage.enableMarkdown(true);
        telegramBotManager.sendAnswerMessage(sendMessage);
    }

    private String formatMessage(String messageText) {
        // Заменяем форматирование для жирного текста (**текст** или __текст__)
        messageText = messageText.replaceAll("\\*\\*(.*?)\\*\\*", "*$1*");  // **текст** -> *текст*
        messageText = messageText.replaceAll("__(.*?)__", "*$1*");          // __текст__ -> *текст*

        // Заменяем форматирование для курсива (*текст* или _текст_)
        messageText = messageText.replaceAll("\\*(.*?)\\*", "_$1_");        // *текст* -> _текст_
        messageText = messageText.replaceAll("_(.*?)_", "_$1_");            // _текст_ -> _текст_

        // Заменяем зачеркнутый текст (~~текст~~)
        messageText = messageText.replaceAll("~~(.*?)~~", "~$1~");          // ~~текст~~ -> ~текст~

        // Заменяем моноширинный текст (код) (`текст`)
        messageText = messageText.replaceAll("`([^`]*)`", "`$1`");          // `текст` -> `текст`

        // Заменяем заголовки (например, ## Заголовок 2)
        messageText = messageText.replaceAll("### (.*?)\n", "*$1*\n");       // ## Заголовок 2 -> *Заголовок 2*
        messageText = messageText.replaceAll("## (.*?)\n", "*$1*\n");       // ## Заголовок 2 -> *Заголовок 2*
        messageText = messageText.replaceAll("# (.*?)\n", "*$1*\n");        // # Заголовок 1 -> *Заголовок 1*

        // Заменяем цитаты ( > Цитата)
        messageText = messageText.replaceAll("^> (.*)$", "> _$1_");         // > Цитата -> > _Цитата_

        // Заменяем горизонтальные линии на пустую строку (---)
        messageText = messageText.replaceAll("---", "\n");
        return messageText;
    }
}
