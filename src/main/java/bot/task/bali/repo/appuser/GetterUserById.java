package bot.task.bali.repo.appuser;

import bot.task.bali.entities.AppUser;

import java.util.UUID;

public interface GetterUserById {

    AppUser getUserById(UUID uuid);
    default AppUser getUserById(String id){
        return getUserById(UUID.fromString(id));
    }

    AppUser getByAmoLeadId(Long id);
    default AppUser getByAmoLeadId(String id) {
        return getByAmoLeadId(Long.parseLong(id));
    }
}
