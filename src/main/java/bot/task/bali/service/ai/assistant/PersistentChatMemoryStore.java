package bot.task.bali.service.ai.assistant;

import bot.task.bali.repo.appuser.AppUserSaver;
import bot.task.bali.repo.appuser.GetterUserById;
import dev.langchain4j.data.message.ChatMessage;
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
public class PersistentChatMemoryStore implements ChatMemoryStore {

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
        log.debug("Delete messages from user {}", user.getId());
        user.setChatMessages(new ArrayList<>());
        userSaver.save(user);
    }
}
