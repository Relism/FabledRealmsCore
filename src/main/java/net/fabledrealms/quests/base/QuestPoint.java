package net.fabledrealms.quests.base;

import org.bukkit.Location;

import java.util.UUID;

public class QuestPoint {

    private final UUID id;
    private Location start;
    private Location target;
    private boolean achieved;
    private String nextQuest;

    public QuestPoint(Location start) {
        this.start = start;
        this.id = UUID.randomUUID();
    }

    public QuestPoint(UUID id, Location start, Location target, boolean achieved, String nextQuest) {
        this.id = id;
        this.start = start;
        this.target = target;
        this.achieved = achieved;
        this.nextQuest = nextQuest;
    }

    public UUID getId() {
        return id;
    }

    public Location getStart() {
        return start;
    }

    public void setStart(Location start) {
        this.start = start;
    }

    public Location getTarget() {
        return target;
    }

    public void setTarget(Location target) {
        this.target = target;
    }

    public boolean isAchieved() {
        return achieved;
    }

    public void setAchieved(boolean achieved) {
        this.achieved = achieved;
    }

    public String getNextQuest() {
        return nextQuest;
    }

    public void setNextQuest(String nextQuest) {
        this.nextQuest = nextQuest;
    }

    public void init() {}
}
