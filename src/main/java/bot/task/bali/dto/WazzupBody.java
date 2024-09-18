package bot.task.bali.dto;


import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WazzupBody {

    @NotBlank
    private String channelId;
    @NotBlank
    private String chatType;
    @NotBlank
    private String chatId;
    @NotBlank
    private String text;

    public WazzupBody copy() {
        return WazzupBody.builder()
                .channelId(channelId)
                .chatType(chatType)
                .chatId(chatId)
                .text(text)
                .build();
    }
}
