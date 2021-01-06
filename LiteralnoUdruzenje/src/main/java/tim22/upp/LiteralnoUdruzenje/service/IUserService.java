package tim22.upp.LiteralnoUdruzenje.service;

import org.springframework.security.core.userdetails.UserDetails;
import tim22.upp.LiteralnoUdruzenje.model.Role;
import tim22.upp.LiteralnoUdruzenje.model.User;

import java.util.List;

public interface IUserService {
    User findByEmail(String email);
    User findByUsername(String username);
    List<User> findByRole(Role role);
}
