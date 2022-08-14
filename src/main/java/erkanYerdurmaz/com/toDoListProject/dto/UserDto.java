package erkanYerdurmaz.com.toDoListProject.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
@Builder
public class UserDto extends BaseDto {

    private String id;
    private String firstName;
    private String lastName;

    @NotEmpty
    private String email;

    @NotEmpty
    private String password;
}
