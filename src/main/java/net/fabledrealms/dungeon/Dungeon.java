package net.fabledrealms.dungeon;

import java.util.List;
import java.util.Map;

public class Dungeon {

    private final String name;
    private final String dungeonSpawn;
    private final String dungeonClaimOne;
    private final String dungeonClaimTwo;
    private final Map<Integer, List<String>> chests;
    private final Map<Integer, List<String>> mobs;

    public Dungeon(String name, String dungeonSpawn, String dungeonClaimOne, String dungeonClaimTwo,
                   Map<Integer, List<String>> chests, Map<Integer, List<String>> mobs) {
        this.name = name;
        this.dungeonSpawn = dungeonSpawn;
        this.dungeonClaimOne = dungeonClaimOne;
        this.dungeonClaimTwo = dungeonClaimTwo;
        this.chests = chests;
        this.mobs = mobs;
    }

    public String getName() {
        return name;
    }

    public Map<Integer, List<String>> getChests() {
        return chests;
    }

    public Map<Integer, List<String>> getMobs() {
        return mobs;
    }

    public String getDungeonClaimOne() {
        return dungeonClaimOne;
    }

    public String getDungeonClaimTwo() {
        return dungeonClaimTwo;
    }

    public String getDungeonSpawn() {
        return dungeonSpawn;
    }
}
