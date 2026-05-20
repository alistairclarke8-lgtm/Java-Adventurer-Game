package seng201.team0.factories;

import seng201.team0.models.Event;
import seng201.team0.models.EventOption;
import seng201.team0.models.EventOutcome;
import seng201.team0.models.Expedition;

import java.util.ArrayList;
import java.util.List;

public class ExpeditionPool {

    private ExpeditionPool() {}

    }
    private static Expedition buildExpeditionName() {
        List<Event> events = new ArrayList<>();

        events.add(new Event(
                "Event description.",
                List.of(
                        new EventOption("Option 1",
                                List.of(
                                        new EventOutcome("Outcome description.", probability, goldChange, staminaChange, isFailure),
                                        new EventOutcome("Outcome description.", probability, goldChange, staminaChange, isFailure)
                                )),
                        new EventOption("Option 2",
                                List.of(
                                        new EventOutcome("Outcome description.", probability, goldChange, staminaChange, isFailure),
                                        new EventOutcome("Outcome description.", probability, goldChange, staminaChange, isFailure)
                                ))
                )
        ));

        return new Expedition("Name",
                "Description.",
                payMultiplier, difficulty, events);
    }
public static List<Expedition> getAll() {
    return List.of(
    );
}
