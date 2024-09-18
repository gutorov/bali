package bot.task.bali.repo.telegram;

import bot.task.bali.entities.TelegramUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

interface TelegramUserRepository extends JpaRepository<TelegramUser, UUID> {
    Optional<TelegramUser> findByChatId(Long chatId);
}
