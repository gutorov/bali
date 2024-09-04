package bot.task.bali.repo.appuser;

import bot.task.bali.entities.AppUser;
import bot.task.bali.exception.UserNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@AllArgsConstructor
public class AppUserRepoManager implements GetterUserById, AppUserSaver {

    private final AppUserRepository appUserRepository;

    @Override
    public AppUser getUserById(UUID uuid) {
        return appUserRepository.findById(uuid)
                .orElseThrow(() -> new UserNotFoundException(uuid));
    }

    @Override
    public void save(AppUser appUser) {
        try {
            appUserRepository.save(appUser);
        } catch (Exception e) {
            throw new RuntimeException("Failed to save user: " + appUser.getId(), e);
        }
    }
}
