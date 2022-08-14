package erkanYerdurmaz.com.toDoListProject.service;

import erkanYerdurmaz.com.toDoListProject.entity.User;
import erkanYerdurmaz.com.toDoListProject.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    UserService.DefaultUserService userService;

    @Mock
    UserRepository userRepository;

    @Captor
    ArgumentCaptor<User> userCaptor;

    @Test
    void shouldSaveUser() {
        User user = new User();

        userService.saveUser(user);

        verify(userRepository).save(userCaptor.capture());
        User verifiedUser = userCaptor.getValue();

        assertEquals(verifiedUser, user);
    }

    @Test
    void shouldReturnUserWhenExist() {
        User user = new User();

        when(userRepository.findById("userId")).thenReturn(Optional.of(user));

        Optional<User> result = userService.findUserBy("userId");

        assertTrue(result.isPresent());
        assertEquals(result.get(), user);
    }

    @Test
    void shouldNotReturnUserWhenDoesNotExist() {
        when(userRepository.findById("userId")).thenReturn(Optional.empty());

        Optional<User> result = userService.findUserBy("userId");

        assertFalse(result.isPresent());
    }

    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    void shouldReturnExistenceOfUser(boolean userExist) {
        when(userRepository.existsByEmailAndPassword("email", "password")).thenReturn(userExist);

        boolean result = userService.checkUserExist("email", "password");

        assertThat(result, is(userExist));
    }
}