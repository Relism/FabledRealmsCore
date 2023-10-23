package net.fabledrealms.dungeon.manager;

import net.fabledrealms.Core;
import net.fabledrealms.dungeon.Dungeon;
import net.fabledrealms.dungeon.DungeonLocation;

import java.util.*;

public class DungeonManager {

    private final Core main;
    private final Set<Dungeon> dungeons = new HashSet<>();
    private final Set<String> currentWorlds = new HashSet<>();

    public DungeonManager(Core main) {
        this.main = main;
    }

    public void load(){
        dungeons.add(new Dungeon(new DungeonLocation(this.main.getDungeonFileWrapper().getFile().getInt("dungeon.spawn.x"),
                this.main.getDungeonFileWrapper().getFile().getInt("dungeon.spawn.y"),
                this.main.getDungeonFileWrapper().getFile().getInt("dungeon.spawn.z")),
                getChests(),
                getMobs(), getLocations()));
    }

    private Map<Integer, List<DungeonLocation>> getChests(){
        Map<Integer, List<DungeonLocation>> map = new HashMap<>();
        if (this.main.getDungeonFileWrapper().getFile().getConfigurationSection("locations") == null) return map;
        for (final String index : Objects.requireNonNull(this.main.getDungeonFileWrapper().getFile().getConfigurationSection("locations")).getKeys(false)) {
            if (map.get(this.main.getDungeonFileWrapper().getFile().getInt("locations." + index + ".floor")) == null) {
                List<DungeonLocation> locations = new ArrayList<>();
                locations.add(new DungeonLocation(0,0,0));
                map.put(this.main.getDungeonFileWrapper().getFile().getInt("locations." + index + ".floor"), locations);
            } else {
                map.get(this.main.getDungeonFileWrapper().getFile().getInt("locations." + index + ".floor"))
                        .add(new DungeonLocation(0,0,0));
            }
        }

        return map;
    }

    private Map<Integer, List<String>> getMobs(){
        return null;
    }

    private List<DungeonLocation> getLocations(){
        return new ArrayList<>();
    }
}
