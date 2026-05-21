package seng201.team0.factories;
import seng201.team0.models.Event;
import seng201.team0.models.EventOption;
import seng201.team0.models.EventOutcome;
import seng201.team0.models.Expedition;

import java.util.ArrayList;
import java.util.List;

/**
 * Factory class generating the pool of available expeditions
 * Each expedition has its own event pool with theme-matching prompts,
 * options and weighted outcomes.
 */
public class ExpeditionPool {

    private ExpeditionPool() {}

    /**
     * Builds a fresh list of expeditions available each round
     */
    public static List<Expedition> getAll() {
        return List.of(
                buildRuinedCitadel(),
                buildTradersRoad(),
                buildForgottenMine(),
                buildSunkenTemple(),
                buildFrostpeakPass(),
                buildPlagueVillage(),
                buildShadowMarket(),
                buildBurningWastes(),
                buildAncientLibrary(),
                buildSeaCliffRuins(),
                buildOrcStronghold(),
                buildHauntedMansion(),
                buildDeepForest(),
                buildVolcanicCaverns(),
                buildDesertTomb()
        );
    }

    // -------------------------------------------------------------------------
    // EXPEDITION 1
    // -------------------------------------------------------------------------

    private static Expedition buildRuinedCitadel() {
        List<Event> events = new ArrayList<>();

        events.add(new Event(
                "A group of bandits has set up camp at the citadel's main entrance. " +
                        "They haven't spotted you yet. What's your approach?",
                List.of(
                        new EventOption("Sneak past them",
                                List.of(
                                        new EventOutcome("You slip past undetected and enter the citadel.", 55, 0, 0, false),
                                        new EventOutcome("Partially spotted — you run, losing stamina.", 25, 0, -10, false),
                                        new EventOutcome("Fully caught — a brutal fight breaks out. You retreat.", 20, 0, -15, true)
                                )),
                        new EventOption("Fight through",
                                List.of(
                                        new EventOutcome("You win a fierce battle and take their gold — 20 gold gained.", 60, 20, -12, false),
                                        new EventOutcome("The battle ends in a stalemate. You push through but exhausted.", 30, 0, -18, false),
                                        new EventOutcome("Outnumbered — you lose the fight and retreat.", 10, 0, 0, true)
                                ))
                )
        ));

        events.add(new Event(
                "You find the citadel's old armory. Most weapons are rusted, " +
                        "but something glints in the corner.",
                List.of(
                        new EventOption("Dig through the rubble",
                                List.of(
                                        new EventOutcome("You uncover a stash of 40 gold worth of sellable parts.", 45, 40, 0, false),
                                        new EventOutcome("Just rust. Nothing useful.", 35, 0, -3, false),
                                        new EventOutcome("A section of ceiling collapses. Everyone takes stamina damage.", 20, 0, -12, false)
                                )),
                        new EventOption("Leave it, keep moving",
                                List.of(
                                        new EventOutcome("You move on to the inner keep.", 100, 0, 0, false)
                                ))
                )
        ));

        events.add(new Event(
                "The floor ahead is riddled with pressure plates. " +
                        "One wrong step and the whole passage could collapse.",
                List.of(
                        new EventOption("Carefully pick your way through",
                                List.of(
                                        new EventOutcome("The party navigates the traps successfully.", 65, 0, -5, false),
                                        new EventOutcome("One trap fires — stamina lost but no one is seriously hurt.", 25, 0, -15, false),
                                        new EventOutcome("Multiple traps fire — serious stamina loss.", 10, 0, -25, false)
                                )),
                        new EventOption("Sprint through",
                                List.of(
                                        new EventOutcome("You make it through on luck alone.", 40, 0, -8, false),
                                        new EventOutcome("Traps everywhere — heavy stamina damage.", 60, 0, -20, false)
                                ))
                )
        ));

        events.add(new Event(
                "In the great hall you find the bandit commander counting gold. " +
                        "He spots you. There's no sneaking past this one.",
                List.of(
                        new EventOption("Challenge him to single combat",
                                List.of(
                                        new EventOutcome("Your champion wins — 50 gold seized.", 50, 50, -10, false),
                                        new EventOutcome("A tough fight — you win but barely. 25 gold and heavy stamina cost.", 30, 25, -20, false),
                                        new EventOutcome("You lose the duel. The party is forced to retreat.", 20, 0, 0, true)
                                )),
                        new EventOption("Rush him as a group",
                                List.of(
                                        new EventOutcome("Overwhelming numbers win out — 35 gold.", 70, 35, -12, false),
                                        new EventOutcome("His guards join the fight. You win but it costs you.", 30, 15, -20, false)
                                ))
                )
        ));

        events.add(new Event(
                "The citadel's vault door stands ajar. Inside is more than " +
                        "you expected — but so is the risk of being caught taking it.",
                List.of(
                        new EventOption("Take everything you can carry",
                                List.of(
                                        new EventOutcome("You load up and escape with 60 gold in loot.", 55, 60, 0, false),
                                        new EventOutcome("Reinforcements arrive — you escape with 30 gold but lose stamina.", 30, 30, -15, false),
                                        new EventOutcome("Cut off — you escape empty handed.", 15, 0, -10, false)
                                )),
                        new EventOption("Take only what you can quickly grab",
                                List.of(
                                        new EventOutcome("Quick grab — 25 gold and a clean exit.", 80, 25, 0, false),
                                        new EventOutcome("Still spotted. 25 gold but some stamina lost fleeing.", 20, 25, -8, false)
                                ))
                )
        ));

        events.add(new Event(
                "The eastern tower begins to shake — old masonry crumbling. " +
                        "You need to get out now. How do you exit?",
                List.of(
                        new EventOption("Climb down the outer wall",
                                List.of(
                                        new EventOutcome("You descend safely, making it out before the collapse.", 60, 0, -5, false),
                                        new EventOutcome("A section gives way — heavy stamina loss but you survive.", 40, 0, -18, false)
                                )),
                        new EventOption("Find the stairs",
                                List.of(
                                        new EventOutcome("You find the stairs and exit cleanly.", 75, 0, -8, false),
                                        new EventOutcome("The stairs are partially blocked — takes longer and costs stamina.", 25, 0, -12, false)
                                ))
                )
        ));

        return new Expedition("Ruined Citadel",
                "A crumbling fortress controlled by bandits. High risk, high reward. Adventurers demand 1.5x pay.",
                1.5, 8, events);
    }

    // -------------------------------------------------------------------------
    // EXPEDITION 2
    // -------------------------------------------------------------------------

    private static Expedition buildTradersRoad() {
        List<Event> events = new ArrayList<>();

        events.add(new Event(
                "A merchant's cart has lost a wheel on the road. " +
                        "He's struggling to fix it alone and flags you down.",
                List.of(
                        new EventOption("Help repair the cart",
                                List.of(
                                        new EventOutcome("The merchant rewards you with 30 gold for your help.", 70, 30, -5, false),
                                        new EventOutcome("He has no gold but gives you useful supplies instead.", 20, 10, 0, false),
                                        new EventOutcome("He was a decoy — his partners ambush you. Lose gold and stamina.", 10, -10, -10, false)
                                )),
                        new EventOption("Ignore him and pass by",
                                List.of(
                                        new EventOutcome("You leave him to figure it out himself.", 100, 0, 0, false)
                                ))
                )
        ));

        events.add(new Event(
                "A group of men have set up an unofficial toll on the bridge ahead. " +
                        "Pay up or find another way across.",
                List.of(
                        new EventOption("Pay the toll",
                                List.of(
                                        new EventOutcome("You pay 15 gold and cross without incident.", 100, -15, 0, false)
                                )),
                        new EventOption("Refuse and find a ford",
                                List.of(
                                        new EventOutcome("You find a shallow ford downstream and cross safely.", 70, 0, -8, false),
                                        new EventOutcome("The ford is deeper than it looks — tiring crossing.", 30, 0, -14, false)
                                )),
                        new EventOption("Intimidate them into letting you pass",
                                List.of(
                                        new EventOutcome("They back down and let you cross for free.", 50, 0, 0, false),
                                        new EventOutcome("They call your bluff — a brawl breaks out. Stamina lost.", 50, 0, -12, false)
                                ))
                )
        ));

        events.add(new Event(
                "A cheerful peddler with a loaded mule offers to trade. " +
                        "His prices look fair — maybe even a bargain.",
                List.of(
                        new EventOption("Browse his wares",
                                List.of(
                                        new EventOutcome("You buy a useful item for 10 gold.", 50, -10, 0, false),
                                        new EventOutcome("Nothing catches your eye.", 30, 0, 0, false),
                                        new EventOutcome("He has a rare item — costs 20 gold but worth it.", 20, -20, 0, false)
                                )),
                        new EventOption("Keep moving",
                                List.of(
                                        new EventOutcome("You thank him and move on.", 100, 0, 0, false)
                                ))
                )
        ));

        events.add(new Event(
                "Bandits leap from the tree line. There are only three of them — " +
                        "but they have the element of surprise.",
                List.of(
                        new EventOption("Fight back immediately",
                                List.of(
                                        new EventOutcome("You overpower them quickly and take their coin — 20 gold.", 60, 20, -8, false),
                                        new EventOutcome("A tough scrap. You win but it costs you stamina.", 30, 10, -15, false),
                                        new EventOutcome("They get lucky — you retreat, losing some gold.", 10, -10, -5, false)
                                )),
                        new EventOption("Throw gold to distract them and run",
                                List.of(
                                        new EventOutcome("They scramble for the gold and you escape safely.", 80, -10, 0, false),
                                        new EventOutcome("They pocket the gold and pursue anyway. Stamina lost fleeing.", 20, -10, -10, false)
                                ))
                )
        ));

        events.add(new Event(
                "A busy inn sits at the junction. Hot food and a rest would do " +
                        "the party good, but it'll cost you.",
                List.of(
                        new EventOption("Rest and eat",
                                List.of(
                                        new EventOutcome("A warm meal and rest restores the party's stamina.", 80, -12, 15, false),
                                        new EventOutcome("The food is bad. You paid for nothing useful.", 20, -12, 0, false)
                                )),
                        new EventOption("Just ask for information",
                                List.of(
                                        new EventOutcome("The innkeeper tips you off about a nearby stash — you find 15 gold.", 60, 15, 0, false),
                                        new EventOutcome("The rumour leads nowhere.", 40, 0, 0, false)
                                )),
                        new EventOption("Skip the inn entirely",
                                List.of(
                                        new EventOutcome("You bypass the inn and press on.", 100, 0, 0, false)
                                ))
                )
        ));

        events.add(new Event(
                "A frantic servant stops you — his lord's luggage cart was " +
                        "robbed an hour ago. He's offering a reward for recovery.",
                List.of(
                        new EventOption("Track down the thieves",
                                List.of(
                                        new EventOutcome("You recover the luggage and receive 40 gold reward.", 50, 40, -10, false),
                                        new EventOutcome("Partially recovered — 20 gold reward.", 30, 20, -10, false),
                                        new EventOutcome("The thieves are more organized than expected. You retreat empty handed.", 20, 0, -8, false)
                                )),
                        new EventOption("Decline — not your problem",
                                List.of(
                                        new EventOutcome("You wish him luck and continue on your way.", 100, 0, 0, false)
                                ))
                )
        ));

        return new Expedition("Trader's Road",
                "A well-travelled road between towns. Steadier income with fewer nasty surprises.",
                1.0, 5, events);
    }

