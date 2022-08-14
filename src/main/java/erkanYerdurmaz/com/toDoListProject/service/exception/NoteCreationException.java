package erkanYerdurmaz.com.toDoListProject.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Failed to create due to lack of required params!")
public class NoteCreationException extends RuntimeException {
}
