package seng201.team0.services;

import seng201.team0.GameEnvironment;
import seng201.team0.factories.ExpeditionPool;
import seng201.team0.models.Expedition;
import seng201.team0.models.Item;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ExpeditionService {

    private final GameEnvironment gameEnvironment;
    private final Random random;

    private List<Expedition> availableExpeditions = new ArrayList<>();

    private Expedition currentExpedition;
    private int currentEventIndex = 0;
    private int goldEarned = 0;
    private int eventsCompleted = 0;
    private boolean expeditionOver = false;
    private boolean retreated = false;
    private final List<String> log = new ArrayList<>();
    private final List<Item> itemsFound = new ArrayList<>();

    public ExpeditionService(GameEnvironment gameEnvironment, Random random) {
        this.gameEnvironment = gameEnvironment;
        this.random = random;
    }

    public ExpeditionService(GameEnvironment gameEnvironment) {
        this(gameEnvironment, new Random());
    }

    public void refreshExpeditions() {
        availableExpeditions = ExpeditionPool.getAll();
    }

    public List<Expedition> getAvailableExpeditions() {
        return availableExpeditions;
    }

    public void startExpedition(Expedition expedition) {
        this.currentExpedition = expedition;
        this.currentEventIndex = 0;
        this.goldEarned = 0;
        this.eventsCompleted = 0;
        this.expeditionOver = false;
        this.retreated = false;
        this.log.clear();
        this.itemsFound.clear();
        log.add("Your party sets out on the " + expedition.getName() + ".");
    }
}