package com.interview.notetransposer;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class NoteTransposerTest {

    @Test
    public void testTransposeNotesUp() {
        List<List<Integer>> originalNotes = Arrays.asList(
            Arrays.asList(2, 1),  // C in 2nd octave
            Arrays.asList(2, 6)   // F in 2nd octave
        );
        int semitones = 2;  // Transpose up by 2 semitones
        
        List<List<Integer>> expected = Arrays.asList(
            Arrays.asList(2, 3),  // D in 2nd octave
            Arrays.asList(2, 8)   // G in 2nd octave
        );

        List<List<Integer>> transposed = NoteTransposer.transposeNotes(originalNotes, semitones);
        assertEquals(expected, transposed);
    }

    @Test
    public void testTransposeNotesDown() {
        List<List<Integer>> originalNotes = Arrays.asList(
            Arrays.asList(2, 1),  // C in 2nd octave
            Arrays.asList(2, 6)   // F in 2nd octave
        );
        int semitones = -1;  // Transpose down by 1 semitone
        
        List<List<Integer>> expected = Arrays.asList(
            Arrays.asList(1, 12), // B in 1st octave
            Arrays.asList(2, 5)   // E in 2nd octave
        );

        List<List<Integer>> transposed = NoteTransposer.transposeNotes(originalNotes, semitones);
        assertEquals(expected, transposed);
    }

    @Test
    public void testInvalidJsonInput() {
        // Simulating the reading of an invalid JSON format
        String invalidJson = "[[2,1], [3,]";
        assertThrows(Exception.class, () -> {
            List<List<Integer>> notes = NoteTransposer.parseNotes(invalidJson);
        }, "Should throw an exception due to invalid JSON");
    }

    @Test
    public void testNotesOutOfRange() {
        List<List<Integer>> originalNotes = List.of(
                Arrays.asList(5, 12)  // Last valid note
        );
        int semitones = 1;  // Transpose up by 1 semitone, which should make it invalid

        Exception exception = assertThrows(RuntimeException.class, () -> {
            NoteTransposer.transposeNotes(originalNotes, semitones);
        });
        assertTrue(exception.getMessage().contains("Note out of range"));
    }

    @Test
    public void testExcessiveSemitones() {
        List<List<Integer>> originalNotes = List.of(
                Arrays.asList(2, 11)  // Note close to the upper limit
        );
        int semitones = 100;  // Unrealistically high number of semitones

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            NoteTransposer.transposeNotes(originalNotes, semitones);
        });
        assertTrue(exception.getMessage().contains("Note out of range"));
    }

    @Test
    public void testFileNotFound() {
        assertThrows(Exception.class, () -> {
            List<List<Integer>> notes = NoteTransposer.readNotesFromFile("nonexistent/path/a.json");
        }, "Should throw an exception because file does not exist");
    }

    @Test
    public void testFailedOutputFileWrite() {
        List<List<Integer>> transposedNotes = Arrays.asList(
                Arrays.asList(2, 3), Arrays.asList(2, 4)
        );
        assertThrows(Exception.class, () -> {
            NoteTransposer.writeNotesToFile(transposedNotes, "nonexistent/path/b.json");
        }, "Should throw an exception because output directory does not exist");
    }
}
