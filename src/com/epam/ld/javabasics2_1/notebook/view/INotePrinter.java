package com.epam.ld.javabasics2_1.notebook.view;

import com.epam.ld.javabasics2_1.notebook.entity.Note;
import com.epam.ld.javabasics2_1.notebook.entity.NoteBook;

import java.util.List;

public interface INotePrinter {

    public void print(NoteBook noteBook);
    public void print(List<Note> notes);
    public void print(Note note);

}