    // -------------------------------------------------------------------------
    // EXPEDITION 3
    // -------------------------------------------------------------------------

    private static Expedition buildForgottenMine() {
        List<Event> events = new ArrayList<>();

        events.add(new Event(
                "The mine entrance is partially collapsed. You can squeeze through " +
                        "but it will take effort.",
                List.of(
                        new EventOption("Clear the rubble",
                                List.of(
                                        new EventOutcome("You clear enough rubble to enter safely.", 70, 0, -8, false),
                                        new EventOutcome("The rubble shifts — a minor cave-in costs stamina.", 30, 0, -15, false)
                                )),
                        new EventOption("Squeeze through as-is",
                                List.of(
                                        new EventOutcome("You squeeze through with some effort.", 60, 0, -5, false),
                                        new EventOutcome("Someone gets stuck briefly — costs more stamina than expected.", 40, 0, -12, false)
                                ))
                )
        ));

        events.add(new Event(
                "A pocket of firedamp gas has built up in this section of the mine. " +
                        "One spark and it could ignite.",
                List.of(
                        new EventOption("Move through carefully without torches",
                                List.of(
                                        new EventOutcome("You navigate the dark section without incident.", 65, 0, -5, false),
                                        new EventOutcome("Someone stumbles in the dark — stamina lost.", 35, 0, -12, false)
                                )),
                        new EventOption("Risk it with torches",
                                List.of(
                                        new EventOutcome("You get through before the gas ignites.", 45, 0, -5, false),
                                        new EventOutcome("A small explosion knocks the party back — heavy stamina loss.", 55, 0, -22, false)
                                ))
                )
        ));

        events.add(new Event(
                "An abandoned ore cart sits on the tracks loaded with raw metal. " +
                        "It's heavy but could be worth something.",
                List.of(
                        new EventOption("Push it out to sell",
                                List.of(
                                        new EventOutcome("You haul it out and sell the ore for 35 gold.", 50, 35, -10, false),
                                        new EventOutcome("The tracks give way mid-push — you abandon the cart, exhausted.", 30, 0, -15, false),
                                        new EventOutcome("The cart rolls back and injures someone — stamina lost, no gold.", 20, 0, -18, false)
                                )),
                        new EventOption("Leave it and press deeper",
                                List.of(
                                        new EventOutcome("You push on into the mine.", 100, 0, 0, false)
                                ))
                )
        ));

        events.add(new Event(
                "A group of kobolds has made a nest in the lower tunnels. " +
                        "They're small but numerous.",
                List.of(
                        new EventOption("Fight through them",
                                List.of(
                                        new EventOutcome("You scatter them and find their hoard — 30 gold.", 55, 30, -10, false),
                                        new EventOutcome("More than expected — you win but it costs stamina.", 30, 15, -18, false),
                                        new EventOutcome("Overwhelmed by numbers — you retreat.", 15, 0, -5, true)
                                )),
                        new EventOption("Find a way around",
                                List.of(
                                        new EventOutcome("You locate a secondary tunnel and bypass them.", 60, 0, -8, false),
                                        new EventOutcome("The alternate route is a dead end — you backtrack, losing stamina.", 40, 0, -12, false)
                                ))
                )
        ));

        events.add(new Event(
                "Deep in the mine you find an untapped gem vein. " +
                        "It would take time to extract anything valuable.",
                List.of(
                        new EventOption("Spend time mining it",
                                List.of(
                                        new EventOutcome("You extract 50 gold worth of gems.", 50, 50, -12, false),
                                        new EventOutcome("The vein is shallow — only 20 gold worth before it runs out.", 35, 20, -8, false),
                                        new EventOutcome("The digging causes a small collapse — stamina lost, no gems recovered.", 15, 0, -20, false)
                                )),
                        new EventOption("Note the location and move on",
                                List.of(
                                        new EventOutcome("You mark the spot and continue.", 100, 0, 0, false)
                                ))
                )
        ));

        events.add(new Event(
                "The mine's main shaft has flooded at the bottom. " +
                        "You need to cross to reach the exit.",
                List.of(
                        new EventOption("Wade through",
                                List.of(
                                        new EventOutcome("The water is shallow enough — you cross without major issue.", 65, 0, -8, false),
                                        new EventOutcome("Deeper than it looked — the cold water saps stamina.", 35, 0, -15, false)
                                )),
                        new EventOption("Look for another exit",
                                List.of(
                                        new EventOutcome("You find a ventilation shaft and climb out — gold found on the way.", 40, 15, -10, false),
                                        new EventOutcome("The search takes too long — stamina lost and you wade anyway.", 60, 0, -18, false)
                                ))
                )
        ));

        return new Expedition("Forgotten Mine",
                "An abandoned mine with untapped riches — and plenty of hazards. Adventurers demand 1.2x pay.",
                1.2, 6, events);
    }

    // -------------------------------------------------------------------------
    // EXPEDITION 4
    // -------------------------------------------------------------------------

