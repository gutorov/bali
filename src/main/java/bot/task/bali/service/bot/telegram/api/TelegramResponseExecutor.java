package bot.task.bali.service.bot.telegram.api;

public interface TelegramResponseExecutor {
    void execute(Long chatId, String message);
}
