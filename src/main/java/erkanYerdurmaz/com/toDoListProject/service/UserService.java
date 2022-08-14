package erkanYerdurmaz.com.toDoListProject.service;

import erkanYerdurmaz.com.toDoListProject.entity.User;
import erkanYerdurmaz.com.toDoListProject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface UserService {

    User saveUser(User user);

    Optional<User> findUserBy(String id);

    boolean checkUserExist(String email, String password);

    User getOneUserByUserName(String userName);

    @Service
    @RequiredArgsConstructor
    class DefaultUserService implements UserService {

        private final UserRepository userRepository;

        @Override
        public User saveUser(User user) {
            return userRepository.save(user);
        }

        @Override
        @Transactional(readOnly = true)
        public Optional<User> findUserBy(String id) {
            return userRepository.findById(id);
        }

        @Override
        @Transactional(readOnly = true)
        public boolean checkUserExist(String email, String password) {
            return userRepository.existsByEmailAndPassword(email, password);
        }

        @Override
        public User getOneUserByUserName(String userName) {
            return userRepository.findByUserLastName(userName);
        }
    }
}
