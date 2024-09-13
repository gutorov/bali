package bot.task.bali.service.ai.assistant;

import bot.task.bali.entities.utils.AmoCustomFieldExistValues;
import bot.task.bali.entities.utils.AmoCustomFieldValueExistValue;
import dev.langchain4j.data.message.ChatMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;

import java.util.List;

public interface ServiceAssistant {
//    @SystemMessage("You are a professional translator into {{language}}")
//    @UserMessage("Translate the following text: {{text}}")
//    String translate(@V("text") String text, @V("language") String language);
//
//    @SystemMessage("Summarize every message from user in {{n}} bullet points. Provide only bullet points.")
//    List<String> summarize(@UserMessage String text, @V("n") int n);

//    @UserMessage("Определи какой язык используется клиентов в {{listMessages}} и верни один поле, которому соответсвует. Варианты выбора приведены в {{existValues.values()}}")
//    AmoCustomFieldValueExistValue searchLanguage(@V("listMessages")List<ChatMessage> listMessages, @V("existValues") List<AmoCustomFieldExistValues> existValues);
//
//    @UserMessage("Я тебе предоставлю историю сообщений: {{listMessages}} и тебе нужно определить один из вариантов {{existValues.values()}} и вернуть")
//    AmoCustomFieldValueExistValue summarize(@V("listMessages")List<ChatMessage> listMessages);

    @UserMessage("Определи какое значение в {{listMessages}} и верни один поле, которому варианту соответсвует. " +
            "Варианты выбора приведены в {{existValues}}" +
            "Дополнительная информация о чем идет речь описано в {{info}}")
    AmoCustomFieldValueExistValue searchVals(@V("listMessages")List<ChatMessage> listMessages,
                                             @V("existValues") AmoCustomFieldExistValues existValues,
                                             @V("info") String info);
}
