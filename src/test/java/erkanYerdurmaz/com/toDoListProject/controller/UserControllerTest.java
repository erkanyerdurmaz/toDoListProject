package erkanYerdurmaz.com.toDoListProject.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import erkanYerdurmaz.com.toDoListProject.converter.impl.UserConverter;
import erkanYerdurmaz.com.toDoListProject.dto.UserDto;
import erkanYerdurmaz.com.toDoListProject.entity.User;
import erkanYerdurmaz.com.toDoListProject.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    UserService userService;

    @MockBean
    UserConverter userConverter;

    @Test
    void shouldReturnSuccessWhenRequestedPostUser() throws Exception {
        UserDto requestBody = UserDto.builder()
                .firstName("firstName")
                .lastName("lastName")
                .email("email")
                .password("password")
                .build();

        User user = new User();
        user.setId("userId");

        when(userService.saveUser(any())).thenReturn(user);

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(requestBody)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(header().string("location", "http://localhost/api/users/userId"));
    }

    @Test
    void shouldReturnBadRequestWhenRequestedPostUserIfEmailIsEmpty() throws Exception {
        UserDto requestBody = UserDto.builder()
                .firstName("firstName")
                .lastName("lastName")
                .build();

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(requestBody)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnUserWhenRequestedGetUser() throws Exception {
        User user = new User();
        user.setId("userId");
        user.setFirstName("firstName");
        user.setLastName("lastName");
        user.setEmail("email");

        when(userService.findUserBy("userId")).thenReturn(Optional.of(user));
        when(userConverter.to(user)).thenCallRealMethod();

        mockMvc.perform(get("/api/users/userId", "userId", "userId")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("userId"));
    }

    @Test
    void shouldReturnNotFoundWhenRequestedGetUserIfUserDoesNotExist() throws Exception {
        when(userService.findUserBy("userId")).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/users/userId", "userId", "userId")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldReturnAcceptedWhenRequestedCheckUserExistIfUserExist() throws Exception {
        when(userService.checkUserExist("email", "password")).thenReturn(true);

        mockMvc.perform(get("/api/users")
                        .param("email", "email")
                        .param("password", "password")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isAccepted());
    }

    @Test
    void shouldReturnNotFoundWhenRequestedCheckUserExistIfUserDoesNotExist() throws Exception {
        when(userService.checkUserExist("email", "password")).thenReturn(false);

        mockMvc.perform(get("/api/users")
                        .param("email", "email")
                        .param("password", "password")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

}