package bot.task.bali.service.app.converter;

import bot.task.bali.entities.utils.AmoCustomField;
import bot.task.bali.entities.utils.AmoCustomFieldExistValues;

import java.util.List;

public interface ConverterToCustomField {
    List<AmoCustomField> convertToList(String jsonResponse);
    AmoCustomField convert(String jsonResponse);
    AmoCustomFieldExistValues convertExistValues(String jsonResponse);
}
