package erkanYerdurmaz.com.toDoListProject.controller;

import erkanYerdurmaz.com.toDoListProject.dto.NoteDto;
import erkanYerdurmaz.com.toDoListProject.converter.impl.NoteConverter;
import erkanYerdurmaz.com.toDoListProject.entity.Note;
import erkanYerdurmaz.com.toDoListProject.service.NoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class NoteController {

    private final NoteService noteService;
    private final NoteConverter noteConverter;

    @PostMapping("/api/notes")
    ResponseEntity postNote(@Valid @RequestBody NoteDto incomingNote, @RequestHeader String userId) {
        Note note = noteConverter.to(incomingNote);
        String noteId = noteService.saveNote(note, userId).getId();

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(noteId)
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping("/api/notes/{id}")
    ResponseEntity getNote(@PathVariable String id) {
        Optional<Note> mayNote = noteService.findNoteBy(id);
        if (!mayNote.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(noteConverter.to(mayNote.get()));
    }

    @GetMapping("/api/notes/userId/{userId}")
    ResponseEntity getNotesByUserId(@PathVariable String userId) {
        List<Note> notes = noteService.findByUserId(userId);
        List<NoteDto> noteDtos = notes.stream().map(noteConverter::to)
                .collect(Collectors.toList());
        return ResponseEntity.ok(noteDtos);
    }
}
