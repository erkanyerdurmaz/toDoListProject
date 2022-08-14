package erkanYerdurmaz.com.toDoListProject.controller;

import erkanYerdurmaz.com.toDoListProject.converter.impl.UserConverter;
import erkanYerdurmaz.com.toDoListProject.dto.UserDto;
import erkanYerdurmaz.com.toDoListProject.entity.User;
import erkanYerdurmaz.com.toDoListProject.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;


@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserConverter userConverter;

    @PostMapping("/api/users")
    ResponseEntity postUser(@Valid @RequestBody UserDto userDto) {
        User user = userConverter.to(userDto);
        String userId = userService.saveUser(user).getId();

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(userId)
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping("/api/users/{id}")
    ResponseEntity getUser(@PathVariable String id) {
        Optional<User> mayUser = userService.findUserBy(id);
        if (!mayUser.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(userConverter.to(mayUser.get()));
    }

    @GetMapping("/api/users")
    ResponseEntity checkUserExist(@RequestParam String email, @RequestParam String password) {
        boolean userExist = userService.checkUserExist(email, password);
        if (!userExist) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.accepted().build();
    }
}