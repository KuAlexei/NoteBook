package com.epam.ld.javabasics2_1.notebook.service;

import com.epam.ld.javabasics2_1.notebook.entity.Note;
import com.epam.ld.javabasics2_1.notebook.entity.NoteBook;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class NoteBookService {

    private long nextNoteId = 1;
    private final NoteBook noteBook;

    public NoteBookService(NoteBook noteBook) {
        this.noteBook = noteBook;
        nextNoteId += noteBook.getNotes().stream().mapToLong(n -> n.getId()).max().orElse(0);
    }


    public Note getNoteById(long id) {
        return noteBook.getNotes().stream().filter(n -> n.getId() == id).findFirst().orElse(null);
    }

    public List<Note> getNotesForDay(LocalDate date) {
        return (List<Note>) noteBook.getNotes().stream().filter(n -> n.getDateTime().toLocalDate().isEqual(date)).collect(Collectors.toList());
    }

    public List<Note> getAllNotes() {
        return Collections.unmodifiableList(noteBook.getNotes());
    }

    public Note addNote(String record) {
        return addNote(null, record);
    }

    public Note addNote(LocalDateTime dateTime, String record) {
        if (record == null) return null;
        if (dateTime == null) {
            dateTime = LocalDateTime.now();
        }
        Note aNewNote = new Note(nextNoteId++, dateTime, record);
        noteBook.getNotes().add(aNewNote);
        return aNewNote;
    }

    public boolean deleteNote(Note note) {
        return noteBook.getNotes().remove(note);
    }

    public boolean deleteNote(long id) {
        Note noteToDelete = getNoteById(id);
        return deleteNote(noteToDelete);
    }

    public void deleteAllNotes() {
        noteBook.getNotes().clear();
        nextNoteId = 1;
    }

}
