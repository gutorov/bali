package bot.task.bali.service.app;

import org.springframework.web.multipart.MultipartFile;

public interface UploadFileService {
    boolean uploadFile(MultipartFile file);
}
