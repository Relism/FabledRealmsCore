package net.fabledrealms.dungeon.manager;

import io.lumine.mythic.core.skills.placeholders.all.RandomFloatRoundedPlaceholder;
import net.fabledrealms.Core;
import net.fabledrealms.dungeon.Dungeon;
import net.fabledrealms.dungeon.DungeonLocation;
import net.fabledrealms.util.cuboid.Cuboid;
import net.fabledrealms.util.world.WorldManager;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.checkerframework.checker.units.qual.C;

import java.util.*;

public class DungeonManager {

    private final Core main;
    private Dungeon dungeonHelper;
    private final Map<UUID, World> dungeonWorlds = new HashMap<>();

    public DungeonManager(Core main) {
        this.main = main;
    }

    public void load() {
        this.dungeonHelper = new Dungeon(new DungeonLocation(this.main.getDungeonFileWrapper().getFile().getInt("dungeon.spawn.x"),
                this.main.getDungeonFileWrapper().getFile().getInt("dungeon.spawn.y"),
                this.main.getDungeonFileWrapper().getFile().getInt("dungeon.spawn.z")),
                getChests(),
                getMobs(), getLocations());
    }

    public void startDungeon(Player owner){
        World created = this.main.getWorldManager().copyWorld(this.main.getWorldManager().getWorld(), owner.getName() + "-dungeon-world");
        dungeonWorlds.put(owner.getUniqueId(), created);

        this.spawnChests(dungeonWorlds.get(owner.getUniqueId()));
        this.spawnMobs(dungeonWorlds.get(owner.getUniqueId()));

        owner.teleport(new Location(dungeonWorlds.get(owner.getUniqueId()),
                this.dungeonHelper.getDungeonSpawn().getX(),
                this.dungeonHelper.getDungeonSpawn().getY(),
                this.dungeonHelper.getDungeonSpawn().getZ()));
    }

    public void endDungeon(Player owner){

    }

    private void spawnMobs(World world) {

    }

    private void spawnChests(World world) {
        if (this.main.getDungeonFileWrapper().getFile().getConfigurationSection("floor") == null) return;
        for (String index : Objects.requireNonNull(this.main.getDungeonFileWrapper().getFile().getConfigurationSection("floor")).getKeys(false)) {
            Cuboid cuboid = new Cuboid(new Location(world,1,1,1), new Location(world,1,1,1));
        }
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

    public Dungeon getDungeonHelper() {
        return dungeonHelper;
    }

    public Map<UUID, World> getDungeonWorlds() {
        return dungeonWorlds;
    }
}
