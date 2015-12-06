package pl.maciejczekp.expense.tracker.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.maciejczekp.expense.tracker.model.AppUser;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<AppUser, String> {
    Optional<AppUser> findOneByEmail(String email);
}
