package bot.task.bali.service.app;

import dev.ai4j.openai4j.embedding.EmbeddingModel;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class FileService implements UploadFileService  {
    @Override
    public boolean uploadFile(MultipartFile file) {
//        EmbeddingModel
        return false;
    }
}
