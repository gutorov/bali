package bot.task.bali.service.amo;

import bot.task.bali.entities.amo.AmoColumnQualification;

public interface AmoApiColumnMover {
    boolean moveUser(Long amoLeadId, AmoColumnQualification amoColumn);
}
