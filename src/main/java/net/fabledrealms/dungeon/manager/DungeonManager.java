package net.fabledrealms.dungeon.manager;

import net.fabledrealms.Core;
import net.fabledrealms.dungeon.Dungeon;
import org.bukkit.Location;

import java.util.*;

public class DungeonManager {

    private final Core main;
    private final Set<Dungeon> dungeons = new HashSet<>();

    public DungeonManager(Core main) {
        this.main = main;
    }

    public void load(){
        if (this.main.getDungeonFileWrapper().getFile().getConfigurationSection("dungeon") == null) return;
        for (final String dungeon : Objects.requireNonNull(this.main.getDungeonFileWrapper().getFile().getConfigurationSection("dungeon")).getKeys(false)) {
            dungeons.add(new Dungeon(this.main.getDungeonFileWrapper().getFile().getString("dungeon." + dungeon + ".name"),
                    this.main.getDungeonFileWrapper().getFile().getLocation("dungeon." + dungeon + ".spawn"),
                    this.main.getDungeonFileWrapper().getFile().getLocation("dungeon." + dungeon + ".first"),
                    this.main.getDungeonFileWrapper().getFile().getLocation("dungeon." + dungeon + ".second"),
                    this.main.getDungeonFileWrapper().getFile().get("dungeon." + dungeon + ".chests"),
                    (Map<Integer, List<String>>) this.main.getDungeonFileWrapper().getFile().get("dungeon." + dungeon + ".mobs")));
        }
    }

    // todo get locationseriazlier and finish this function
    private Map<Integer, List<Location>> getChestsFromFile(String dungeon){
        Map<Integer, List<Location>> map = new HashMap<>();
        List<Location> locations = new ArrayList<>();
        if (this.main.getDungeonFileWrapper().getFile().getConfigurationSection("dungeon." + dungeon + ".chests") == null) return map;
        for (final String integers : Objects.requireNonNull(this.main.getDungeonFileWrapper().getFile().getConfigurationSection("dungeon." + dungeon + ".chests")).getKeys(false)) {
            this.main.getDungeonFileWrapper().getFile().getStringList("dungeon." + dungeon + ".chests." + integers + ".list")
                            .forEach(string -> locations.add(null));
            map.put(this.main.getDungeonFileWrapper().getFile().getInt("dungeon." + dungeon + ".chests." + integers + ".floor"),
                    locations);
            locations.clear();
        }

        return map;
    }

    private void getMobsFromFile(){}

    public void save(){
        dungeons.forEach(dungeon -> {
            this.main.getDungeonFileWrapper().getFile().set("dungeon." + dungeon.getName() + ".name", dungeon.getName());
            this.main.getDungeonFileWrapper().getFile().set("dungeon." + dungeon.getName() + ".spawn", dungeon.getDungeonSpawn());
            this.main.getDungeonFileWrapper().getFile().set("dungeon." + dungeon.getName() + ".first", dungeon.getDungeonClaimOne());
            this.main.getDungeonFileWrapper().getFile().set("dungeon." + dungeon.getName() + ".second", dungeon.getDungeonClaimTwo());
            dungeon.getChests().forEach((integer, list) -> {
                this.main.getDungeonFileWrapper().getFile().set("dungeon." + dungeon.getName() + ".chests." + integer + ".floor", integer);
                this.main.getDungeonFileWrapper().getFile().set("dungeon." + dungeon.getName() + ".chests." + integer + ".list", list);
            });
            dungeon.getMobs().forEach((integer, mob) -> {
                this.main.getDungeonFileWrapper().getFile().set("dungeon." + dungeon.getName() + ".mobs." + integer + ".floor", integer);
                this.main.getDungeonFileWrapper().getFile().set("dungeon." + dungeon.getName() + ".mobs." + integer + ".list", mob);
            });
            this.main.getDungeonFileWrapper().saveFile();
        });
    }

    public Set<Dungeon> getDungeons() {
        return dungeons;
    }
}
