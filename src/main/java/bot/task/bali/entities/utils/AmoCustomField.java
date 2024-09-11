package bot.task.bali.entities.utils;

import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class AmoCustomField {
    @SerializedName("field_id")
    private Long id;
    @SerializedName("field_name")
    private String name;
    private List<AmoCustomFieldValue> values;
}
