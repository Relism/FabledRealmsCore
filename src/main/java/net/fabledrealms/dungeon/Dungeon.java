package net.fabledrealms.dungeon;

import org.bukkit.Location;

import java.util.List;
import java.util.Map;

public class Dungeon {

    private final String name;
    private final Location dungeonSpawn;
    private final Location dungeonClaimOne;
    private final Location dungeonClaimTwo;
    private final Map<Integer, List<String>> chests;
    private final Map<Integer, List<String>> mobs;

    public Dungeon(String name, Location dungeonSpawn, Location dungeonClaimOne, Location dungeonClaimTwo,
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

    public Location getDungeonClaimOne() {
        return dungeonClaimOne;
    }

    public Location getDungeonClaimTwo() {
        return dungeonClaimTwo;
    }

    public Location getDungeonSpawn() {
        return dungeonSpawn;
    }
}
