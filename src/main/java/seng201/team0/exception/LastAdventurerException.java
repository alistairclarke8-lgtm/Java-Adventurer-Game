package seng201.team0.exception;

/** Thrown when retiring last adventurer from guild. */

public class LastAdventurerException extends Exception {

    /**
     * Calls the superclass Exception with the human-readable message
     */
    public LastAdventurerException() {
        super("Cannot retire the last adventurer.");
    }
}