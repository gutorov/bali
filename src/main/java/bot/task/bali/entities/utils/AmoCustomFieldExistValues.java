package bot.task.bali.entities.utils;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

import java.util.List;

@Data
public class AmoCustomFieldExistValues {
    @SerializedName("id")
    private Long id;
    @SerializedName("name")
    private String name;
    @SerializedName("enums")
    private List<AmoCustomFieldValueExistValue> values;
}
