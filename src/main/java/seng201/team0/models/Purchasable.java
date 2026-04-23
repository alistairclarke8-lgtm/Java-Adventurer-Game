package seng201.team0.models;

/**
 * Marker interface for anything the player can buy with gold, whether from
 * the guild hall (adventurers) or the market (items).git pull
 */

public interface Purchasable {

    /**
     * @return the display name shown to the player in the shop list
     */
    String getName();

    /**
     * @return the amount of gold required to purchase this object. For
     * adventurers this is the hiring cost; for items this is the
     * shop price
     */
    int getBuyPrice();

    /**
     * @return a short, player-facing description explaining what this
     * purchase does
     */
    String getDescription();


}
