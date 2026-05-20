package seng201.team0.exception;

/** Thrown when an operation is attempted on an empty main party. */
public class EmptyPartyException extends Exception {

    /** @param message detail message */
    public EmptyPartyException(String message) { super(message); }
}