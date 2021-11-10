package com.epam.ld.javabasics2_1.notebook;

import com.epam.ld.javabasics2_1.notebook.entity.NoteBook;

import java.util.ArrayList;
import java.util.List;

public class NoteBookProvider {

    private static NoteBookProvider instance;
    private List<NoteBook> noteBooks = new ArrayList<>();

    private NoteBookProvider() {
        noteBooks.add(new NoteBook());
    }

    public static NoteBookProvider getInstance() {
        if (instance == null) {
            instance = new NoteBookProvider();
        }
        return instance;
    }

    public NoteBook getNoteBook() {
        return getNoteBook(0);
    }

    public NoteBook getNoteBook(int index) {
        return noteBooks.get(index);
    }

}
