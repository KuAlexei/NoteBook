package com.epam.ld.javabasics2_1.notebook.service;

import com.epam.ld.javabasics2_1.notebook.entity.Note;
import com.epam.ld.javabasics2_1.notebook.entity.NoteBook;
import com.epam.ld.javabasics2_1.notebook.repository.NoteBookProvider;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class NoteBookService {

    private static final NoteBookProvider PROVIDER = NoteBookProvider.getInstance();

    private long nextNoteId = 1;
    private final NoteBook noteBook;
    private final Integer id;

    public NoteBookService() {
        this(new NoteBook());
    }

    public NoteBookService(NoteBook noteBook) {
        this(PROVIDER.addNoteBook(noteBook));
    }

    public NoteBookService(Integer id) {
        try {
            this.noteBook = PROVIDER.getNoteBook(id);
            this.id = id;
            nextNoteId += noteBook.getNotes().stream().mapToLong(Note::getId).max().orElse(0);
        } catch (IndexOutOfBoundsException ioobe) {
            NoSuchElementException nsee= new NoSuchElementException(String.format("There is no NoteBook with id=%d", id));
            nsee.addSuppressed(ioobe);
            throw nsee;
        }
    }

    public Integer getId() {
        return id;
    }

    public NoteBook getNoteBook() {
        return noteBook;
    }

    public Note getNoteById(long id) {
        return noteBook.getNotes().stream().filter(n -> n.getId() == id).findFirst().orElse(null);
    }

    public List<Note> getNotesForDay(LocalDate date) {
        return noteBook.getNotes().stream().filter(n -> n.getDateTime().toLocalDate().isEqual(date)).collect(Collectors.toList());
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
