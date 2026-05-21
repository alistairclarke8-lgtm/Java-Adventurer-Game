package seng201.team0.exception;

/**
 * Thrown when the guild attempts to pay for something but does not have enough gold.
 */
public class InsufficientGoldException extends Exception {
    /**
     * Message from the price owed and the gold on hand.
     * @param price gold required for the transaction
     * @param goldHeld gold currently in the guild's treasury
     */
    public InsufficientGoldException(int price, int goldHeld) {
        super("Not enough gold: need " + price + ", have " + goldHeld + ".");
    }

    /**
     * Constructs a new exception with the given detail message.
     * @param message human-readable description, typically including the
     *                amount required and the amount held
     */
    public InsufficientGoldException(String message) {
        super(message);
    }

}
