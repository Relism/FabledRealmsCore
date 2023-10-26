package net.fabledrealms.dungeon.manager;

import net.fabledrealms.Core;
import net.fabledrealms.dungeon.Dungeon;
import net.fabledrealms.dungeon.DungeonLocation;
import net.fabledrealms.util.world.WorldManager;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.*;

public class DungeonManager {

    private final Core main;
    private final Set<Dungeon> dungeons = new HashSet<>();
    private final Set<World> currentWorlds = new HashSet<>();

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

    public void startDungeon(Player owner){
        World created = WorldManager.copyWorld(WorldManager.init(this.main.getDungeonFileWrapper()
                .getFile().getString("dungeon.world")), owner.getName() + "-dungeonworld");
        // todo spawn chests
        currentWorlds.add(created);
    }

    private Map<Integer, List<DungeonLocation>> getChests(){
        Map<Integer, List<DungeonLocation>> map = new HashMap<>();
        if (this.main.getDungeonFileWrapper().getFile().getConfigurationSection("chest-locations") == null) return map;
        for (final String index : Objects.requireNonNull(this.main.getDungeonFileWrapper().getFile().getConfigurationSection("locations")).getKeys(false)) {
            if (map.get(this.main.getDungeonFileWrapper().getFile().getInt("chest-locations." + index + ".floor")) == null) {
                List<DungeonLocation> locations = new ArrayList<>();
                locations.add(new DungeonLocation(this.main.getDungeonFileWrapper().getFile().getInt("chest-locations." + index + ".x"),
                        this.main.getDungeonFileWrapper().getFile().getInt("chest-locations." + index + ".y"),
                        this.main.getDungeonFileWrapper().getFile().getInt("chest-locations." + index + ".z")));
                map.put(this.main.getDungeonFileWrapper().getFile().getInt("chest-locations." + index + ".floor"), locations);
            } else {
                map.get(this.main.getDungeonFileWrapper().getFile().getInt("chest-locations." + index + ".floor"))
                        .add(new DungeonLocation(this.main.getDungeonFileWrapper().getFile().getInt("chest-locations." + index + ".x"),
                                this.main.getDungeonFileWrapper().getFile().getInt("chest-locations." + index + ".y"),
                                this.main.getDungeonFileWrapper().getFile().getInt("chest-locations." + index + ".z")));
            }
        }

        return map;
    }

    private Map<Integer, List<String>> getMobs(){
        Map<Integer, List<String>> map = new HashMap<>();
        if (this.main.getDungeonFileWrapper().getFile().getConfigurationSection("mobs") == null) return map;
        for (final String index : Objects.requireNonNull(this.main.getDungeonFileWrapper().getFile().getConfigurationSection("mobs")).getKeys(false)) {
            if (map.get(this.main.getDungeonFileWrapper().getFile().getInt("mobs." + index + ".floor")) == null) {
                List<String> mobs = new ArrayList<>();
                mobs.add(this.main.getDungeonFileWrapper().getFile().getString("mobs." + index + ".name"));
                map.put(this.main.getDungeonFileWrapper().getFile().getInt("mobs." + index + ".floor"), mobs);
            } else {
                map.get(this.main.getDungeonFileWrapper().getFile().getInt("mobs." + index + ".floor"))
                        .add(this.main.getDungeonFileWrapper().getFile().getString("mobs." + index + ".name"));
            }
        }

        return map;
    }

    private Map<Integer, List<DungeonLocation>> getLocations(){
        Map<Integer, List<DungeonLocation>> map = new HashMap<>();
        if (this.main.getDungeonFileWrapper().getFile().getConfigurationSection("mobs-location") == null) return map;
        for (final String index : Objects.requireNonNull(this.main.getDungeonFileWrapper().getFile().getConfigurationSection("mobs-location")).getKeys(false)) {
            if (map.get(this.main.getDungeonFileWrapper().getFile().getInt("mobs-location." + index + ".floor")) == null) {
                List<DungeonLocation> locations = new ArrayList<>();
                locations.add(new DungeonLocation(this.main.getDungeonFileWrapper().getFile().getInt("mobs-location." + index + ".x"),
                        this.main.getDungeonFileWrapper().getFile().getInt("mobs-location." + index + ".y"),
                        this.main.getDungeonFileWrapper().getFile().getInt("mobs-location." + index + ".z")));
                map.put(this.main.getDungeonFileWrapper().getFile().getInt("mobs-location." + index + ".floor"), locations);
            } else {
                map.get(this.main.getDungeonFileWrapper().getFile().getInt("mobs-location." + index + ".floor"))
                        .add(new DungeonLocation(this.main.getDungeonFileWrapper().getFile().getInt("mobs-location." + index + ".x"),
                                this.main.getDungeonFileWrapper().getFile().getInt("mobs-location." + index + ".y"),
                                this.main.getDungeonFileWrapper().getFile().getInt("mobs-location." + index + ".z")));
            }
        }

        return map;
    }

    public Set<Dungeon> getDungeons() {
        return dungeons;
    }

    public Set<World> getCurrentWorlds() {
        return currentWorlds;
    }
}
