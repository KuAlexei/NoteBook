package com.epam.ld.javabasics2_1.notebook;

import com.epam.ld.javabasics2_1.notebook.entity.Note;
import com.epam.ld.javabasics2_1.notebook.entity.NoteBook;
import com.epam.ld.javabasics2_1.notebook.service.NoteBookService;
import com.epam.ld.javabasics2_1.notebook.view.ConsoleNotePrinter;
import com.epam.ld.javabasics2_1.notebook.view.INotePrinter;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class NoteBookTests {
    @BeforeAll
    static void beforeAll() {
        NoteBook noteBook = NoteBookProvider.getInstance().getNoteBook();
        NoteBookService nbService = new NoteBookService(noteBook);
        nbService.addNote("Single line note!");
        nbService.addNote("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.");
        nbService.addNote(LocalDateTime.of(2000,1,1,0,0,59),"Happy 2000 YEAR!");
    }

    @Test
    void Note() {
        Note n11 = new Note(1,LocalDateTime.of(2000,2,2,2,2,2)," test");
        Note n12 = new Note(1,LocalDateTime.of(2000,2,2,2,2,2)," test");
        Note n13 = new Note(1,LocalDateTime.of(2000,2,2,2,2,2),"test");
        Note n21 = new Note(1,LocalDateTime.now(),"test");
        assertEquals(n11, n12);
        assertNotEquals(n11, n13);
        assertNotEquals(n21, n13);
    }

    @Test
    void NoteBookService() {
        NoteBook noteBook = NoteBookProvider.getInstance().getNoteBook();
        NoteBookService nbService = new NoteBookService(noteBook);
        long size = nbService.getAllNotes().size();
        Note toDelete = nbService.addNote("Test Note to DELETE");
        assertEquals(size + 1, nbService.getAllNotes().size());
        assertEquals(toDelete, nbService.getNoteById(toDelete.getId()));
        assertTrue(nbService.deleteNote(toDelete.getId()));
        assertEquals(size, nbService.getAllNotes().size());
        assertNull(nbService.getNoteById(toDelete.getId()));
        assertNotNull(nbService.getNotesForDay(LocalDate.of(2000,1,1)));
        nbService.deleteAllNotes();
        assertTrue(nbService.getAllNotes().size()==0);
    }

    @Test
    void NoteBookConsoleView() {
        INotePrinter notePrinter = new ConsoleNotePrinter();
        NoteBook noteBook = NoteBookProvider.getInstance().getNoteBook();
        NoteBookService nbService = new NoteBookService(noteBook);
        assertAll("NoteBookConsoleView",
                () -> assertDoesNotThrow(() -> notePrinter.print(noteBook), "Existing Notebook"),
                () -> assertDoesNotThrow(() -> notePrinter.print(new NoteBook()), "Empty Notebook"),
                () -> assertDoesNotThrow(() -> notePrinter.print((NoteBook) null), "Notebook is null"),
                () -> assertDoesNotThrow(() -> notePrinter.print(nbService.getNotesForDay(LocalDate.now())), "Existing List of Notes"),
                () -> assertDoesNotThrow(() -> notePrinter.print(new ArrayList<Note>()), "List of Notes is empty"),
                () -> assertDoesNotThrow(() -> notePrinter.print((List<Note>) null), "List of Notes is null"),
                () -> assertDoesNotThrow(() -> notePrinter.print(nbService.getNotesForDay(LocalDate.of(2000,1,1)).stream().findFirst().get()), "Existing Note"),
                () -> assertDoesNotThrow(() -> notePrinter.print((Note) null), "Note is null")
        );
    }

}