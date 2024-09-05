package bot.task.bali.repo.appuser;

import bot.task.bali.entities.AppUser;
import bot.task.bali.entities.Status;
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

        try {
            var opt = appUserRepository.findById(uuid);
            if (opt.isPresent()) {
                return opt.get();
            }else {
                var user = AppUser.builder().build();
                return appUserRepository.save(user);
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to save user: " + uuid, e);
        }
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
