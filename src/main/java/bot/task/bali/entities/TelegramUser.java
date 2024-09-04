package bot.task.bali.entities;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@Entity
@Table(name = "telegram_user")
public class TelegramUser extends AppUser {
    
    @EqualsAndHashCode.Include
    private Long chatId;
    private String telegramUserName;
}