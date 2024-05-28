package com.interview.notetransposer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.io.IOException;

public class NoteTransposer {
    private static final int MIN_OCTAVE = -3;
    private static final int MAX_OCTAVE = 5;
    private static final int MIN_NOTE = 1;
    private static final int MAX_NOTE = 12;
    private static final int NOTES_IN_OCTAVE = 12;

    public static void main(String[] args) {
        if (args.length != 3) {
            System.out.println("Usage: java -jar NoteTransposer.jar <inputFile> <semitone> <outputFile>");
            System.exit(1);
        }

        try {
            String inputFile = args[0];
            int semitoneChange = Integer.parseInt(args[1]);
            String outputFile = args[2];

            List<List<Integer>> notes = readNotesFromFile(inputFile);
            List<List<Integer>> transposedNotes = transposeNotes(notes, semitoneChange);

            if (transposedNotes == null) {
                System.out.println("Error: One or more notes out of range after transposition.");
                System.exit(2);
            }

            writeNotesToFile(transposedNotes, outputFile);
            System.out.println("Transposition completed successfully.");
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(3);
        }
    }

    static List<List<Integer>> transposeNotes(List<List<Integer>> notes, int semitoneChange) {
        List<List<Integer>> transposedNotes = new ArrayList<>();
        for (List<Integer> note : notes) {
            int octave = note.get(0);
            int noteNumber = note.get(1) + semitoneChange;

            while (noteNumber > MAX_NOTE) {
                noteNumber -= NOTES_IN_OCTAVE;
                octave++;
            }
            while (noteNumber < MIN_NOTE) {
                noteNumber += NOTES_IN_OCTAVE;
                octave--;
            }

            if (octave < MIN_OCTAVE || (octave == MIN_OCTAVE && noteNumber < 10) ||
                    octave > MAX_OCTAVE || (octave == MAX_OCTAVE && noteNumber > 1)) {
                throw new RuntimeException("Note out of range after transposition.");
            }

            transposedNotes.add(List.of(octave, noteNumber));
        }
        return transposedNotes;
    }


    static List<List<Integer>> readNotesFromFile(String filePath) throws IOException {
        Path path = Paths.get(filePath);
        String jsonInput = Files.readString(path);
        return parseNotes(jsonInput);
    }

    static List<List<Integer>> parseNotes(String json) throws JsonSyntaxException {
        Gson gson = new Gson();
        return gson.fromJson(json, new TypeToken<List<List<Integer>>>() {}.getType());
    }

    static void writeNotesToFile(List<List<Integer>> notes, String filePath) throws IOException {
        Gson prettyGson = new GsonBuilder().setPrettyPrinting().create();
        String prettyJson = prettyGson.toJson(notes);
        Files.writeString(Paths.get(filePath), prettyJson);
    }
}