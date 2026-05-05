package seng201.team0.exception;
/**
 * Thrown when the guild cannot accept another adventurer or item because a
 * capacity limit has been reached. At most five adventurers in the main party,
 * at most five in the reserves
 */
public class GuildFullException extends Exception {

    /**
     * Constructs a new exception with the given detail message.
     * @param message human-readable description of which collection is full
     */
    public GuildFullException(String message) {
        super(message);
    }
}