package net.fabledrealms.dungeon;

import net.fabledrealms.util.cuboid.Cuboid;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Dungeon {

    private final DungeonLocation location;
    private final Map<Integer, List<DungeonLocation>> chests;
    private final Map<Integer, List<String>> mobs;
    private final Map<Integer, List<DungeonLocation>> mobLocations;
    private final Map<Integer, Cuboid> cuboids = new HashMap<>();

    public Dungeon(DungeonLocation location,
                   Map<Integer, List<DungeonLocation>> chests, Map<Integer, List<String>> mobs, Map<Integer, List<DungeonLocation>> mobLocations) {
        this.location = location;
        this.chests = chests;
        this.mobs = mobs;
        this.mobLocations = mobLocations;
    }

    public Map<Integer, List<DungeonLocation>> getChests() {
        return chests;
    }

    public Map<Integer, List<String>> getMobs() {
        return mobs;
    }

    public DungeonLocation getDungeonSpawn() {
        return location;
    }

    public Map<Integer, List<DungeonLocation>> getMobLocations() {
        return mobLocations;
    }

    public Map<Integer, Cuboid> getCuboids() {
        return cuboids;
    }
}
