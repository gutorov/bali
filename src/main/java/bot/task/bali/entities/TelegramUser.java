package bot.task.bali.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;


@Getter
@Setter
@Entity
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class TelegramUser extends AppUser {

    @EqualsAndHashCode.Include
    private Long chatId;
}