    private static Expedition buildSunkenTemple() {
        List<Event> events = new ArrayList<>();

        events.add(new Event(
                "The temple entrance is guarded by stone constructs that still seem to function. " +
                        "They haven't moved yet.",
                List.of(
                        new EventOption("Attempt to sneak past",
                                List.of(
                                        new EventOutcome("You slip past while they face away.", 50, 0, -5, false),
                                        new EventOutcome("One construct activates — you run, losing stamina.", 30, 0, -15, false),
                                        new EventOutcome("Both activate — you're driven back.", 20, 0, 0, true)
                                )),
                        new EventOption("Destroy them",
                                List.of(
                                        new EventOutcome("You shatter both constructs and find gems in their cores — 25 gold.", 55, 25, -12, false),
                                        new EventOutcome("A tough fight — constructs dealt damage before falling.", 45, 0, -20, false)
                                ))
                )
        ));

        events.add(new Event(
                "A chamber is half-submerged in murky water. Ancient offerings " +
                        "are visible on an altar just above the waterline.",
                List.of(
                        new EventOption("Wade in and take the offerings",
                                List.of(
                                        new EventOutcome("You recover 40 gold worth of offerings without incident.", 55, 40, -5, false),
                                        new EventOutcome("Something in the water bites — stamina lost but you get 20 gold.", 30, 20, -15, false),
                                        new EventOutcome("You sink into deeper water than expected and have to retreat.", 15, 0, -18, false)
                                )),
                        new EventOption("Leave it alone",
                                List.of(
                                        new EventOutcome("You respect the altar and press on.", 100, 0, 0, false)
                                ))
                )
        ));

        events.add(new Event(
                "Glyphs on the wall pulse with faint light. " +
                        "One of your adventurers thinks they can read them.",
                List.of(
                        new EventOption("Let them attempt to read the glyphs",
                                List.of(
                                        new EventOutcome("They reveal a hidden door — 35 gold worth of relics inside.", 45, 35, 0, false),
                                        new EventOutcome("The glyphs trigger a trap — stamina lost.", 35, 0, -15, false),
                                        new EventOutcome("A burst of energy knocks the party back — heavy stamina loss.", 20, 0, -22, false)
                                )),
                        new EventOption("Ignore them and move on",
                                List.of(
                                        new EventOutcome("You walk past without incident.", 100, 0, 0, false)
                                ))
                )
        ));

        events.add(new Event(
                "A massive serpent coils around the temple's central pillar. " +
                        "It blocks the path forward.",
                List.of(
                        new EventOption("Lure it away with food",
                                List.of(
                                        new EventOutcome("The serpent follows the bait — you slip past.", 60, 0, -5, false),
                                        new EventOutcome("It takes the food but stays put — you fight it anyway.", 40, 0, -18, false)
                                )),
                        new EventOption("Fight the serpent",
                                List.of(
                                        new EventOutcome("You slay it and harvest valuable scales — 30 gold.", 45, 30, -15, false),
                                        new EventOutcome("The fight is brutal — you drive it off but lose heavy stamina.", 35, 0, -22, false),
                                        new EventOutcome("The serpent is too powerful — you retreat.", 20, 0, 0, true)
                                ))
                )
        ));

        events.add(new Event(
                "The inner sanctum holds a sealed chest on a raised dais. " +
                        "Strange runes line the lock.",
                List.of(
                        new EventOption("Force it open",
                                List.of(
                                        new EventOutcome("The chest bursts open — 55 gold in ancient coins.", 50, 55, -8, false),
                                        new EventOutcome("The runes activate — you take damage forcing it. 30 gold.", 30, 30, -15, false),
                                        new EventOutcome("A curse activates — heavy stamina loss and nothing gained.", 20, 0, -20, false)
                                )),
                        new EventOption("Study the runes before touching it",
                                List.of(
                                        new EventOutcome("You find the safe combination in the inscriptions — 55 gold, no damage.", 40, 55, 0, false),
                                        new EventOutcome("You misread the runes — trap triggers anyway.", 60, 20, -12, false)
                                ))
                )
        ));

        events.add(new Event(
                "The temple begins flooding as you make your way out. " +
                        "Water is rising fast.",
                List.of(
                        new EventOption("Run for the entrance",
                                List.of(
                                        new EventOutcome("You sprint out before the water rises too high.", 65, 0, -10, false),
                                        new EventOutcome("The water catches you — you make it out exhausted.", 35, 0, -20, false)
                                )),
                        new EventOption("Look for a higher exit",
                                List.of(
                                        new EventOutcome("You find a collapsed wall and climb out — with extra loot.", 45, 20, -8, false),
                                        new EventOutcome("No exit found in time — you're forced to swim out, stamina gutted.", 55, 0, -25, false)
                                ))
                )
        ));

        return new Expedition("Sunken Temple",
                "A partially submerged ruin filled with ancient traps and creatures. Adventurers demand 1.4x pay.",
                1.4, 7, events);
    }

    // -------------------------------------------------------------------------
    // EXPEDITION 5
    // -------------------------------------------------------------------------

    private static Expedition buildFrostpeakPass() {
        List<Event> events = new ArrayList<>();

        events.add(new Event(
                "A blizzard is rolling in from the north. You can push on or " +
                        "shelter and wait it out.",
                List.of(
                        new EventOption("Push on through the blizzard",
                                List.of(
                                        new EventOutcome("You make good time despite the cold.", 40, 0, -10, false),
                                        new EventOutcome("The blizzard worsens — heavy stamina loss.", 60, 0, -20, false)
                                )),
                        new EventOption("Shelter and wait",
                                List.of(
                                        new EventOutcome("The blizzard passes in an hour — you continue refreshed.", 70, 0, 5, false),
                                        new EventOutcome("The blizzard lasts longer than expected — time and stamina lost.", 30, 0, -10, false)
                                ))
                )
        ));

        events.add(new Event(
                "A frozen traveller is slumped against a rock. He's barely alive " +
                        "and has a pack strapped to his back.",
                List.of(
                        new EventOption("Help him and warm him up",
                                List.of(
                                        new EventOutcome("He recovers and rewards you with 25 gold from his pack.", 65, 25, -5, false),
                                        new EventOutcome("He's too far gone. You tried. Stamina spent.", 35, 0, -8, false)
                                )),
                        new EventOption("Take his pack and leave",
                                List.of(
                                        new EventOutcome("The pack holds 30 gold in trade goods.", 70, 30, 0, false),
                                        new EventOutcome("Just rations. Barely worth the guilt.", 30, 5, 0, false)
                                ))
                )
        ));

        events.add(new Event(
                "A narrow ice bridge spans a deep crevasse. " +
                        "It looks old and creaks under foot.",
                List.of(
                        new EventOption("Cross one at a time carefully",
                                List.of(
                                        new EventOutcome("Everyone makes it across safely.", 70, 0, -5, false),
                                        new EventOutcome("The bridge cracks on the last crossing — a close call but everyone's fine.", 30, 0, -12, false)
                                )),
                        new EventOption("Look for another route",
                                List.of(
                                        new EventOutcome("You find a safer crossing further along.", 50, 0, -8, false),
                                        new EventOutcome("No better route — you cross the bridge anyway, with less confidence.", 50, 0, -15, false)
                                ))
                )
        ));

        events.add(new Event(
                "A pair of frost wolves are circling the camp of a dead merchant. " +
                        "His goods are still loaded on his horse.",
                List.of(
                        new EventOption("Drive off the wolves and take the goods",
                                List.of(
                                        new EventOutcome("You drive them away and recover 40 gold in trade goods.", 55, 40, -10, false),
                                        new EventOutcome("The wolves fight back hard — you win but it costs stamina.", 30, 20, -18, false),
                                        new EventOutcome("A third wolf appears — you retreat empty handed.", 15, 0, -8, true)
                                )),
                        new EventOption("Leave it alone",
                                List.of(
                                        new EventOutcome("You give the wolves a wide berth and continue.", 100, 0, 0, false)
                                ))
                )
        ));

        events.add(new Event(
                "An avalanche rumbles above. You have seconds to react.",
                List.of(
                        new EventOption("Run ahead of it",
                                List.of(
                                        new EventOutcome("You outpace the avalanche and shelter behind a rock outcrop.", 55, 0, -10, false),
                                        new EventOutcome("The edge of the avalanche catches you — heavy stamina loss.", 45, 0, -22, false)
                                )),
                        new EventOption("Dive into a snow bank",
                                List.of(
                                        new EventOutcome("You're buried briefly but dig out safely.", 60, 0, -12, false),
                                        new EventOutcome("Buried deep — takes hours to dig out. Heavy stamina loss.", 40, 0, -20, false)
                                ))
                )
        ));

        events.add(new Event(
                "A mountain hermit offers to share his fire and food. " +
                        "He knows the pass well.",
                List.of(
                        new EventOption("Accept his hospitality",
                                List.of(
                                        new EventOutcome("He feeds you and gives you a shortcut — stamina recovered and time saved.", 75, 0, 15, false),
                                        new EventOutcome("The food is foul but filling. Minor stamina restored.", 25, 0, 5, false)
                                )),
                        new EventOption("Decline and press on",
                                List.of(
                                        new EventOutcome("You thank him and continue into the cold.", 100, 0, 0, false)
                                ))
                )
        ));

        return new Expedition("Frostpeak Pass",
                "A treacherous mountain pass plagued by cold and predators. Adventurers demand 1.3x pay.",
                1.3, 6, events);
    }

    // -------------------------------------------------------------------------
    // EXPEDITION 6
    // -------------------------------------------------------------------------

