package bot.task.bali.exception;

import java.util.UUID;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(UUID uuid) {
        super("user with id " + uuid + " not found");
    }
}
