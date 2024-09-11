package bot.task.bali.service.ai.assistant;

import bot.task.bali.repo.appuser.AppUserSaver;
import bot.task.bali.repo.appuser.GetterUserById;
import dev.langchain4j.data.message.ChatMessage;
import dev.langchain4j.data.message.SystemMessage;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.store.memory.chat.ChatMemoryStore;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Log4j2
@Component
public class PersistentChatMemoryStore implements ChatMemoryStore, AdderSystemMessageToChat {

    private final GetterUserById getterUserById;
    private final AppUserSaver userSaver;

    @Override
    public List<ChatMessage> getMessages(Object key) {
        var user = getterUserById.getUserById((UUID) key);
        return user.getChatMessages();
    }

    @Override
    public void updateMessages(Object key, List<ChatMessage> list) {
        var user = getterUserById.getUserById((UUID) key);
        user.setChatMessages(list);
        userSaver.save(user);
    }

    @Override
    public void deleteMessages(Object key) {
        var user = getterUserById.getUserById((UUID) key);
        user.setChatMessages(new ArrayList<>());
        userSaver.save(user);
        log.warn("Deleted messages from user {}", user.getId());
    }

    /**
     * @param key userId
     * @param message добавочное systemMessage сообщение
     */
    @Override
    public void addMessageToChat(UUID key, String message) {
        var user = getterUserById.getUserById(key);
        var chatMessages = user.getChatMessages();
        if (chatMessages.isEmpty()) return;

        int index = -1;
        for(int i = 1; i < chatMessages.size(); i++) {
            var chatMessage = chatMessages.get(i);
            if (chatMessage instanceof SystemMessage converted) {
                if (converted.text().equals(message)){
                    return;
                }
                index = i;
                break;
            }
        }

        if (index != -1) {
            chatMessages.remove(index);
        } else if (chatMessages.size() > 10) {
            chatMessages.remove(1);
        }

        ChatMessage systemMessage = SystemMessage.from(message);
        chatMessages.add(1, systemMessage);
        user.setChatMessages(new ArrayList<>(chatMessages));
        userSaver.save(user);
    }
}
