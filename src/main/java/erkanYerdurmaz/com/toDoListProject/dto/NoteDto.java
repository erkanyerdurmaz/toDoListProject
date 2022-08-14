package erkanYerdurmaz.com.toDoListProject.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
@Builder
public class NoteDto extends BaseDto {

    private String id;

    @NotEmpty
    private String text;
}
