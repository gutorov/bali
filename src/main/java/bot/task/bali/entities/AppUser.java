package bot.task.bali.entities;

import dev.langchain4j.data.message.ChatMessage;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.GenericGenerator;

import java.util.List;
import java.util.UUID;

import static dev.langchain4j.data.message.ChatMessageDeserializer.messagesFromJson;
import static dev.langchain4j.data.message.ChatMessageSerializer.messagesToJson;

@Getter
@Setter
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name = "app_user")
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class AppUser {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private Status status = Status.COLD;

    @Column(columnDefinition = "TEXT")
    private String chatMessages;

    private Long amoCrmLeadId;

    public List<ChatMessage> getChatMessages() {
        return messagesFromJson(chatMessages);
    }

    public void setChatMessages(List<ChatMessage> chatMessageList) {
        this.chatMessages = messagesToJson(chatMessageList);
    }
}