package bot.task.bali.repo.files;

import bot.task.bali.entities.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface FileRepository extends JpaRepository<FileEntity, UUID> {

}
