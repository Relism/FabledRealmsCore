package net.fabledrealms.dungeon.manager;

import io.lumine.mythic.api.mobs.MythicMob;
import io.lumine.mythic.bukkit.BukkitAdapter;
import io.lumine.mythic.bukkit.MythicBukkit;
import net.fabledrealms.Core;
import net.fabledrealms.dungeon.Dungeon;
import net.fabledrealms.dungeon.DungeonLocation;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class DungeonManager {

    private final Core main;
    private final Map<UUID, World> dungeonWorlds = new HashMap<>();

    public DungeonManager(Core main) {
        this.main = main;
    }

    public void startDungeon(Player owner){
        World created = this.main.getWorldManager().copyWorld(this.main.getWorldManager().getWorld(), owner.getName() + "-dungeon-world");
        dungeonWorlds.put(owner.getUniqueId(), created);

        this.spawnChests(dungeonWorlds.get(owner.getUniqueId()));
        this.spawnMobs(dungeonWorlds.get(owner.getUniqueId()));

        owner.teleport(new Location(dungeonWorlds.get(owner.getUniqueId()),
                this.main.getDungeonFileWrapper().getFile().getInt("dungeon.spawn.x"),
                this.main.getDungeonFileWrapper().getFile().getInt("dungeon.spawn.y"),
                this.main.getDungeonFileWrapper().getFile().getInt("dungeon.spawn.z")));
    }

    public void endDungeon(Player owner){
        // todo assign to spawn
        owner.teleport(new Location(Bukkit.getWorld("world"),1,1,1));
        this.main.getWorldManager().unloadWorld(this.dungeonWorlds.get(owner.getUniqueId()));
    }

    private void spawnMobs(World world) {
        if (this.main.getDungeonFileWrapper().getFile().getConfigurationSection("mobs") == null) return;
        for (final String index : Objects.requireNonNull(this.main.getDungeonFileWrapper().getFile().getConfigurationSection("mobs")).getKeys(false)) {
            MythicMob mob = MythicBukkit.inst().getMobManager().getMythicMob(this.main.getDungeonFileWrapper().getFile().getString("mobs." + index + ".name")).orElse(null);
            Location spawnLocation = new Location(world,
                    this.main.getDungeonFileWrapper().getFile().getInt("mobs." + index + ".x"),
                    this.main.getDungeonFileWrapper().getFile().getInt("mobs." + index + ".y"),
                    this.main.getDungeonFileWrapper().getFile().getInt("mobs." + index + ".z"));

            if (mob != null) {
                mob.spawn(BukkitAdapter.adapt(spawnLocation), 1);
            }
        }
    }

    private void spawnChests(World world) {
        for (final String index : Objects.requireNonNull(this.main.getDungeonFileWrapper().getFile().getConfigurationSection("chest-locations")).getKeys(false)) {
            Location location = new Location(world,
                    this.main.getDungeonFileWrapper().getFile().getInt("chest-locations." + index + ".x"),
                    this.main.getDungeonFileWrapper().getFile().getInt("chest-locations." + index + ".y"),
                    this.main.getDungeonFileWrapper().getFile().getInt("chest-locations." + index + ".z"));
            location.getBlock().setType(Material.CHEST);
            if (location.getBlock().getState() instanceof Chest chest) {
                Inventory chestInventory = chest.getBlockInventory();
                chestInventory.clear();

                for (Map.Entry<Integer, ItemStack> m : this.main.getLootManager().assignItems
                        (this.main.getLootManager().getRandomizedItems(this.main.getDungeonFileWrapper().getFile().getInt("chest-locations." + index + ".floor"),
                                this.main.getConfigFile().getFile().getInt("dungeon.item-per-chest")), this.main.getConfigFile().getFile().getInt("dungeon.item-per-chest")).entrySet()) {
                    chestInventory.setItem(m.getKey(), m.getValue());
                }
            }
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

    public Map<UUID, World> getDungeonWorlds() {
        return dungeonWorlds;
    }
}
