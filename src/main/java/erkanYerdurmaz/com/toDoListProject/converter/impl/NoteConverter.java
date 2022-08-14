package erkanYerdurmaz.com.toDoListProject.converter.impl;

import erkanYerdurmaz.com.toDoListProject.converter.EntityConverter;
import erkanYerdurmaz.com.toDoListProject.dto.NoteDto;
import erkanYerdurmaz.com.toDoListProject.entity.Note;
import org.springframework.stereotype.Component;

@Component
public class NoteConverter implements EntityConverter<NoteDto, Note> {

    @Override
    public NoteDto to(Note note) {
        return NoteDto.builder()
                .id(note.getId())
                .text(note.getText())
                .build();
    }

    @Override
    public Note to(NoteDto noteDto) {
        Note note = new Note();
        note.setId(noteDto.getId());
        note.setText(noteDto.getText());
        return note;
    }
}
