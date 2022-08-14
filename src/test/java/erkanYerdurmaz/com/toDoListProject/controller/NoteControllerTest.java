
package erkanYerdurmaz.com.toDoListProject.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import erkanYerdurmaz.com.toDoListProject.converter.impl.NoteConverter;
import erkanYerdurmaz.com.toDoListProject.dto.NoteDto;
import erkanYerdurmaz.com.toDoListProject.entity.Note;
import erkanYerdurmaz.com.toDoListProject.service.NoteService;
import org.assertj.core.util.Lists;
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
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(NoteController.class)
class NoteControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    NoteService noteService;

    @MockBean
    NoteConverter noteConverter;

    @Test
    void shouldReturnSuccessWhenRequestedPostNote() throws Exception {
        NoteDto requestBody = NoteDto.builder()
                .text("text")
                .build();

        Note note = new Note();
        note.setId("noteId");

        when(noteService.saveNote(any(), eq("userId"))).thenReturn(note);

        mockMvc.perform(post("/api/notes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(requestBody))
                        .header("userId", "userId"))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(header().string("location", "http://localhost/api/notes/noteId"));
    }

    @Test
    void shouldReturnBadRequestWhenRequestedPostNoteIfTextIsEmpty() throws Exception {
        NoteDto requestBody = NoteDto.builder().build();

        mockMvc.perform(post("/api/notes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(requestBody))
                        .header("userId", "userId"))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnBadRequestWhenRequestedPostNoteIfUserIdIsEmpty() throws Exception {
        NoteDto requestBody = NoteDto.builder()
                .text("text")
                .build();

        mockMvc.perform(post("/api/notes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(requestBody)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnSuccessWhenRequestedGetNote() throws Exception {
        Note note = new Note();
        note.setId("noteId");
        note.setText("text");
        note.setUserId("userId");

        when(noteService.findNoteBy("noteId")).thenReturn(Optional.of(note));
        when(noteConverter.to(note)).thenCallRealMethod();

        mockMvc.perform(get("/api/notes/noteId", "noteId", "noteId")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("noteId"))
                .andExpect(jsonPath("$.text").value("text"));
    }

    @Test
    void shouldReturnNotFoundWhenRequestGetNoteIfNoteDoesNotExist() throws Exception {
        when(noteService.findNoteBy("noteId")).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/notes/noteId", "noteId", "noteId")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldReturnSuccessWhenRequestedGetNotesByUserId() throws Exception {
        Note note1 = new Note();
        note1.setText("text1");

        Note note2 = new Note();
        note2.setText("text2");

        when(noteService.findByUserId("userId")).thenReturn(Lists.newArrayList(note1, note2));
        when(noteConverter.to(note1)).thenCallRealMethod();
        when(noteConverter.to(note2)).thenCallRealMethod();

        mockMvc.perform(get("/api/notes/userId/{userId}", "userId", "userId")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].text").value("text1"))
                .andExpect(jsonPath("$[1].text").value("text2"));
    }
}