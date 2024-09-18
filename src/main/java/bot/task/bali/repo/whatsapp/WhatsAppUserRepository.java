package bot.task.bali.repo.whatsapp;

import bot.task.bali.entities.WhatsAppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

interface WhatsAppUserRepository extends JpaRepository<WhatsAppUser, UUID> {
    Optional<WhatsAppUser> findByChatId(String chatId);
}
