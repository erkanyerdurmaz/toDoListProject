package erkanYerdurmaz.com.toDoListProject.service.impl;

import erkanYerdurmaz.com.toDoListProject.entity.User;
import erkanYerdurmaz.com.toDoListProject.repository.UserRepository;
import erkanYerdurmaz.com.toDoListProject.security.JwtUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUserLastName(username);
        return JwtUserDetails.create(user);
    }

    public UserDetails loadUserById(String id) {
        User user = userRepository.findById(id).get();
        return JwtUserDetails.create(user);
    }
}
