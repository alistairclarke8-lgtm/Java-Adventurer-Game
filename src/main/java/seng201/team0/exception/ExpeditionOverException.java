package seng201.team0.exception;

/**
 * Thrown when an attempt is made to act on an expedition that has
 * already concluded.
 */
public class ExpeditionOverException extends Exception {

    /**
     * Constructs an ExpeditionOverException with a default message.
     */
    public ExpeditionOverException(){super("Expedition is over.");}
}
