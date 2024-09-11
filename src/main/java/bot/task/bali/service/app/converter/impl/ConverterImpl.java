package bot.task.bali.service.app.converter.impl;

import bot.task.bali.entities.utils.AmoCustomField;
import bot.task.bali.entities.utils.AmoCustomFieldExistValues;
import bot.task.bali.service.app.converter.ConverterToCustomField;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

@Log4j2
@Component
class ConverterImpl implements ConverterToCustomField {

    private final Gson gson = new Gson();

    @Override
    public List<AmoCustomField> convertToList(String jsonResponse) {
        try{
            // Используем JsonParser для извлечения только custom_fields_values
            JsonObject jsonObject = JsonParser.parseString(jsonResponse).getAsJsonObject();
            JsonArray customFieldsArray = jsonObject.getAsJsonArray("custom_fields_values");

            // Конвертируем массив custom_fields_values в список CustomField
            Type listType = new TypeToken<List<AmoCustomField>>() {}.getType();
            return gson.fromJson(customFieldsArray, listType);
        }catch (Exception e) {
            log.error(e);
            return new ArrayList<>();
        }
    }

    @Override
    public AmoCustomField convert(String jsonResponse) {
        try{
            // Используем JsonParser для извлечения только custom_fields_values
            JsonObject jsonObject = JsonParser.parseString(jsonResponse).getAsJsonObject();

            // Конвертируем массив custom_fields_values в список CustomField
            return gson.fromJson(jsonObject, AmoCustomField.class);
        }catch (Exception e) {
            log.error(e);
            throw new RuntimeException("Fail to convert json to CustomField");
        }
    }

    @Override
    public AmoCustomFieldExistValues convertExistValues(String jsonResponse) {
        try{
            // Используем JsonParser для извлечения только custom_fields_values
            JsonObject jsonObject = JsonParser.parseString(jsonResponse).getAsJsonObject();

            // Конвертируем массив custom_fields_values в список CustomField
            return gson.fromJson(jsonObject, AmoCustomFieldExistValues.class);
        }catch (Exception e) {
            log.error(e);
            throw new RuntimeException("Fail to convert json to CustomField");
        }
    }
}
