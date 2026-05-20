package seng201.team0.exception;

/**
 * Thrown when player does not select an option, the guild name is not between 3 and 15 characters
 * or it includes special characters.
 */
public class SetupValidationException extends Exception{

    /**
     * @param message the human-readable message passed to the super class, Exception
     */
    public SetupValidationException(String message) {
        super(message);
    }

}
