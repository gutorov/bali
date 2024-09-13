package bot.task.bali.dto;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class UserDTO {
    private UUID appUserId;
    private Long amoLeadId;
}
