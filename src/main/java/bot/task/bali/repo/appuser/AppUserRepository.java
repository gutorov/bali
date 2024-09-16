package bot.task.bali.repo.appuser;

import bot.task.bali.entities.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
interface AppUserRepository extends JpaRepository<AppUser, UUID> {
    Optional<AppUser> findById(UUID id);
    Optional<AppUser> findByAmoCrmLeadId(Long id);
}
