package tim22.upp.LiteralnoUdruzenje.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tim22.upp.LiteralnoUdruzenje.model.Role;
import tim22.upp.LiteralnoUdruzenje.model.User;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
    User save(User user);
    User findByUsername(String username);
    List<User> findByRole(Role role);
}
