package erkanYerdurmaz.com.toDoListProject.service;

import erkanYerdurmaz.com.toDoListProject.entity.Note;
import erkanYerdurmaz.com.toDoListProject.repository.NoteRepository;
import erkanYerdurmaz.com.toDoListProject.repository.UserRepository;
import erkanYerdurmaz.com.toDoListProject.service.exception.NoteCreationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface NoteService {

    Note saveNote(Note note, String userId);

    Optional<Note> findNoteBy(String id);

    List<Note> findByUserId(String userId);

    @Service
    @RequiredArgsConstructor
    class DefaultNoteService implements NoteService {

        private final UserRepository userRepository;
        private final NoteRepository noteRepository;

        @Override
        public Note saveNote(Note note, String userId) {
            boolean checkExistUser = userRepository.existsById(userId);
            if (!checkExistUser) throw new NoteCreationException();

            note.setUserId(userId);
            return noteRepository.save(note);
        }

        @Override
        @Transactional(readOnly = true)
        public Optional<Note> findNoteBy(String id) {
            return noteRepository.findById(id);
        }

        @Override
        @Transactional(readOnly = true)
        public List<Note> findByUserId(String userId) {
            return noteRepository.findByUserId(userId);
        }
    }
}
