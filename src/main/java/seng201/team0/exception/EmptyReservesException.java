package seng201.team0.exception;

/** Thrown when an operation is attempted on empty reserves. */
public class EmptyReservesException extends Exception {

    /** @param message detail message */
    public EmptyReservesException(String message) { super(message); }
}