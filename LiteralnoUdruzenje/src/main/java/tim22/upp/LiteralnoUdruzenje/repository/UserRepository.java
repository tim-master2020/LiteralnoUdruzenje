package tim22.upp.LiteralnoUdruzenje.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tim22.upp.LiteralnoUdruzenje.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
    User save(User user);
    User findByUsername(String username);
}
