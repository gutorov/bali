package bot.task.bali.entities.utils;

import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class AmoCustomFieldValue {
    private String value;
    @SerializedName("enum_id")
    private Long id;
}
