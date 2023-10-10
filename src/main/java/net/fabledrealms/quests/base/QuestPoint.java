package net.fabledrealms.quests.base;

import org.bukkit.Location;

public class QuestPoint {

    private Location start;
    private Location target;
    private boolean achieved;

    // MIGHT
    private String nextQuest;

    public QuestPoint(Location start, Location target, boolean achieved, String nextQuest) {
        this.start = start;
        this.target = target;
        this.achieved = achieved;
        this.nextQuest = nextQuest;
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
