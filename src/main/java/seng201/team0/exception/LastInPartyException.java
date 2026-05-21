package seng201.team0.exception;

/**
 * Thrown when the last adventurer in the main party is tried to be moved to reserves.
 */
public class LastInPartyException extends Exception{

    public LastInPartyException(String message) {super(message);}
}
