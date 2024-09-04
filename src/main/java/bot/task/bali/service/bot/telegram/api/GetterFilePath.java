package bot.task.bali.service.bot.telegram.api;

import org.telegram.telegrambots.meta.api.objects.Audio;
import org.telegram.telegrambots.meta.api.objects.Voice;

public interface GetterFilePath {

    default String getPath(Voice voice) {
        return getPath(voice.getFileId());
    }

    default String getPath(Audio audio) {
        return getPath(audio.getFileId());
    }

    String getPath(String fileId);
}
