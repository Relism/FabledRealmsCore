package net.fabledrealms.dungeon.manager;

import io.lumine.mythic.api.mobs.MythicMob;
import io.lumine.mythic.bukkit.MythicBukkit;
import net.fabledrealms.Core;
import net.fabledrealms.dungeon.Dungeon;
import net.fabledrealms.util.serializer.LocationSerializer;
import org.bukkit.Location;

import java.util.*;

public class DungeonManager {

    private final Core main;
    private final Set<Dungeon> dungeons = new HashSet<>();
    private final Set<String> currentWorlds = new HashSet<>();

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
                    getChestsFromFile(dungeon), getMobsFromFile(dungeon)));
        }
    }

    private Map<Integer, List<Location>> getChestsFromFile(String dungeon){
        Map<Integer, List<Location>> map = new HashMap<>();
        if (this.main.getDungeonFileWrapper().getFile().getConfigurationSection("dungeon." + dungeon + ".chests") == null) return map; // empty map
        for (final String integers : Objects.requireNonNull(this.main.getDungeonFileWrapper().getFile().getConfigurationSection("dungeon." + dungeon + ".chests")).getKeys(false)) {
            List<Location> locations = new ArrayList<>();
            this.main.getDungeonFileWrapper().getFile().getStringList("dungeon." + dungeon + ".chests." + integers + ".list")
                            .forEach(string -> locations.add(LocationSerializer.deSerialize(string)));
            map.put(this.main.getDungeonFileWrapper().getFile().getInt("dungeon." + dungeon + ".chests." + integers + ".floor"),
                    locations);
        }

        return map;
    }

    private Map<Integer, List<String>> getMobsFromFile(String dungeon){
        Map<Integer, List<String>> map = new HashMap<>();

        if (this.main.getDungeonFileWrapper().getFile().getConfigurationSection("dungeon." + dungeon + ".mobs") == null) return map; // empty map
        for (final String integers : Objects.requireNonNull(this.main.getDungeonFileWrapper().getFile().getConfigurationSection("dungeon." + dungeon + ".mobs")).getKeys(false)) {
            List<String> mobs = new ArrayList<>(this.main.getDungeonFileWrapper().getFile().getStringList("dungeon." + dungeon + ".mobs." + integers + ".list"));
            map.put(this.main.getDungeonFileWrapper().getFile().getInt("dungeon." + dungeon + ".mobs." + integers + ".floor"),
                    mobs);
        }

        return map;
    }

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

    public List<MythicMob> getMythicMobFromList(String dungeon, int floor){
        List<MythicMob> map = new ArrayList<>();
        for (final String integers : Objects.requireNonNull(this.main.getDungeonFileWrapper().getFile().getConfigurationSection("dungeon." + dungeon + ".mobs")).getKeys(false)) {
            if (this.main.getDungeonFileWrapper().getFile().getInt("dungeon." + dungeon + ".mobs." + integers + ".floor") == floor) {
                this.main.getDungeonFileWrapper().getFile().getStringList("dungeon." + dungeon + ".mobs." + integers + ".mobs").forEach(stringMob -> {
                    map.add(MythicBukkit.inst().getMobManager().getMythicMob(stringMob).get());
                });
            }
        }

        return map;
    }

    public Set<Dungeon> getDungeons() {
        return dungeons;
    }

    public Set<String> getCurrentWorlds() {
        return currentWorlds;
    }
}