    private static Expedition buildPlagueVillage() {
        List<Event> events = new ArrayList<>();

        events.add(new Event(
                "The village entrance is barricaded. Survivors inside call out " +
                        "that the sick are locked in the church.",
                List.of(
                        new EventOption("Force the barricade",
                                List.of(
                                        new EventOutcome("You push through and enter the village.", 70, 0, -8, false),
                                        new EventOutcome("The barricade is sturdier than it looks — heavy effort to break through.", 30, 0, -15, false)
                                )),
                        new EventOption("Find another way in",
                                List.of(
                                        new EventOutcome("You hop a fence and enter from the rear unmolested.", 80, 0, -5, false),
                                        new EventOutcome("The rear is guarded by a panicked survivor who attacks — stamina lost.", 20, 0, -12, false)
                                ))
                )
        ));

        events.add(new Event(
                "A locked house has valuables visible through the window. " +
                        "The owners are nowhere to be seen.",
                List.of(
                        new EventOption("Break in and take what you find",
                                List.of(
                                        new EventOutcome("You find 35 gold in coin and goods.", 55, 35, 0, false),
                                        new EventOutcome("The noise draws infected villagers — you escape but lose stamina.", 30, 15, -12, false),
                                        new EventOutcome("The house is infected — you rush out empty handed.", 15, 0, -8, false)
                                )),
                        new EventOption("Leave it be",
                                List.of(
                                        new EventOutcome("You move on without looting.", 100, 0, 0, false)
                                ))
                )
        ));

        events.add(new Event(
                "A group of infected villagers shuffles toward you. " +
                        "They're slow but there are many of them.",
                List.of(
                        new EventOption("Fight through them",
                                List.of(
                                        new EventOutcome("You cut through them efficiently with minimal effort.", 50, 0, -10, false),
                                        new EventOutcome("More infected arrive — a slog that costs heavy stamina.", 50, 0, -20, false)
                                )),
                        new EventOption("Lure them away with noise",
                                List.of(
                                        new EventOutcome("You bang a pot and lead them in the wrong direction.", 65, 0, -5, false),
                                        new EventOutcome("They don't follow — you have to fight anyway.", 35, 0, -15, false)
                                ))
                )
        ));

        events.add(new Event(
                "A survivor offers you information about where the village elder " +
                        "hid the community's savings, in exchange for escort out.",
                List.of(
                        new EventOption("Agree to escort him",
                                List.of(
                                        new EventOutcome("He leads you to 50 gold in hidden savings and you escort him safely.", 55, 50, -10, false),
                                        new EventOutcome("He bolts once he gets safe — no gold and effort wasted.", 30, 0, -8, false),
                                        new EventOutcome("He leads you into an ambush from desperate survivors.", 15, -10, -15, false)
                                )),
                        new EventOption("Take the information by force",
                                List.of(
                                        new EventOutcome("He gives it up — you find 50 gold.", 60, 50, -5, false),
                                        new EventOutcome("He gives false directions — you waste time and stamina.", 40, 0, -12, false)
                                ))
                )
        ));

        events.add(new Event(
                "The village apothecary's stores are intact. " +
                        "Medicines and reagents could restore the party's stamina.",
                List.of(
                        new EventOption("Use the medicines on the party",
                                List.of(
                                        new EventOutcome("The medicines work — significant stamina restored.", 70, 0, 20, false),
                                        new EventOutcome("Mixed results — minor stamina restored.", 30, 0, 8, false)
                                )),
                        new EventOption("Sell the medicines instead",
                                List.of(
                                        new EventOutcome("You pack them up — 30 gold when sold later.", 100, 30, 0, false)
                                ))
                )
        ));

        events.add(new Event(
                "The source of the plague appears to be a contaminated well " +
                        "in the village square. Destroying it may stop the spread.",
                List.of(
                        new EventOption("Collapse the well",
                                List.of(
                                        new EventOutcome("You seal the well — the survivors cheer and reward you with 20 gold.", 75, 20, -8, false),
                                        new EventOutcome("The effort collapses part of a nearby building — stamina lost, no reward.", 25, 0, -15, false)
                                )),
                        new EventOption("Leave it and focus on getting out",
                                List.of(
                                        new EventOutcome("You make for the exit without delay.", 100, 0, 0, false)
                                ))
                )
        ));

        return new Expedition("Plague Village",
                "A quarantined settlement ravaged by sickness. Low gold risk but dangerous conditions. Adventurers demand 1.3x pay.",
                1.3, 6, events);
    }

    // -------------------------------------------------------------------------
    // EXPEDITION 7
    // -------------------------------------------------------------------------

    private static Expedition buildShadowMarket() {
        List<Event> events = new ArrayList<>();

        events.add(new Event(
                "The underground market's gatekeeper demands you either pay " +
                        "an entry fee or prove you have something to trade.",
                List.of(
                        new EventOption("Pay the entry fee",
                                List.of(
                                        new EventOutcome("You pay 10 gold and enter without issue.", 100, -10, 0, false)
                                )),
                        new EventOption("Bluff your way in",
                                List.of(
                                        new EventOutcome("Your story holds — you enter for free.", 55, 0, 0, false),
                                        new EventOutcome("He doesn't buy it — you pay double or leave.", 45, -20, 0, false)
                                ))
                )
        ));

        events.add(new Event(
                "A fence offers to buy stolen goods — no questions asked. " +
                        "His prices are generous.",
                List.of(
                        new EventOption("Sell everything you've picked up",
                                List.of(
                                        new EventOutcome("You offload your haul for 40 gold.", 75, 40, 0, false),
                                        new EventOutcome("He shortchanges you — only 20 gold.", 25, 20, 0, false)
                                )),
                        new EventOption("Negotiate harder",
                                List.of(
                                        new EventOutcome("He respects the grit — 50 gold.", 40, 50, 0, false),
                                        new EventOutcome("He takes offense and throws you out of his stall.", 60, 0, 0, false)
                                ))
                )
        ));

        events.add(new Event(
                "Two rival gangs are about to brawl in the market corridor. " +
                        "You're right in the middle of it.",
                List.of(
                        new EventOption("Get out of the way fast",
                                List.of(
                                        new EventOutcome("You duck into a side alley and wait it out.", 70, 0, -5, false),
                                        new EventOutcome("You catch a stray blow fleeing — stamina lost.", 30, 0, -12, false)
                                )),
                        new EventOption("Back one side to end it fast",
                                List.of(
                                        new EventOutcome("Your chosen side wins — they pay you 25 gold for the help.", 50, 25, -10, false),
                                        new EventOutcome("Your side loses — you retreat bruised.", 50, 0, -15, false)
                                ))
                )
        ));

        events.add(new Event(
                "A merchant is being robbed in a side alley. " +
                        "His attacker hasn't seen you.",
                List.of(
                        new EventOption("Intervene and help the merchant",
                                List.of(
                                        new EventOutcome("The robber flees — the grateful merchant gives you 30 gold.", 65, 30, -8, false),
                                        new EventOutcome("The robber calls for backup — a messy brawl. Stamina lost.", 35, 10, -18, false)
                                )),
                        new EventOption("Rob the robber while he's distracted",
                                List.of(
                                        new EventOutcome("You lift his coin purse cleanly — 20 gold.", 60, 20, 0, false),
                                        new EventOutcome("He spots you — a fight breaks out.", 40, 0, -15, false)
                                ))
                )
        ));

        events.add(new Event(
                "An information broker claims to know the location of a hidden " +
                        "cache nearby. He wants 15 gold to share it.",
                List.of(
                        new EventOption("Pay for the information",
                                List.of(
                                        new EventOutcome("The cache is real — 45 gold in valuables.", 55, 30, -5, false),
                                        new EventOutcome("The location is wrong — he's gone before you can complain.", 45, -15, -8, false)
                                )),
                        new EventOption("Threaten it out of him",
                                List.of(
                                        new EventOutcome("He cracks and tells you — the cache yields 45 gold.", 45, 45, -5, false),
                                        new EventOutcome("He screams for the market guards — you have to leave quickly.", 55, 0, -10, false)
                                ))
                )
        ));

        events.add(new Event(
                "Market guards are sweeping the area. If you're caught with " +
                        "unregistered goods you'll be fined.",
                List.of(
                        new EventOption("Bribe the guard captain",
                                List.of(
                                        new EventOutcome("He pockets the 15 gold and waves you through.", 70, -15, 0, false),
                                        new EventOutcome("He takes the bribe and fines you anyway — 25 gold total lost.", 30, -25, 0, false)
                                )),
                        new EventOption("Hide your goods and act innocent",
                                List.of(
                                        new EventOutcome("They pass you by without incident.", 60, 0, -5, false),
                                        new EventOutcome("They search you — forced to dump goods to avoid a larger fine.", 40, -10, -8, false)
                                ))
                )
        ));

        return new Expedition("Shadow Market",
                "An illegal underground bazaar. Profits are high but the company you keep is dangerous. Adventurers demand 1.2x pay.",
                1.2, 5, events);
    }

    // -------------------------------------------------------------------------
    // EXPEDITION 8
    // -------------------------------------------------------------------------

