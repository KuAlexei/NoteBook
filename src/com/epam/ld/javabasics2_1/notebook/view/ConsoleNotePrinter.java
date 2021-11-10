package com.epam.ld.javabasics2_1.notebook.view;

import com.epam.ld.javabasics2_1.notebook.entity.Note;
import com.epam.ld.javabasics2_1.notebook.entity.NoteBook;

import java.io.PrintStream;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ConsoleNotePrinter implements INotePrinter {

    private final PrintStream output;

    private static final DateTimeFormatter DATE_FMT = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
    private static final int DATE_COL_LEN = 27;
    private static final int NOTE_COL_LEN = 60;

    private static final Pattern PTRN_SPLIT_LINES = Pattern.compile("\\b.{1," + (NOTE_COL_LEN - 3) + "}\\b\\W?");

    private static final String FMT_TABLE_TOP = "\u2550";
    private static final String FMT_TABLE_TOP_LEFT = "\u2554";
    private static final String FMT_TABLE_TOP_CENTER = "\u2566";
    private static final String FMT_TABLE_TOP_RIGHT = "\u2557";
    private static final String FMT_TABLE_BOTTOM = FMT_TABLE_TOP;
    private static final String FMT_TABLE_BOTTOM_LEFT = "\u255A";
    private static final String FMT_TABLE_BOTTOM_CENTER = "\u2569";
    private static final String FMT_TABLE_BOTTOM_RIGHT = "\u255D";
    private static final String FMT_TABLE_LINE = FMT_TABLE_TOP;
    private static final String FMT_TABLE_LINE_LEFT = "\u2560";
    private static final String FMT_TABLE_LINE_CENTER = "\u256C";
    private static final String FMT_TABLE_LINE_RIGHT = "\u2563";
    private static final String FMT_TABLE_COL_LEFT = "\u2551";
    private static final String FMT_TABLE_COL_CENTER = FMT_TABLE_COL_LEFT;
    private static final String FMT_TABLE_COL_RIGHT = FMT_TABLE_COL_LEFT;

    private static final String FMT_NEWLINE = "\n";

    private static final String FMT_HEADER = FMT_TABLE_TOP_LEFT + Stream.generate(() -> FMT_TABLE_TOP).limit(DATE_COL_LEN).collect(Collectors.joining()) + FMT_TABLE_TOP_CENTER + Stream.generate(() -> FMT_TABLE_TOP).limit(NOTE_COL_LEN).collect(Collectors.joining()) + FMT_TABLE_TOP_RIGHT + FMT_NEWLINE;
    private static final String FMT_HEADER_SEPARATOR = FMT_TABLE_LINE_LEFT + Stream.generate(() -> FMT_TABLE_LINE).limit(DATE_COL_LEN).collect(Collectors.joining()) + FMT_TABLE_LINE_CENTER + Stream.generate(() -> FMT_TABLE_LINE).limit(NOTE_COL_LEN).collect(Collectors.joining()) + FMT_TABLE_LINE_RIGHT + FMT_NEWLINE;
    private static final String FMT_LINE_SEPARATOR = FMT_HEADER_SEPARATOR;
    private static final String FMT_LINE = FMT_TABLE_COL_LEFT + String.format(" %%%ds ", DATE_COL_LEN - 2) + FMT_TABLE_COL_CENTER + String.format(" %%-%ds ", NOTE_COL_LEN - 2) + FMT_TABLE_COL_RIGHT + FMT_NEWLINE;
    private static final String FMT_FOOTER = FMT_TABLE_BOTTOM_LEFT + Stream.generate(() -> FMT_TABLE_BOTTOM).limit(DATE_COL_LEN).collect(Collectors.joining()) + FMT_TABLE_BOTTOM_CENTER + Stream.generate(() -> FMT_TABLE_BOTTOM).limit(NOTE_COL_LEN).collect(Collectors.joining()) + FMT_TABLE_BOTTOM_RIGHT + FMT_NEWLINE;


    public ConsoleNotePrinter() {
        this(System.out);
    }

    public ConsoleNotePrinter(PrintStream output) {
        this.output = output;
    }

    @Override
    public void print(Note note) {
        List<Note> noteWrapper = new ArrayList<>();
        if (note != null) {
            noteWrapper.add(note);
        }
        print(noteWrapper);
    }

    @Override
    public void print(NoteBook noteBook) {
        print(noteBook!=null?noteBook.getNotes():(List<Note>) null);
    }

    @Override
    public void print(List<Note> notes) {
        notes = notes==null?new ArrayList<>():notes;
        StringBuffer sb = new StringBuffer()
                // header
                .append(FMT_HEADER)
                .append(String.format(FMT_LINE, "Date", "Note"))
                .append(notes.size()>0?FMT_HEADER_SEPARATOR:"")
                // note
                .append(notes.stream().map(new Function<Note, String>() {
                    @Override
                    public String apply(Note note) {
                        StringBuffer line = new StringBuffer();
                        Matcher m = PTRN_SPLIT_LINES.matcher(note.getRecord());
                        int i = 0;
                        while (m.find()) {
                            line.append(String.format(FMT_LINE, (i++ == 0 ? note.getDateTime().format(DATE_FMT) : ""), m.group()));
                        }
                        return line.toString();
                    }
                }).collect(Collectors.joining(FMT_LINE_SEPARATOR)))
                // footer
                .append(FMT_FOOTER);
        output.print(sb);
    }

}
