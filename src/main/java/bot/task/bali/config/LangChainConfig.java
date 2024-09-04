package bot.task.bali.config;

import bot.task.bali.service.ai.assistant.Assistant;
import bot.task.bali.service.ai.assistant.TrigerToolHandler;
import dev.langchain4j.memory.chat.ChatMemoryProvider;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.store.memory.chat.ChatMemoryStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static java.time.Duration.ofSeconds;

@Configuration
public class LangChainConfig {

    @Bean
    ChatLanguageModel createChatLanguageModel(@Value("${open-ai.chat-model.api-key}") String apiKey) {
        return OpenAiChatModel.builder()
                .apiKey(apiKey)
                .timeout(ofSeconds(60))
                .build();
    }

    @Bean
    Assistant createAssistant(ChatLanguageModel chatLanguageModel, ChatMemoryStore store) {

        ChatMemoryProvider chatMemoryProvider = memoryId -> MessageWindowChatMemory.builder()
                .id(memoryId)
                .maxMessages(10)
                .chatMemoryStore(store)
                .build();

        return AiServices.builder(Assistant.class)
            .chatLanguageModel(chatLanguageModel)
            .chatMemoryProvider(chatMemoryProvider)
            .tools(new TrigerToolHandler())
            .build();
    }
}