    private static Expedition buildBurningWastes() {
        List<Event> events = new ArrayList<>();

        events.add(new Event(
                "The sun is punishing and water supplies are already running low. " +
                        "A shimmering shape in the distance could be an oasis.",
                List.of(
                        new EventOption("Head toward it",
                                List.of(
                                        new EventOutcome("It's real — you refill and rest, recovering stamina.", 50, 0, 15, false),
                                        new EventOutcome("A mirage. The detour costs you stamina and time.", 50, 0, -15, false)
                                )),
                        new EventOption("Ignore it and ration carefully",
                                List.of(
                                        new EventOutcome("Rationing works — you conserve stamina.", 65, 0, -5, false),
                                        new EventOutcome("The heat is worse than expected — stamina loss anyway.", 35, 0, -12, false)
                                ))
                )
        ));

        events.add(new Event(
                "The ruins of a caravan sit half-buried in the sand. " +
                        "Bleached bones and broken crates surround the wreck.",
                List.of(
                        new EventOption("Search the wreck",
                                List.of(
                                        new EventOutcome("You find 35 gold in preserved trade goods.", 50, 35, -5, false),
                                        new EventOutcome("Only rusted junk. The search costs stamina.", 35, 0, -10, false),
                                        new EventOutcome("Buried sand scorpions — multiple stings before you retreat.", 15, 0, -20, false)
                                )),
                        new EventOption("Leave it and keep moving",
                                List.of(
                                        new EventOutcome("You press on through the heat.", 100, 0, 0, false)
                                ))
                )
        ));

        events.add(new Event(
                "A sand wyrm bursts from the dunes ahead. " +
                        "It's enormous and very fast.",
                List.of(
                        new EventOption("Stand and fight",
                                List.of(
                                        new EventOutcome("You drive it off and harvest valuable secretions — 40 gold.", 35, 40, -20, false),
                                        new EventOutcome("A brutal fight — you survive but heavily drained.", 45, 0, -28, false),
                                        new EventOutcome("The wyrm is unstoppable — you retreat.", 20, 0, 0, true)
                                )),
                        new EventOption("Scatter and regroup later",
                                List.of(
                                        new EventOutcome("It loses interest and buries back into the sand.", 60, 0, -10, false),
                                        new EventOutcome("It pursues — you can't shake it until nightfall. Heavy stamina loss.", 40, 0, -20, false)
                                ))
                )
        ));

        events.add(new Event(
                "A desert nomad camp is nearby. They look wary but not hostile.",
                List.of(
                        new EventOption("Approach and trade",
                                List.of(
                                        new EventOutcome("They trade water and food for 15 gold — stamina recovered.", 65, -15, 15, false),
                                        new EventOutcome("They demand too high a price — you walk away.", 35, 0, 0, false)
                                )),
                        new EventOption("Raid the camp",
                                List.of(
                                        new EventOutcome("You raid successfully — 30 gold in goods.", 40, 30, -12, false),
                                        new EventOutcome("The nomads are fierce fighters — you retreat bloodied.", 60, 0, -20, false)
                                ))
                )
        ));

        events.add(new Event(
                "You find an ancient stone door half-buried in a dune. " +
                        "Something about it radiates heat.",
                List.of(
                        new EventOption("Force it open",
                                List.of(
                                        new EventOutcome("A fire trap activates — you take damage but find 45 gold inside.", 40, 45, -18, false),
                                        new EventOutcome("The chamber beyond holds only ash and bones.", 35, 0, -10, false),
                                        new EventOutcome("The door seals again after opening — you find nothing useful.", 25, 0, -8, false)
                                )),
                        new EventOption("Leave it",
                                List.of(
                                        new EventOutcome("You leave the door alone and continue.", 100, 0, 0, false)
                                ))
                )
        ));

        events.add(new Event(
                "A dust storm is rolling in fast. Shelter is sparse out here.",
                List.of(
                        new EventOption("Shelter behind the dunes",
                                List.of(
                                        new EventOutcome("The dunes provide enough cover — you wait it out safely.", 60, 0, -8, false),
                                        new EventOutcome("Sand gets everywhere — some equipment damaged, stamina lost.", 40, 0, -15, false)
                                )),
                        new EventOption("Push through it",
                                List.of(
                                        new EventOutcome("You push through — exhausted but ahead of schedule.", 35, 0, -18, false),
                                        new EventOutcome("The storm is blinding — you get turned around. Heavy stamina loss.", 65, 0, -25, false)
                                ))
                )
        ));

        return new Expedition("Burning Wastes",
                "A merciless desert region. Heat and beasts make every step costly. Adventurers demand 1.4x pay.",
                1.4, 7, events);
    }

    // -------------------------------------------------------------------------
    // EXPEDITION 9
    // -------------------------------------------------------------------------

    private static Expedition buildAncientLibrary() {
        List<Event> events = new ArrayList<>();

        events.add(new Event(
                "The library's outer door is sealed with a combination lock. " +
                        "Symbols cover the surrounding arch.",
                List.of(
                        new EventOption("Study the symbols for the combination",
                                List.of(
                                        new EventOutcome("You crack the code and enter cleanly.", 55, 0, -5, false),
                                        new EventOutcome("Wrong combination — a small shock trap fires. Stamina lost.", 45, 0, -12, false)
                                )),
                        new EventOption("Force the door",
                                List.of(
                                        new EventOutcome("The door gives — you enter though the lock is destroyed.", 65, 0, -10, false),
                                        new EventOutcome("The door triggers an alarm — creatures inside are alerted.", 35, 0, -8, false)
                                ))
                )
        ));

        events.add(new Event(
                "Shelves of ancient tomes fill a vast hall. " +
                        "Some could be worth a fortune to the right buyer.",
                List.of(
                        new EventOption("Carefully select valuable-looking texts",
                                List.of(
                                        new EventOutcome("You pick out 3 rare texts worth 45 gold.", 50, 45, -5, false),
                                        new EventOutcome("The texts crumble at your touch — worthless.", 30, 0, -3, false),
                                        new EventOutcome("A guardian golem activates — you drop the books and flee.", 20, 0, -15, false)
                                )),
                        new EventOption("Grab as many as you can carry",
                                List.of(
                                        new EventOutcome("You haul out a pile — worth 30 gold in bulk.", 55, 30, -10, false),
                                        new EventOutcome("Too heavy and too many — you trip an alarm trap.", 45, 10, -18, false)
                                ))
                )
        ));

        events.add(new Event(
                "A guardian golem patrols the restricted section. " +
                        "It hasn't noticed you yet.",
                List.of(
                        new EventOption("Sneak past it",
                                List.of(
                                        new EventOutcome("You slip through while its back is turned.", 55, 0, -5, false),
                                        new EventOutcome("It turns at the wrong moment — you run, losing stamina.", 30, 0, -15, false),
                                        new EventOutcome("It spots you and attacks — you retreat from the restricted section.", 15, 0, -10, true)
                                )),
                        new EventOption("Destroy it",
                                List.of(
                                        new EventOutcome("You dismantle it and salvage its core — 30 gold.", 45, 30, -15, false),
                                        new EventOutcome("It's tougher than expected — heavy stamina loss but it falls.", 35, 15, -22, false),
                                        new EventOutcome("Too powerful — you retreat from this wing.", 20, 0, 0, true)
                                ))
                )
        ));

        events.add(new Event(
                "A sealed vault in the basement holds what appears to be " +
                        "a collection of magical artefacts.",
                List.of(
                        new EventOption("Pick the lock",
                                List.of(
                                        new EventOutcome("You open it cleanly and pocket 55 gold in artefacts.", 45, 55, -5, false),
                                        new EventOutcome("The lock is trapped — you take damage but get 30 gold.", 35, 30, -15, false),
                                        new EventOutcome("The lock seals permanently when you fail — nothing gained.", 20, 0, -8, false)
                                )),
                        new EventOption("Leave the vault alone",
                                List.of(
                                        new EventOutcome("You move on to other sections of the library.", 100, 0, 0, false)
                                ))
                )
        ));

        events.add(new Event(
                "A colony of ink sprites has nested in the upper stacks. " +
                        "They're harmless alone but swarm when startled.",
                List.of(
                        new EventOption("Move through quietly",
                                List.of(
                                        new EventOutcome("You tiptoe past without waking them.", 65, 0, -5, false),
                                        new EventOutcome("One gets startled — a brief swarm. Stamina lost.", 35, 0, -12, false)
                                )),
                        new EventOption("Smoke them out",
                                List.of(
                                        new EventOutcome("They disperse and you pass freely.", 70, 0, -5, false),
                                        new EventOutcome("The smoke damages several nearby books — you feel bad but continue.", 30, 0, -8, false)
                                ))
                )
        ));

        events.add(new Event(
                "The library's reading room holds a private collection behind " +
                        "a glass case. The curator's notes suggest it's extremely rare.",
                List.of(
                        new EventOption("Smash the case and take the collection",
                                List.of(
                                        new EventOutcome("You pocket the collection — worth 60 gold.", 60, 60, -5, false),
                                        new EventOutcome("The case shatters loudly — golems converge. You escape with 30 gold.", 40, 30, -18, false)
                                )),
                        new EventOption("Leave it — too conspicuous",
                                List.of(
                                        new EventOutcome("You walk away from the temptation.", 100, 0, 0, false)
                                ))
                )
        ));

        return new Expedition("Ancient Library",
                "A vast repository of forbidden knowledge. Treasure abounds but so do its guardians. Adventurers demand 1.3x pay.",
                1.3, 6, events);
    }

    // -------------------------------------------------------------------------
    // EXPEDITION 10
    // -------------------------------------------------------------------------

