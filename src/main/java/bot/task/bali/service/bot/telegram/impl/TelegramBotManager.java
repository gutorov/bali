package bot.task.bali.service.bot.telegram.impl;

import bot.task.bali.service.bot.telegram.api.GetterFilePath;
import jakarta.annotation.PostConstruct;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramWebhookBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.GetFile;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.*;
import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

@Component
@Log4j2
class TelegramBotManager extends TelegramWebhookBot implements GetterFilePath {

    @Value("${bot.name}")
    private String botName;

    @Value("${bot.token}")
    private String botToken;

    @Value("${bot.uri}")
    private String botUri;

    @PostConstruct
    public void init() {
        List<BotCommand> listOfCommands = new ArrayList<>();
        listOfCommands.add(new BotCommand("/start", "very begin"));
        try {
            this.execute(new SetMyCommands(listOfCommands, new BotCommandScopeDefault(), null));
        } catch (TelegramApiException e) {
            log.error("Error setting bot's command list: " + e.getMessage());
            e.printStackTrace();
        }

        try {
            var setWebhook = SetWebhook.builder().url(botUri).build();
            this.setWebhook(setWebhook);
            log.debug("botRegistrated");
            log.debug(
                    "\nbotUri : "
                            + botUri
                            + "\nbotName : "
                            + botName
                            + "\nbot Token : "
                            + botToken);
        } catch (TelegramApiException e) {
            log.error(e);
            e.printStackTrace();
        }
    }

    @Override
    public String getBotUsername() {
        return botName;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    @Override
    public String getBotPath() {
        return "/update";
    }

    public void sendAnswerMessage(SendMessage message) {
        if (message != null) {
            try {
                message.setParseMode("HTML");
                message.setDisableWebPagePreview(true);
                execute(message);
            } catch (TelegramApiException e) {
                log.error(e);
            }
        }
    }

    @Override
    public BotApiMethod<?> onWebhookUpdateReceived(Update update) {
        log.debug("web hook called and send null");
        return null;
    }

    @Override
    public String getPath(String fileId) {
        try {
            GetFile getFile = new GetFile(fileId);
            var file = execute(getFile);
            String filePath = file.getFilePath();

            return "https://api.telegram.org/file/bot" + botToken + "/" + filePath;
        } catch (TelegramApiException e) {
            throw new RuntimeException("Failed to get file path", e);
        }
    }
}
