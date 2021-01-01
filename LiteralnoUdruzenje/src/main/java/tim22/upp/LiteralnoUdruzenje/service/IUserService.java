package tim22.upp.LiteralnoUdruzenje.service;

import org.springframework.security.core.userdetails.UserDetails;
import tim22.upp.LiteralnoUdruzenje.model.User;

public interface IUserService {
    User findByEmail(String email);
    User findByUsername(String username);
    User updateUser(User user);
}