    private static Expedition buildSeaCliffRuins() {
        List<Event> events = new ArrayList<>();

        events.add(new Event(
                "The cliff path to the ruins is narrow and slippery from sea spray. " +
                        "A wrong step means a very long fall.",
                List.of(
                        new EventOption("Take it slow and steady",
                                List.of(
                                        new EventOutcome("Everyone makes it across without incident.", 70, 0, -5, false),
                                        new EventOutcome("One party member slips — caught in time but stamina spent.", 30, 0, -12, false)
                                )),
                        new EventOption("Move fast before conditions worsen",
                                List.of(
                                        new EventOutcome("You cross before the next wave of spray hits.", 50, 0, -8, false),
                                        new EventOutcome("The speed causes a stumble — stamina lost.", 50, 0, -15, false)
                                ))
                )
        ));

        events.add(new Event(
                "Smugglers have set up a camp in the ruins and are in the middle " +
                        "of unloading a boat below. They haven't seen you.",
                List.of(
                        new EventOption("Ambush them",
                                List.of(
                                        new EventOutcome("You catch them off guard — 45 gold in smuggled goods.", 55, 45, -10, false),
                                        new EventOutcome("They're better armed than they look — tough fight. 20 gold.", 30, 20, -20, false),
                                        new EventOutcome("They outnumber you — you retreat.", 15, 0, 0, true)
                                )),
                        new EventOption("Wait for them to leave",
                                List.of(
                                        new EventOutcome("They pack up and sail off — you enter the ruins freely.", 65, 0, -5, false),
                                        new EventOutcome("They stay all night — you wait until dawn, losing stamina.", 35, 0, -10, false)
                                ))
                )
        ));

        events.add(new Event(
                "A sea cave below the ruins is accessible at low tide. " +
                        "Local legend says it holds sunken treasure.",
                List.of(
                        new EventOption("Descend and explore the cave",
                                List.of(
                                        new EventOutcome("You find a sunken chest — 50 gold in waterlogged coins.", 45, 50, -10, false),
                                        new EventOutcome("The cave is mostly empty — 10 gold and a lot of effort.", 35, 10, -12, false),
                                        new EventOutcome("The tide comes in faster than expected — desperate scramble out.", 20, 0, -22, false)
                                )),
                        new EventOption("Skip it — too risky",
                                List.of(
                                        new EventOutcome("You focus on the ruins above instead.", 100, 0, 0, false)
                                ))
                )
        ));

        events.add(new Event(
                "A crumbling watchtower on the cliff edge holds a locked iron chest " +
                        "bolted to the floor.",
                List.of(
                        new EventOption("Force it open",
                                List.of(
                                        new EventOutcome("You crack it open — 40 gold inside.", 55, 40, -8, false),
                                        new EventOutcome("The effort causes the tower floor to buckle — stamina lost, 20 gold.", 30, 20, -15, false),
                                        new EventOutcome("The tower collapses — you escape empty handed.", 15, 0, -18, false)
                                )),
                        new EventOption("Leave it",
                                List.of(
                                        new EventOutcome("You leave the chest where it is.", 100, 0, 0, false)
                                ))
                )
        ));

        events.add(new Event(
                "A giant crab has emerged from the water and blocked the path back. " +
                        "It's the size of a cart.",
                List.of(
                        new EventOption("Fight it",
                                List.of(
                                        new EventOutcome("You crack its shell and harvest it — 35 gold in rare materials.", 45, 35, -15, false),
                                        new EventOutcome("A long fight — it eventually retreats. Heavy stamina loss.", 40, 0, -22, false),
                                        new EventOutcome("It's too armoured — you retreat into the ruins.", 15, 0, 0, true)
                                )),
                        new EventOption("Wait it out",
                                List.of(
                                        new EventOutcome("It wanders back into the sea after an hour.", 70, 0, -5, false),
                                        new EventOutcome("It stays put — you eventually have to fight it anyway.", 30, 0, -18, false)
                                ))
                )
        ));

        events.add(new Event(
                "A storm is rolling in off the sea. The path back will be " +
                        "dangerous if you don't move now.",
                List.of(
                        new EventOption("Leave immediately",
                                List.of(
                                        new EventOutcome("You make it back before the worst of the storm hits.", 70, 0, -8, false),
                                        new EventOutcome("The storm catches you — a punishing walk back.", 30, 0, -18, false)
                                )),
                        new EventOption("Shelter in the ruins and wait",
                                List.of(
                                        new EventOutcome("The ruins hold — you wait out the storm in reasonable comfort.", 60, 0, -5, false),
                                        new EventOutcome("The ruins are drafty and cold — miserable night, stamina lost.", 40, 0, -15, false)
                                ))
                )
        ));

        return new Expedition("Sea Cliff Ruins",
                "Coastal ruins haunted by smugglers and sea creatures. Adventurers demand 1.3x pay.",
                1.3, 6, events);
    }

    // -------------------------------------------------------------------------
    // EXPEDITION 11
    // -------------------------------------------------------------------------

    private static Expedition buildOrcStronghold() {
        List<Event> events = new ArrayList<>();

        events.add(new Event(
                "The orc stronghold's outer gate is guarded by two sentries. " +
                        "A third patrols the wall above.",
                List.of(
                        new EventOption("Take out the sentries quietly",
                                List.of(
                                        new EventOutcome("You neutralise them silently and slip inside.", 50, 0, -8, false),
                                        new EventOutcome("One gets a shout off — you're inside but the alarm is partial.", 30, 0, -12, false),
                                        new EventOutcome("The patrol spots you — full alarm raised. You retreat.", 20, 0, 0, true)
                                )),
                        new EventOption("Create a distraction",
                                List.of(
                                        new EventOutcome("They investigate the noise — you slip through the gate.", 60, 0, -5, false),
                                        new EventOutcome("They send only one to investigate — the other stays put.", 40, 0, -15, false)
                                ))
                )
        ));

        events.add(new Event(
                "The orc barracks holds sleeping warriors. " +
                        "Their weapons and coin pouches hang from the bunks.",
                List.of(
                        new EventOption("Steal what you can while they sleep",
                                List.of(
                                        new EventOutcome("You lift 35 gold in pouches without waking anyone.", 55, 35, -5, false),
                                        new EventOutcome("One stirs — you freeze and back out slowly. 15 gold taken.", 30, 15, -8, false),
                                        new EventOutcome("One wakes up — the whole barracks erupts. You flee.", 15, 0, -15, true)
                                )),
                        new EventOption("Leave them and move on",
                                List.of(
                                        new EventOutcome("You leave the barracks undisturbed.", 100, 0, 0, false)
                                ))
                )
        ));

        events.add(new Event(
                "The stronghold's warchief is alone in his command tent, " +
                        "studying maps. He hasn't noticed you.",
                List.of(
                        new EventOption("Assassinate him",
                                List.of(
                                        new EventOutcome("Clean kill — chaos in the stronghold lets you move freely. 20 gold from his person.", 45, 20, -8, false),
                                        new EventOutcome("He survives and roars — you fight your way out. Heavy stamina.", 35, 10, -22, false),
                                        new EventOutcome("He was expecting it — a bodyguard appears. You retreat.", 20, 0, 0, true)
                                )),
                        new EventOption("Steal his maps and plans for later sale",
                                List.of(
                                        new EventOutcome("You steal the plans — worth 30 gold to the right buyer.", 65, 30, -5, false),
                                        new EventOutcome("He notices them missing and raises the alarm.", 35, 0, -10, false)
                                ))
                )
        ));

        events.add(new Event(
                "The stronghold's treasury is locked behind an iron door. " +
                        "A troll guard sits snoring outside.",
                List.of(
                        new EventOption("Sneak past the troll",
                                List.of(
                                        new EventOutcome("You tiptoe past and crack the lock — 55 gold inside.", 50, 55, -8, false),
                                        new EventOutcome("The troll wakes — you drop the loot and run.", 50, 0, -18, false)
                                )),
                        new EventOption("Lure the troll away first",
                                List.of(
                                        new EventOutcome("It follows the bait — you loot the treasury for 55 gold.", 60, 55, -5, false),
                                        new EventOutcome("It comes back too soon — you escape with 25 gold.", 40, 25, -12, false)
                                ))
                )
        ));

        events.add(new Event(
                "A caged prisoner in the courtyard begs for release. " +
                        "Freeing him will make noise.",
                List.of(
                        new EventOption("Free him",
                                List.of(
                                        new EventOutcome("He's a merchant who rewards you with 25 gold once safe.", 55, 25, -8, false),
                                        new EventOutcome("He panics and shouts — the alarm is raised.", 25, 0, -15, false),
                                        new EventOutcome("He was bait — orcs were waiting.", 20, 0, -18, true)
                                )),
                        new EventOption("Leave him",
                                List.of(
                                        new EventOutcome("You press on without freeing him.", 100, 0, 0, false)
                                ))
                )
        ));

        events.add(new Event(
                "The stronghold's rear gate is your exit. " +
                        "A patrol is approaching from outside.",
                List.of(
                        new EventOption("Rush the gate before they arrive",
                                List.of(
                                        new EventOutcome("You slip out just before the patrol rounds the corner.", 65, 0, -8, false),
                                        new EventOutcome("They spot you just as you exit — a chase. Stamina lost.", 35, 0, -15, false)
                                )),
                        new EventOption("Hide and wait for the patrol to pass",
                                List.of(
                                        new EventOutcome("They pass without incident — clean exit.", 70, 0, -5, false),
                                        new EventOutcome("They linger too long — you grow stiff and cold waiting.", 30, 0, -10, false)
                                ))
                )
        ));

        return new Expedition("Orc Stronghold",
                "A heavily fortified orc camp. Combat is likely and the rewards reflect it. Adventurers demand 1.6x pay.",
                1.6, 9, events);
    }

    // -------------------------------------------------------------------------
    // EXPEDITION 12
    // -------------------------------------------------------------------------

