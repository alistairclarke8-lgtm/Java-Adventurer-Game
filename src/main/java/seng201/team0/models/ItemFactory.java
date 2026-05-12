package seng201.team0.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Factory for the pool of items available in the game.
 * Returns new lists each time so the market and inventory do not overlap.
 */
public final class ItemFactory {

    private ItemFactory() { }

    /**
     * @return a fresh list of every item that can show up in the market.
     */
    public static List<Item> buildMasterItemPool() {
        List<Item> items = new ArrayList<>();

        // Upgrades. Permanent stat boosts; consumed on use.
        items.add(new UpgradeItem("Endurance Tonic", 25,
                "Permanently raises max stamina by 10.", "stamina", 10));
        items.add(new UpgradeItem("Eagle Eye Tonic", 25,
                "Permanently raises perception by 10.", "perception", 10));
        items.add(new UpgradeItem("Hearty Stew", 40,
                "Permanently raises max stamina by 20.", "stamina", 20));
        items.add(new UpgradeItem("Hawk's Brew", 40,
                "Permanently raises perception by 20.", "perception", 20));

        // Equipables. Re-equippable stat boosts.
        items.add(new EquipableItem("Leather Boots", 20,
                "Worn boots that ease long marches. +5 stamina while equipped.",
                "stamina", 5));
        items.add(new EquipableItem("Spyglass", 20,
                "A brass spyglass for spotting loot. +5 perception while equipped.",
                "perception", 5));
        items.add(new EquipableItem("Plate Armour", 50,
                "Heavy armour. +15 stamina while equipped.",
                "stamina", 15));
        items.add(new EquipableItem("Enchanted Lens", 50,
                "A monocle of farsight. +15 perception while equipped.",
                "perception", 15));

        // Skill tomes. Teach a permanent named skill; consumed on use.
        items.add(new SkillTome("Tome of Vigour", 35,
                "Teaches the Vigour skill (+8 max stamina).",
                new Skill("Vigour", "Boosts endurance.", 8, 0)));
        items.add(new SkillTome("Tome of Sight", 35,
                "Teaches the Sight skill (+8 perception).",
                new Skill("Sight", "Sharpens the senses.", 0, 8)));
        items.add(new SkillTome("Tome of the Veteran", 60,
                "Teaches the Veteran skill (+10 stamina, +5 perception).",
                new Skill("Veteran", "Battle-hardened.", 10, 5)));

        return items;
    }
}