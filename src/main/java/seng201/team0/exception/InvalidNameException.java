package seng201.team0.exception;

/**
 * Thrown when a proposed guild name fails validation. That is, it is outside
 * the 3–15 character length, or it contains a character that is not
 * a letter, digit, or space.
 */
public class InvalidNameException extends Exception {

    /**
     * Constructs a new exception with the given detail message.
     *
     * @param message human-readable description of why the name is invalid
     */
    public InvalidNameException(String message) {
        super(message);
    }
}