    private static Expedition buildHauntedMansion() {
        List<Event> events = new ArrayList<>();

        events.add(new Event(
                "The mansion's front door swings open on its own. " +
                        "A cold wind rushes out despite the still night.",
                List.of(
                        new EventOption("Enter boldly",
                                List.of(
                                        new EventOutcome("Nothing happens — you step inside without issue.", 60, 0, 0, false),
                                        new EventOutcome("A wailing shriek startles the party — stamina lost to fright.", 40, 0, -10, false)
                                )),
                        new EventOption("Prop the door open and enter cautiously",
                                List.of(
                                        new EventOutcome("You enter safely with an exit assured.", 80, 0, -5, false),
                                        new EventOutcome("The door slams shut despite your prop — you're inside regardless.", 20, 0, -8, false)
                                ))
                )
        ));

        events.add(new Event(
                "The dining room table is set as if for a feast, " +
                        "long-decayed food still on the plates. A glowing candelabra sits at the centre.",
                List.of(
                        new EventOption("Take the candelabra",
                                List.of(
                                        new EventOutcome("You pocket it — worth 30 gold. Nothing reacts.", 55, 30, 0, false),
                                        new EventOutcome("Ghostly figures appear and wail — stamina lost from fright.", 45, 30, -15, false)
                                )),
                        new EventOption("Leave it alone",
                                List.of(
                                        new EventOutcome("You wisely leave it be and continue.", 100, 0, 0, false)
                                ))
                )
        ));

        events.add(new Event(
                "A poltergeist is hurling furniture around the upstairs hallway. " +
                        "The master bedroom is at the end.",
                List.of(
                        new EventOption("Rush through",
                                List.of(
                                        new EventOutcome("You dodge the flying furniture and make it through.", 55, 0, -10, false),
                                        new EventOutcome("A wardrobe catches you — heavy stamina loss.", 45, 0, -20, false)
                                )),
                        new EventOption("Try to banish it",
                                List.of(
                                        new EventOutcome("Your efforts work — it dissipates. The hallway is clear.", 40, 0, -8, false),
                                        new EventOutcome("The attempt enrages it — the barrage intensifies.", 60, 0, -18, false)
                                ))
                )
        ));

        events.add(new Event(
                "The master bedroom holds a portrait of the former lord. " +
                        "Behind it is a wall safe.",
                List.of(
                        new EventOption("Open the safe",
                                List.of(
                                        new EventOutcome("The safe yields 50 gold in jewellery.", 50, 50, -5, false),
                                        new EventOutcome("A ghost materialises — you grab the gold and run. Stamina lost.", 35, 50, -18, false),
                                        new EventOutcome("The safe is empty. The ghost appears anyway.", 15, 0, -15, false)
                                )),
                        new EventOption("Leave the safe alone",
                                List.of(
                                        new EventOutcome("You step back from the portrait and look elsewhere.", 100, 0, 0, false)
                                ))
                )
        ));

        events.add(new Event(
                "The mansion's cellar holds wine racks and what appears to be " +
                        "a locked strongbox half-buried under them.",
                List.of(
                        new EventOption("Move the racks and take the box",
                                List.of(
                                        new EventOutcome("You haul it out — 40 gold in old coins.", 55, 40, -10, false),
                                        new EventOutcome("The racks collapse loudly — minor stamina loss, but 40 gold found.", 30, 40, -15, false),
                                        new EventOutcome("The box is empty. The noise summons something.", 15, 0, -20, false)
                                )),
                        new EventOption("Leave the cellar",
                                List.of(
                                        new EventOutcome("You decide against it and head back upstairs.", 100, 0, 0, false)
                                ))
                )
        ));

        events.add(new Event(
                "The mansion's spirit — the former lord himself — manifests in the foyer. " +
                        "He demands to know why you've invaded his home.",
                List.of(
                        new EventOption("Appeal to him and explain yourselves",
                                List.of(
                                        new EventOutcome("He is satisfied and lets you leave peacefully — even points you to hidden gold. 25 gold.", 45, 25, 0, false),
                                        new EventOutcome("He doesn't believe you — hurls you out. Stamina lost.", 55, 0, -15, false)
                                )),
                        new EventOption("Run for the exit",
                                List.of(
                                        new EventOutcome("You bolt and make it out before he can stop you.", 65, 0, -12, false),
                                        new EventOutcome("He blocks the exit — a terrifying standoff before he relents.", 35, 0, -20, false)
                                ))
                )
        ));

        return new Expedition("Haunted Mansion",
                "A grand house plagued by restless spirits. Nerves of steel required. Adventurers demand 1.3x pay.",
                1.3, 6, events);
    }

    // -------------------------------------------------------------------------
    // EXPEDITION 13
    // -------------------------------------------------------------------------

    private static Expedition buildDeepForest() {
        List<Event> events = new ArrayList<>();

        events.add(new Event(
                "The forest canopy is so thick that almost no light reaches the floor. " +
                        "Navigation is difficult.",
                List.of(
                        new EventOption("Follow a stream to navigate",
                                List.of(
                                        new EventOutcome("The stream leads you true — no major incident.", 70, 0, -5, false),
                                        new EventOutcome("The stream leads to a marsh — a longer route. Stamina lost.", 30, 0, -12, false)
                                )),
                        new EventOption("Press straight ahead by compass",
                                List.of(
                                        new EventOutcome("You navigate correctly and make good time.", 55, 0, -8, false),
                                        new EventOutcome("You get disoriented — a long detour. Stamina lost.", 45, 0, -15, false)
                                ))
                )
        ));

        events.add(new Event(
                "A giant spider has strung webs across the forest path. " +
                        "Its silk glints with dewdrops — and something else is caught in it.",
                List.of(
                        new EventOption("Burn the webs and loot what's caught",
                                List.of(
                                        new EventOutcome("You find 30 gold in the webbed remains of a traveller.", 55, 30, -8, false),
                                        new EventOutcome("The fire draws the spider back — a fight ensues. 15 gold after.", 30, 15, -18, false),
                                        new EventOutcome("The spider appears immediately — too large to fight. You retreat.", 15, 0, 0, true)
                                )),
                        new EventOption("Find a way around",
                                List.of(
                                        new EventOutcome("You circle around without disturbing the web.", 70, 0, -8, false),
                                        new EventOutcome("The detour is long and tiring.", 30, 0, -14, false)
                                ))
                )
        ));

        events.add(new Event(
                "A woodcutter's abandoned camp sits in a small clearing. " +
                        "Tools and a locked chest remain.",
                List.of(
                        new EventOption("Loot the camp",
                                List.of(
                                        new EventOutcome("The chest holds 25 gold. The tools fetch a decent price too.", 60, 25, -5, false),
                                        new EventOutcome("The chest is empty. The tools are rotted.", 40, 0, -3, false)
                                )),
                        new EventOption("Leave it alone",
                                List.of(
                                        new EventOutcome("You respect someone else's camp and move on.", 100, 0, 0, false)
                                ))
                )
        ));

        events.add(new Event(
                "A wounded dire wolf is lying across the path. " +
                        "It growls but doesn't charge.",
                List.of(
                        new EventOption("Put it out of its misery",
                                List.of(
                                        new EventOutcome("You do so cleanly — its pelt is worth 20 gold.", 70, 20, -5, false),
                                        new EventOutcome("It fights back as you approach — stamina lost before it falls.", 30, 10, -15, false)
                                )),
                        new EventOption("Leave it and pass carefully",
                                List.of(
                                        new EventOutcome("It watches you pass without incident.", 65, 0, -5, false),
                                        new EventOutcome("It lunges as you pass — stamina lost.", 35, 0, -15, false)
                                ))
                )
        ));

        events.add(new Event(
                "A druid's stone circle sits in an overgrown clearing. " +
                        "Offerings of gold coins have been left at the stones.",
                List.of(
                        new EventOption("Take the offerings",
                                List.of(
                                        new EventOutcome("You pocket 20 gold. Nothing bad happens.", 55, 20, 0, false),
                                        new EventOutcome("The forest reacts — animals harass you the rest of the way. Stamina lost.", 45, 20, -15, false)
                                )),
                        new EventOption("Leave the offerings",
                                List.of(
                                        new EventOutcome("You show respect. The forest feels calmer somehow.", 100, 0, 5, false)
                                ))
                )
        ));

        events.add(new Event(
                "A forest troll has spotted the party and is crashing through " +
                        "the undergrowth toward you.",
                List.of(
                        new EventOption("Stand and fight",
                                List.of(
                                        new EventOutcome("You bring it down — its hoard yields 40 gold.", 40, 40, -18, false),
                                        new EventOutcome("A brutal brawl — you drive it off but lose heavy stamina.", 40, 0, -25, false),
                                        new EventOutcome("It's too tough — you retreat.", 20, 0, 0, true)
                                )),
                        new EventOption("Flee into the dense undergrowth",
                                List.of(
                                        new EventOutcome("You lose it in the thick growth.", 60, 0, -10, false),
                                        new EventOutcome("It follows relentlessly — you can't shake it for hours. Heavy stamina loss.", 40, 0, -20, false)
                                ))
                )
        ));

        return new Expedition("Deep Forest",
                "An ancient woodland teeming with predators and old magic. Adventurers demand 1.2x pay.",
                1.2, 5, events);
    }

    // -------------------------------------------------------------------------
    // EXPEDITION 14
    // -------------------------------------------------------------------------

