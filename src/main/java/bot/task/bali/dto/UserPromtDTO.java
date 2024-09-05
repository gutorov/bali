package bot.task.bali.dto;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class UserPromtDTO {
    private UUID appUserId;
}
