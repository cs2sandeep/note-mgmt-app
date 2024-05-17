package travel.onarrival.notes.controllers;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import travel.onarrival.notes.entities.Note;
import travel.onarrival.notes.services.NoteService;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/notes")
public class NoteRestController {

    private final NoteService noteService;

    public NoteRestController(NoteService noteService) {
        this.noteService = noteService;
    }

    @GetMapping
    public List<Note> getNotes() {
        return noteService.findAllNotes();
    }

    @GetMapping("{id}")
    public ResponseEntity<Note> getNote(@PathVariable Long id) {
        return ResponseEntity.of(noteService.findNoteById(id));
    }

    @PostMapping
    public ResponseEntity<?> createNote(@RequestBody Note note) {
        try {
            Note n = noteService.saveNote(note);
            URI location = ServletUriComponentsBuilder.fromCurrentRequestUri()
                    .path("/{id}")
                    .buildAndExpand(n.getId())
                    .toUri();
            return ResponseEntity.created(location).body(n);
        } catch (ConstraintViolationException e) {
            Map<String, String> errorDetails = new HashMap<>();
            for (ConstraintViolation<?> violation : e.getConstraintViolations()) {
                errorDetails.put(violation.getPropertyPath().toString(), violation.getMessage());
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDetails);
        } catch (Exception e) {
            Map<String, String> errorDetails = new HashMap<>();
            errorDetails.put("message", "Failed to create note");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorDetails);
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<?> updateNote(@PathVariable Long id, @RequestBody Note note) {
        return noteService.findNoteById(id)
                .map(n -> {
                    try {
                        if (note.getTitle() != null) {
                            n.setTitle(note.getTitle());
                        }
                        if (note.getContent() != null) {
                            n.setContent(note.getContent());
                        }
                        return ResponseEntity.ok(noteService.saveNote(n));
                    } catch (ConstraintViolationException e) {
                        Map<String, String> errorDetails = new HashMap<>();
                        for (ConstraintViolation<?> violation : e.getConstraintViolations()) {
                            errorDetails.put(violation.getPropertyPath().toString(), violation.getMessage());
                        }
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDetails);
                    }
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteNote(@PathVariable Long id) {
        return noteService.findNoteById(id)
                .map(n -> {
                    noteService.deleteNoteById(id);
                    return ResponseEntity.noContent().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
