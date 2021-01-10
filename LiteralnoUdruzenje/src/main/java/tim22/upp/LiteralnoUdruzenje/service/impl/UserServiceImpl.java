package tim22.upp.LiteralnoUdruzenje.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import tim22.upp.LiteralnoUdruzenje.model.enums.Role;
import tim22.upp.LiteralnoUdruzenje.model.User;
import tim22.upp.LiteralnoUdruzenje.repository.UserRepository;
import tim22.upp.LiteralnoUdruzenje.service.IUserService;

import java.util.List;

@Service
public class UserServiceImpl implements IUserService, UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = (userRepository.findByUsername(username));

        if(user == null)
            throw new UsernameNotFoundException("User with "+ username+" doesn't exists!");

        return user;
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User updateUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public List<User> findByRole(Role role) {
        return userRepository.findByRole(Role.COMMITTEE);
    }

    @Override
    public void removeUser(String username) {
        if(userRepository.findByUsername(username) != null){
            userRepository.removeByUsername(username);
        }
    }
}
