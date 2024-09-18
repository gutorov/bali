package bot.task.bali.entities;

import jakarta.persistence.Entity;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@Entity
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class WhatsAppUser extends AppUser {
    @EqualsAndHashCode.Include
    private String chatId;
    private String getChatId;
}
