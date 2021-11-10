package com.epam.ld.javabasics2_1.notebook.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class NoteBook {

    private String filename = null;
    private List<Note> notes = new ArrayList<Note>();

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public List<Note> getNotes() {
        return notes;
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NoteBook noteBook = (NoteBook) o;
        return Objects.equals(getFilename(), noteBook.getFilename()) && getNotes().equals(noteBook.getNotes());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getFilename(), getNotes());
    }

}
