package erkanYerdurmaz.com.toDoListProject;

import erkanYerdurmaz.com.toDoListProject.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class ToDoListProjectApplicationTests {

	@Autowired
	private UserService userService;

	@Test
	void contextLoads() {
		assertNotNull(userService);
	}

}
