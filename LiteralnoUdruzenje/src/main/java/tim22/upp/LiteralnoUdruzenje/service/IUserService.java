package tim22.upp.LiteralnoUdruzenje.service;

import tim22.upp.LiteralnoUdruzenje.model.enums.Role;
import tim22.upp.LiteralnoUdruzenje.model.User;

import java.util.List;

public interface IUserService {
    User findByEmail(String email);
    User findByUsername(String username);
    User updateUser(User user);
    List<User> findByRole(Role role);
}
