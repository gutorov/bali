package bot.task.bali.repo.whatsapp;

import bot.task.bali.entities.WazzupUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

interface WhatsAppUserRepository extends JpaRepository<WazzupUser, UUID> {
    Optional<WazzupUser> findByChatId(String chatId);
}