    private static Expedition buildVolcanicCaverns() {
        List<Event> events = new ArrayList<>();

        events.add(new Event(
                "The cavern entrance breathes hot air and the smell of sulphur. " +
                        "The heat is already uncomfortable.",
                List.of(
                        new EventOption("Wet down cloth and press in",
                                List.of(
                                        new EventOutcome("The dampened cloth helps — you enter at manageable cost.", 65, 0, -8, false),
                                        new EventOutcome("The heat is worse inside — stamina loss despite preparation.", 35, 0, -15, false)
                                )),
                        new EventOption("Enter without delay",
                                List.of(
                                        new EventOutcome("You push through the initial heat quickly.", 50, 0, -10, false),
                                        new EventOutcome("The heat hits hard — heavy stamina loss early.", 50, 0, -18, false)
                                ))
                )
        ));

        events.add(new Event(
                "A lava tube crossing requires walking a narrow ledge " +
                        "above a slow-moving lava flow.",
                List.of(
                        new EventOption("Take the ledge carefully",
                                List.of(
                                        new EventOutcome("Everyone makes it across with care.", 65, 0, -8, false),
                                        new EventOutcome("Someone loses footing — caught in time, but stamina spent.", 25, 0, -15, false),
                                        new EventOutcome("The ledge crumbles — a narrow escape. Heavy stamina loss.", 10, 0, -25, false)
                                )),
                        new EventOption("Look for a safer route",
                                List.of(
                                        new EventOutcome("You find a cooler tunnel that bypasses the crossing.", 45, 0, -5, false),
                                        new EventOutcome("No safer route found — you take the ledge anyway, more nervously.", 55, 0, -18, false)
                                ))
                )
        ));

        events.add(new Event(
                "A vein of rare fire-crystals runs along the cavern wall. " +
                        "They're hot to the touch but very valuable.",
                List.of(
                        new EventOption("Mine what you can",
                                List.of(
                                        new EventOutcome("You extract a good haul — 45 gold worth of crystals.", 50, 45, -12, false),
                                        new EventOutcome("The crystal pocket is shallow — 20 gold. Burns on your hands.", 30, 20, -15, false),
                                        new EventOutcome("The mining destabilises the wall — you flee empty handed.", 20, 0, -18, false)
                                )),
                        new EventOption("Leave them and press on",
                                List.of(
                                        new EventOutcome("You continue into the deeper caverns.", 100, 0, 0, false)
                                ))
                )
        ));

        events.add(new Event(
                "A fire salamander the size of a horse blocks the passage. " +
                        "It regards you with lazy curiosity.",
                List.of(
                        new EventOption("Drive it back with torches",
                                List.of(
                                        new EventOutcome("It backs away — the path is clear.", 55, 0, -8, false),
                                        new EventOutcome("It breathes fire in response — stamina lost before it retreats.", 45, 0, -18, false)
                                )),
                        new EventOption("Kill it for its valuable hide",
                                List.of(
                                        new EventOutcome("You slay it — its hide fetches 40 gold.", 40, 40, -15, false),
                                        new EventOutcome("It fights viciously — heavy stamina loss and 20 gold.", 40, 20, -25, false),
                                        new EventOutcome("It's too fierce — you retreat.", 20, 0, 0, true)
                                ))
                )
        ));

        events.add(new Event(
                "A geyser vent in the floor shoots steam at irregular intervals. " +
                        "A valuable deposit sits just beyond it.",
                List.of(
                        new EventOption("Time the geyser and dash through",
                                List.of(
                                        new EventOutcome("Perfectly timed — you grab 35 gold in mineral deposits.", 50, 35, -8, false),
                                        new EventOutcome("Slightly off — the steam scalds you. Stamina lost, 35 gold.", 35, 35, -18, false),
                                        new EventOutcome("Directly hit — heavy stamina loss, no gold recovered.", 15, 0, -25, false)
                                )),
                        new EventOption("Leave it",
                                List.of(
                                        new EventOutcome("You bypass the vent and continue.", 100, 0, 0, false)
                                ))
                )
        ));

        events.add(new Event(
                "The exit tunnel is filling with toxic volcanic gas. " +
                        "You need to move fast.",
                List.of(
                        new EventOption("Sprint for the exit",
                                List.of(
                                        new EventOutcome("You make it out before the gas thickens.", 65, 0, -10, false),
                                        new EventOutcome("The gas hits you near the exit — coughing but alive. Stamina lost.", 35, 0, -20, false)
                                )),
                        new EventOption("Cover your faces and move carefully",
                                List.of(
                                        new EventOutcome("The careful approach keeps the gas out of your lungs.", 55, 0, -12, false),
                                        new EventOutcome("Too slow — the gas overwhelms the covers. Heavy stamina loss.", 45, 0, -22, false)
                                ))
                )
        ));

        return new Expedition("Volcanic Caverns",
                "Unstable tunnels beneath an active volcano. Danger at every step. Adventurers demand 1.5x pay.",
                1.5, 8, events);
    }

    // -------------------------------------------------------------------------
    // EXPEDITION 15
    // -------------------------------------------------------------------------

    private static Expedition buildDesertTomb() {
        List<Event> events = new ArrayList<>();

        events.add(new Event(
                "The tomb entrance is sealed with a stone slab engraved with warnings. " +
                        "Someone has already broken part of the seal.",
                List.of(
                        new EventOption("Force the slab open",
                                List.of(
                                        new EventOutcome("You heave it open and enter. The warnings were just superstition.", 60, 0, -8, false),
                                        new EventOutcome("A poison dart fires from the frame — stamina lost.", 40, 0, -15, false)
                                )),
                        new EventOption("Find the already-broken section",
                                List.of(
                                        new EventOutcome("You squeeze through the broken section with little effort.", 75, 0, -5, false),
                                        new EventOutcome("The broken section is trapped too — dart caught someone.", 25, 0, -12, false)
                                ))
                )
        ));

        events.add(new Event(
                "The antechamber walls are painted with murals depicting curses " +
                        "on those who take from the dead.",
                List.of(
                        new EventOption("Ignore the murals and press in",
                                List.of(
                                        new EventOutcome("Nothing happens. Just paint.", 70, 0, 0, false),
                                        new EventOutcome("A trap in the floor triggers — clearly not just paint.", 30, 0, -15, false)
                                )),
                        new EventOption("Search for traps before moving on",
                                List.of(
                                        new EventOutcome("You find and disarm two floor traps. Safe passage.", 65, 0, -5, false),
                                        new EventOutcome("You find one but miss another — stamina lost.", 35, 0, -12, false)
                                ))
                )
        ));

        events.add(new Event(
                "A burial chamber holds mummified remains on stone biers. " +
                        "Valuable offerings have been placed with the bodies.",
                List.of(
                        new EventOption("Take the offerings",
                                List.of(
                                        new EventOutcome("You pocket 40 gold in amulets and coins.", 50, 40, -5, false),
                                        new EventOutcome("The mummies stir as you take them — animated but slow. 40 gold but stamina lost fleeing.", 30, 40, -18, false),
                                        new EventOutcome("The mummies rise immediately — you drop everything and run.", 20, 0, -15, true)
                                )),
                        new EventOption("Respect the dead and leave them",
                                List.of(
                                        new EventOutcome("You leave the burial chamber undisturbed.", 100, 0, 0, false)
                                ))
                )
        ));

        events.add(new Event(
                "A deep pit trap blocks the corridor. The other side holds " +
                        "what appears to be a cache of burial goods.",
                List.of(
                        new EventOption("Jump it",
                                List.of(
                                        new EventOutcome("You clear it and reach the cache — 35 gold.", 55, 35, -8, false),
                                        new EventOutcome("One party member doesn't quite make it — pulled up safely. Stamina lost.", 30, 20, -15, false),
                                        new EventOutcome("Someone falls in — hauled out but heavy stamina loss.", 15, 0, -22, false)
                                )),
                        new EventOption("Find something to bridge it",
                                List.of(
                                        new EventOutcome("You find a loose stone door and bridge the pit — 35 gold safely.", 60, 35, -10, false),
                                        new EventOutcome("Nothing useful nearby — you jump it anyway.", 40, 20, -12, false)
                                ))
                )
        ));

        events.add(new Event(
                "The tomb's inner sanctum holds a sarcophagus of pure granite. " +
                        "The lid is engraved with a pharaoh's face, inlaid with gems.",
                List.of(
                        new EventOption("Prize out the gem inlays",
                                List.of(
                                        new EventOutcome("You carefully extract the gems — 50 gold.", 55, 50, -8, false),
                                        new EventOutcome("The sarcophagus reacts — the lid slides open and something stirs.", 30, 50, -18, false),
                                        new EventOutcome("The lid collapses inward — you scramble back. No gems but you're safe.", 15, 0, -10, false)
                                )),
                        new EventOption("Open the sarcophagus fully",
                                List.of(
                                        new EventOutcome("Empty — but a false bottom hides 45 gold in coin.", 45, 45, -8, false),
                                        new EventOutcome("A mummy lord rises — a desperate battle. You survive. 30 gold.", 35, 30, -25, false),
                                        new EventOutcome("The mummy lord is overwhelming — you flee.", 20, 0, 0, true)
                                ))
                )
        ));

        events.add(new Event(
                "The tomb begins to seal itself — stone doors sliding shut " +
                        "throughout the complex.",
                List.of(
                        new EventOption("Sprint for the entrance",
                                List.of(
                                        new EventOutcome("You make it out before the last door closes.", 65, 0, -10, false),
                                        new EventOutcome("Barely — the door clips someone. Heavy stamina but you're out.", 35, 0, -20, false)
                                )),
                        new EventOption("Look for a secondary exit",
                                List.of(
                                        new EventOutcome("You find a ventilation shaft and crawl out — with bonus loot.", 40, 20, -12, false),
                                        new EventOutcome("No exit found — you sprint for the entrance in a panic.", 60, 0, -22, false)
                                ))
                )
        ));

        return new Expedition("Desert Tomb",
                "An ancient burial complex with powerful guardians and untouched riches. Adventurers demand 1.5x pay.",
                1.5, 8, events);
    }
}